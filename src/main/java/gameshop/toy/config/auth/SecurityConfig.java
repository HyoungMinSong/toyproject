package gameshop.toy.config.auth;

import gameshop.toy.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화시켜 줍니다.
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain Configure(HttpSecurity http) throws Exception { //WebSecurityConfigureAdapter deprecated 이슈로 SecurityFilterChain 사용
        http
                .csrf().disable().headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**","/profile","/sign-up").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
        return http.build();
    }
}
