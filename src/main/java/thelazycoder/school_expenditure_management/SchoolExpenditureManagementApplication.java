package thelazycoder.school_expenditure_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SchoolExpenditureManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolExpenditureManagementApplication.class, args);
    }

}
