package com.manankanodia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet() {
        return new GreetResponse(
                "Hello",
                List.of("Python", "Java"),
                new Person ("manan", 30, 10000)
        );
    }

    record Person(String name, int age, double savings){}

    record GreetResponse(
            String greet,
            List<String> favLanguages,
            Person person
    ){}

    @GetMapping
    public List<Customer> getCustomers() {
//        return List.of();
        return customerRepository.findAll();
    }

}
