package maven;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by pisarenko on 11.01.2017.
 */
@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        System.out.println("SpringWebSecurityConfiguration");
        http
                .authorizeRequests()
                    .antMatchers("/**")
                    .permitAll().and()
                .csrf()
                    .disable();
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
}
