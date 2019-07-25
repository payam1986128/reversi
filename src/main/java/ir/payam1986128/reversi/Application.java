package ir.payam1986128.reversi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/23 - 08:29 PM
 */
@SpringBootApplication
@ComponentScan({"ir.payam1986128.reversi"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
