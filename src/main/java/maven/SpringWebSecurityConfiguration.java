package maven;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Created by pisarenko on 11.01.2017.
 */
@Configuration
// @EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SpringWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        System.out.println("SpringWebSecurityConfiguration");
        http
                .httpBasic()
                    .disable()
                .authorizeRequests()
                    .anyRequest()
                    .permitAll()
        ;
        /*
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable();
        http.authorizeRequests().anyRequest().permitAll();
        */
        //http.authorizeRequests().anyRequest().permitAll();
        /*
                .authorizeRequests()
                    .antMatchers("/**")
                    .permitAll();
                    */
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}
