package hu.elte.alkfejl.etterem.controllers;

import hu.elte.alkfejl.etterem.entities.Customer;
import hu.elte.alkfejl.etterem.entities.Order;
import hu.elte.alkfejl.etterem.repositories.CustomerRepository;
import hu.elte.alkfejl.etterem.repositories.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    private OrderRepository orderRepo;
    
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<Iterable<Customer>> getAll() {
        return ResponseEntity.ok(customerRepo.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Customer> post(@RequestBody Customer customer) {
        customer.setId(null);
        return ResponseEntity.ok(customerRepo.save(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Integer id) {
        Optional<Customer> oCustomer = customerRepo.findById(id);
        if (!oCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCustomer.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Customer> oCustomer = customerRepo.findById(id);
        if (!oCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customerRepo.delete(oCustomer.get());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Customer> put(@PathVariable Integer id,
                                        @RequestBody Customer customer) {
        Optional<Customer> oCustomer = customerRepo.findById(id);
        if (!oCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        return ResponseEntity.ok(customerRepo.save(customer));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable Integer id) {
        Optional<Customer> oCustomer = customerRepo.findById(id);
        if (!oCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCustomer.get().getOrders());
    }
    
    @PostMapping("/{id}/orders")
    public ResponseEntity<Order> postOrders(@RequestBody Order order) {
       order.setId(null);
       return ResponseEntity.ok(orderRepo.save(order));
    }

}