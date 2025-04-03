package com.d288.backendproject.bootstrap;

import com.d288.backendproject.dao.CustomerRepository;
import com.d288.backendproject.dao.DivisionRepository;
import com.d288.backendproject.entities.Customer;
import com.d288.backendproject.entities.Division;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;
    private final Random random = new Random();
    private List<Division> divisions; // Store the divisions once fetched


    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository){
        this.divisionRepository = divisionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception{
        // Fetch divisions with IDs between 2 and 104
        divisions = divisionRepository.findDivisionsInRange(2L, 104L);

        // No execution if no divisions exist
        if (divisions == null || divisions.isEmpty()) {
            throw new IllegalStateException("Error: No divisions found!");
        }
        if (customerRepository.count() <= 1) {
            // Create customers with random divisions
            Customer jessie = new Customer("Jessie", "James", "2204 W. 4th ave.", "(928)-224-2222", "85365", getRandomDivision(),new Date());
            Customer mary = new Customer("Mary", "Stew", "1708 N. 18th ave.", "(408)-434-1162", "92876", getRandomDivision(), new Date());
            Customer brighton = new Customer("Brighton", "Ray", "4320 S. 24th ave.", "(729)-132-4520", "73329", getRandomDivision(), new Date());
            Customer john = new Customer("John", "Smith", "2095 E. 4th ave.", "(898)-215-9876", "43311", getRandomDivision(), new Date());
            Customer larry = new Customer("Larry", "Jones", "3397 N. 10th ave.", "(808)-345-1777", "61224", getRandomDivision(), new Date());

            // Save customers
            customerRepository.save(jessie);
            customerRepository.save(mary);
            customerRepository.save(brighton);
            customerRepository.save(john);
            customerRepository.save(larry);

            System.out.println("From BootStrap");
            System.out.println("Number of Customers: " + customerRepository.count());
        }
    }

    // Updates customer to random division
    private Division getRandomDivision() {
        return divisions.get(random.nextInt(divisions.size()));
    }
}
