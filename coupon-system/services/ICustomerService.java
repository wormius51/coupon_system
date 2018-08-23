package coupon.system.couponsystemweb.services;

import java.util.ArrayList;

import coupon.system.couponsystemweb.entities.Customer;

public interface ICustomerService {
	public ArrayList<Customer> getAllCustomers();
	public Customer getCustomerById(long id);
	public Customer getCustomer(String name,String password);
	public void saveCustomer(Customer customer);
	public void deleteCustomer(long id);
	public boolean nameTaken(String name);
}
