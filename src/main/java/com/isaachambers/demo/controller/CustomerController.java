package com.isaachambers.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaachambers.demo.domain.Customer;
import com.isaachambers.demo.services.CustomerService;

@RestController
@RequestMapping(path = "customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping("")
	public ResponseEntity<Object> getAllCustomers() {
		try {
			return new ResponseEntity<Object>(customerService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Object> getCustomerById(@PathVariable Long customerId) {
		try {
			Customer c = customerService.findbyId(customerId);
			if (null == c) {
				return new ResponseEntity<Object>("Customer Not Found", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<Object>(c, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("")
	public ResponseEntity<Object> saveNewCustomer(@RequestBody Customer customer) {
		try {
			return new ResponseEntity<Object>(customerService.saveCustomer(customer), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/{customerId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
		try {
			Customer c = customerService.findbyId(customerId);
			if (null == c) {
				return new ResponseEntity<Object>("Customer Not Found", HttpStatus.NOT_FOUND);
			} else {
				c.setAge(customer.getAge());
				c.setCountry(customer.getCountry());
				c.setFirstName(customer.getFirstName());
				c.setSecondName(customer.getSecondName());
				customerService.updateCustomer(customer);
				return new ResponseEntity<Object>(c, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Object> removeCustomer(@PathVariable Long customerId) {

		try {
			Customer c = customerService.findbyId(customerId);
			if (null == c) {
				return new ResponseEntity<Object>("Customer Not Found", HttpStatus.NOT_FOUND);
			} else {
				customerService.deletebyId(customerId);
				return new ResponseEntity<Object>(c, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
