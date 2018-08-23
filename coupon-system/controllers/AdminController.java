package coupon.system.couponsystemweb.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.entities.Customer;
import coupon.system.couponsystemweb.helpers.ResponseObject;
import coupon.system.couponsystemweb.services.ICompanyService;
import coupon.system.couponsystemweb.services.ICustomerService;
/**
 * https://coupon-system.herokuapp.com/admin
 * @author Hazor
 *
 */
@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private ICustomerService customerService;
	/**
	 * "/getAllCompanies"
	 * method get
	 * no argument or body
	 */
	@RequestMapping("/getAllCompanies")
	public ResponseEntity<ResponseObject> getAllCompanies () {
		ArrayList<Company> companies = companyService.getAllCompanies();
		ResponseObject ro = new ResponseObject(true, "OK", companies);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.OK);
	}
	/**
	 * "/getAllCustomers/
	 * method get
	 * no arguments or body
	 */
	@RequestMapping("/getAllCustomers")
	public ResponseEntity<ResponseObject> getAllCustomers() {
		ArrayList<Customer> customers = customerService.getAllCustomers();
		ResponseObject ro = new ResponseObject(true, "OK", customers);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.OK);
	}
	
	@RequestMapping("/getCustomerById")
	public ResponseEntity<ResponseObject> getCustomerById(@RequestParam long id) {
		Customer customer = customerService.getCustomerById(id);
		ResponseObject ro;
		if (customer == null) {
			ro = new ResponseObject(false, "not found", null);
			return new ResponseEntity<ResponseObject>(ro, HttpStatus.NOT_FOUND);
		}
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(customer);
		ro = new ResponseObject(true, "found", customers);
		return new ResponseEntity<ResponseObject>(ro, HttpStatus.OK);
	}
	
	@RequestMapping("/getCompanyById")
	public ResponseEntity<ResponseObject> getCompanyById(@RequestParam long id) {
		Company company = companyService.getCompanyById(id);
		ResponseObject ro;
		if (company == null) {
			ro = new ResponseObject(false, "not found", null);
			return new ResponseEntity<ResponseObject>(ro, HttpStatus.NOT_FOUND);
		}
		ArrayList<Company> companies = new ArrayList<>();
		companies.add(company);
		ro = new ResponseObject(true, "found", companies);
		return new ResponseEntity<ResponseObject>(ro, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/addCompany", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> addCompany (@RequestBody Company company) {
		companyService.saveCompany(company);
		ResponseObject ro = new ResponseObject(true, "OK", null);
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
	
	/**
	 * "/addCompany" or "/updateCompany", no difference for admin.
	 * method post
	 * @param company, a body which contains a name, a password and an email.
	 */
	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> updateCompany (@RequestBody Company company) {
		companyService.saveCompany(company);
		ResponseObject ro = new ResponseObject(true, "OK", null);
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
	
	
	/**
	 * "/deleteCompany"
	 * method delete
	 * @param id a number that is the company id.
	 * @return
	 */
	@RequestMapping(value = "/deleteCompany", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseObject> deleteCompany (@RequestParam long id) {
		ResponseObject ro;
		try {
			companyService.deleteCompany(id);
			ro = new ResponseObject(true, "OK", null);
		} catch (Exception e) {
			ro = new ResponseObject(false, "failed", null);
		}
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
	/**
	 * "/addCustomer" or "/updateCustomer", no difference for admin.
	 * method post
	 * @param customer, a body which contains a name and a password.
	 */
	@RequestMapping(value = {"/addCustomer","/updateCustomer"}, method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> addCompany (@RequestBody Customer customer) {
		ResponseObject ro;
		try {
			customerService.saveCustomer(customer);
			ro = new ResponseObject(true, "OK", null);
		} catch (Exception e) {
			ro = new ResponseObject(false, "failed", null);
		}
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
	/**
	 * "/deleteCustomer"
	 * method delete
	 * @param id a number that is the customer id.
	 * @return
	 */
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseObject> deleteCustomer (@RequestParam long id) {
		ResponseObject ro;
		try {
			customerService.deleteCustomer(id);
			ro = new ResponseObject(true, "OK", null);
		} catch (Exception e) {
			ro = new ResponseObject(false, "failed", null);
		}
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
}
