//package ru.shvets.springmvclibraryjpa.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//import ru.shvets.springsecurityapp4.service.PersonDetailsService
//
///**
// * @author  Oleg Shvets
// * @version 1.0
// * @date  06.02.2023 23:25
// */
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig (
////    private val authProvider: AuthProviderImpl
//private val personDetailsService: PersonDetailsService
//) {
//
////    @Bean
//    // конфигурирование аутентификации
////    fun configure(auth: AuthenticationManagerBuilder): AuthenticationManagerBuilder {
////        return auth.authenticationProvider(authProvider)
////    }
//
////    fun configure(auth: AuthenticationManagerBuilder) {
////        auth.authenticationProvider(authProvider)
////    }
//
//    @Override
//    // конфигурирование аутентификации
//    protected fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService(personDetailsService)
//            .passwordEncoder(encoder())
//    }
//
//// -------------------------
//    @Bean
//    fun encoder(): PasswordEncoder{
////        return NoOpPasswordEncoder.getInstance()
//        return BCryptPasswordEncoder()
//    }
//
//// -------------------------
//
//    @Bean
//    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
//    // конфигурирование spring security
//    // конфигурирование авторизации
////      http.csrf().disable()// отключаем защиту от межсайтовой подделки запросов
//        http.authorizeHttpRequests()
//            .requestMatchers("/admin").hasRole("ADMIN")
//            .requestMatchers("/auth/login", "/auth/registration", "error").permitAll()
//            .anyRequest().hasAnyRole("USER", "ADMIN")
////            .anyRequest().authenticated()
//            .and()
//            .formLogin().loginPage("/auth/login")
//            .loginProcessingUrl("/process_login")
//            .defaultSuccessUrl("/hello", true)
//            .failureUrl("/auth/login?error")
//            .and()
//            .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login")
//
//        return http.build()
//    }
//
//}