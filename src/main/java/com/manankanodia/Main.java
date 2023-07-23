package com.manankanodia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

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

    record NewCustomerRequest(String name, String email, Integer age) {}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setAge(request.age);
        customer.setName(request.name);
        customer.setEmail(request.email);
        customerRepository.save(customer);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
        customer.setEmail(request.email);
        customerRepository.save(customer);
        }
    }
    
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }
}
