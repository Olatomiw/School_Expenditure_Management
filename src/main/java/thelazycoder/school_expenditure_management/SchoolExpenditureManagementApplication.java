package thelazycoder.school_expenditure_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class SchoolExpenditureManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolExpenditureManagementApplication.class, args);
    }

}
