package wsir.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import wsir.carrental.util.JwtUtil;
import wsir.carrental.util.RedisUtil;

@SpringBootApplication
@EnableConfigurationProperties(JwtUtil.class)
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
