package server.image.image.Security;
// 이 부분은 CORS 이후에 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // config to permit all requests
        http.httpBasic().disable();
        http.formLogin().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() { // to delete default username and password that is printed in the log every time, you can provide here any auth manager (InMemoryAuthenticationManager, etc) as you need
        return authentication -> {
            throw new UnsupportedOperationException();
        };
    }
    
}
