package kr.or.connect.guestbook.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "kr.or.connect.guestbook.dao",  "kr.or.connect.guestbook.service"})
@Import({ DBConfig.class })
public class ApplicationConfig {

}