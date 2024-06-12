package jp.ac.it_college.std.s23024.book.manager.presentation.config

import jp.ac.it_college.std.s23024.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s23024.book.manager.application.service.security.BookManagerUserDetailsService
import jp.ac.it_college.std.s23024.book.manager.domain.types.RoleType
import jp.ac.it_college.std.s23024.book.manager.presentation.handler.SecurityHandler
import org.apache.tomcat.util.file.ConfigurationSource
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableMBeanExport
import org.springframework.core.annotation.Order
import org.springframework.security.config.SecurityNamespaceHandler
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationService: AuthenticationService
) {

    @Bean
    @Order(1)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/login", permitAll)
                authorize("/admin/**", hasAuthority(RoleType.ADMIN.toString()))
                authorize(anyRequest, authenticated)
            }
            formLogin {
                //設定(ログイン)に関する設定(2)
                loginProcessingUrl = "/login"
                usernameParameter = "email"
                passwordParameter = "pass"
                authenticationSuccessHandler = SecurityHandler
                authenticationFailureHandler = SecurityHandler
            }
            csrf {
                //クロスサイトリクエストフォージェリ対策の無効化
                // RESTAPIでこれがあると利用不可になるため無効化
                disable()
            }
            exceptionHandling {
                // 未認証 or 拒否時の設定
                authenticationEntryPoint = SecurityHandler
                accessDeniedHandler = SecurityHandler
            }
            cors {
                // CORSの設定(4)。多分書かなくてもOK
            }
        }
        return http.build()
    }

    //パスワード暗号化アルゴリズムを指定(Argon2id)
    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder(
        16, 32, 1, 19 * 1024, 2
    )

    // 認証処理を実装したクラスの設定
    @Bean
    fun userDetailsService(): UserDetailsService =
        BookManagerUserDetailsService(authenticationService)

    @Bean
    fun coreConfigurationSource(): CorsConfigurationSource {
        // CORSの基本設定
        val config = CorsConfiguration().apply {
            // 許可する HTTP メソッドのリスト。実際使うのは GET POST PATCH
            allowedMethods = listOf(CorsConfiguration.ALL) // * (全部許可)
            // 許可する HTTP リクエストヘッダ。特に制御はしない
            allowedHeaders = listOf(CorsConfiguration.ALL) // * (全部許可)
            // アクセス元として許可するオリジン(スキーマ+ドメイン+ポート)
            allowedOrigins = listOf(
                "http://localhost:3000",
            )
            // 資格情報を受け付けるかどうか(セションで必要になる)
            allowCredentials = true
        }
        //基本設定を適用するリクエストパスの設定
        val sourse = UrlBasedCorsConfigurationSource().apply {
            //全リクエストに CORS設定を適用
            registerCorsConfiguration("/**", config)
        }
        return sourse
    }
}