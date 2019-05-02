package com.isaachambers.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaachambers.demo.domain.Customer;
import com.isaachambers.demo.model.KafkaMessage;
import com.isaachambers.demo.model.MessageType;
import com.isaachambers.demo.repository.CustomerRepository;

@Service
public class CustomerService {
	@Value("${kafka.search.topic}")
	private String searchTopic;

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	private CustomerRepository customerRepository;
	private KafkaTemplate<String, String> kafkaTemplate;
	private ObjectMapper mapper;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, KafkaTemplate<String, String> kafkaTemplate,
			ObjectMapper mapper) {
		this.customerRepository = customerRepository;
		this.kafkaTemplate = kafkaTemplate;
		this.mapper = mapper;
	}

	public Customer saveCustomer(Customer customer) throws Exception {
		try {
			Customer c = customerRepository.save(customer);
			KafkaMessage kfm = new KafkaMessage(MessageType.NEW_CUSTOMER, "New Customer Created",
					c.getCustomerId() + "");
			kafkaTemplate.send(searchTopic, mapper.writeValueAsString(kfm));
			return c;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
			throw ex;
		}
	}

	public Customer findbyId(Long id) {
		return customerRepository.findById(id).orElse(null);
	}

	public Customer updateCustomer(Customer customer) throws Exception {
		try {
			Customer c = customerRepository.save(customer);
			KafkaMessage kfm = new KafkaMessage(MessageType.UPDATE_CUSTOMER, "Customer Updated",
					c.getCustomerId() + "");
			kafkaTemplate.send(searchTopic, mapper.writeValueAsString(kfm));
			return c;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
			throw ex;
		}
	}

	public void deletebyId(Long customerId) throws Exception {
		try {
			customerRepository.deleteById(customerId);
			KafkaMessage kfm = new KafkaMessage(MessageType.DELETE_CUSTOMER, "Customer Deleted", customerId + "");
			kafkaTemplate.send(searchTopic, mapper.writeValueAsString(kfm));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
			throw ex;
		}
	}

	public List<Customer> findAll() {
		List<Customer> allCustomers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> {
			allCustomers.add(customer);
		});
		return allCustomers;
	}

}
