package nyo.lu.appdeployer.jee;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.springframework.boot.SpringApplication.run;

@EnableFeignClients
@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        run(Launcher.class, args);
    }

}
