package com.nbcb;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.nbcb.utils.IDWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubboConfiguration
public class OrderWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderWebApplication.class, args);
    }

    @Bean
    public IDWorker getBean() {
        return new IDWorker(1, 1);
    }
}
