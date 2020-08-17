package com.xagu.studio.studiosystem;

import com.xagu.studio.studiosystem.config.UniqueNameGenerator;
import com.xagu.studio.studiosystem.mirai.listener.FriendListener;
import com.xagu.studio.studiosystem.mirai.star.RobotStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(nameGenerator = UniqueNameGenerator.class)
public class StudioSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(StudioSystemApplication.class, args);
        RobotStar.star();
    }

}
