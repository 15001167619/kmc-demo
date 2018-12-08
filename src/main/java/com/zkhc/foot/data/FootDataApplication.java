package com.zkhc.foot.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 武海升
 * @date 2018/12/6 10:30
 */
@SpringBootApplication
@Slf4j
public class FootDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootDataApplication.class, args);
        log.info("FootDataApplication Application start-up is success!");
    }

}
