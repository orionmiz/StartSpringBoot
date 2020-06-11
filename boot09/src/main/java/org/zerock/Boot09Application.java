package org.zerock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Boot09Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot09Application.class, args);
    }

}
