package com.example.myspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login, password, 'true' from user where login=?")
                .authoritiesByUsernameQuery("select login, authority from user where login=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**").permitAll()
                .antMatchers("/**").hasAnyRole("ADMIN","STUDENT")
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .loginProcessingUrl("/perform-login")
                .usernameParameter("user")
                .passwordParameter("pass")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        /*http.authorizeRequests()
                .antMatchers("/students/student-progress").hasAnyRole("ADMIN","STUDENT")
                .antMatchers("/students").hasAnyRole("ADMIN","STUDENT")
                .antMatchers("/students/**").hasRole("ADMIN")
                .antMatchers("/disciplines/**").hasRole("ADMIN")
                .antMatchers("/semesters/**").hasRole("ADMIN")
                .antMatchers("/**").hasAnyRole("ADMIN","STUDENT")
                .and().formLogin();

         */
    }
}
