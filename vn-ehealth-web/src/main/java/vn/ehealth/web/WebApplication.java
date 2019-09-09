package vn.ehealth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebApplication {

	@Autowired
    ApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
