package org.zerock.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.filter.SessionCreatorFilter;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final DataSource dataSource;

    final ZerockUsersService zerockUsersService;

    public SecurityConfig(DataSource dataSource, ZerockUsersService zerockUsersService) {
        this.dataSource = dataSource;
        this.zerockUsersService = zerockUsersService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("security config...........");

        /*http.authorizeRequests().antMatchers("/guest/**").permitAll();
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");*/

        http.authorizeRequests()
            .antMatchers("/boards/list").permitAll()
            .antMatchers("/boards/register").hasAnyRole("BASIC", "MANAGER", "ADMIN");

        http.formLogin().loginPage("/login").successHandler(new LoginSuccessHandler());

        http.exceptionHandling().accessDeniedPage("/accessDenied");

        http.logout().logoutUrl("/logout").invalidateHttpSession(true);

        //http.userDetailsService(zerockUsersService);

        http
            .rememberMe()
            .key("zerock")
            .userDetailsService(zerockUsersService)
            .tokenRepository(getJDBCRepository())
            .tokenValiditySeconds(60 * 60 * 24); // 24 Hours

    }

    private PersistentTokenRepository getJDBCRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        log.info("build Auth global...........");

        auth.userDetailsService(zerockUsersService).passwordEncoder(passwordEncoder());

    }
}
