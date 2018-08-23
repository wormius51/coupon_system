package coupon.system.couponsystemweb.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coupon.system.couponsystemweb.entities.Customer;
import coupon.system.couponsystemweb.repositories.CustomerRepository;
@Service
public class CustomerService implements ICustomerService{
	
	@Autowired
	CustomerRepository repository;
	
	@Override
	public ArrayList<Customer> getAllCustomers() {
		return (ArrayList<Customer>) repository.findAll();
	}

	@Override
	public Customer getCustomerById(long id) {
		Optional<Customer> customer = repository.findById(id);
		if (customer.isPresent())
			return customer.get();
		return null;
	}

	@Override
	public Customer getCustomer(String name, String password) {
		Optional<Customer> customer = repository.getCustomer(name, password);
		if (customer.isPresent())
			return customer.get();
		return null;
	}

	@Override
	public void saveCustomer(Customer customer) {
		repository.save(customer);
	}

	@Override
	public void deleteCustomer(long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean nameTaken(String name) {
		Optional<Customer> op = repository.getCustomerByName(name);
		return op.isPresent();
	}

}