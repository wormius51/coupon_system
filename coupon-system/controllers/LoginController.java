package coupon.system.couponsystemweb.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.entities.Customer;
import coupon.system.couponsystemweb.helpers.ClientType;
import coupon.system.couponsystemweb.helpers.ResponseObject;
import coupon.system.couponsystemweb.services.ICompanyService;
import coupon.system.couponsystemweb.services.ICustomerService;
/**
 * https://coupon-system.herokuapp.com/login
 * @author Hazor
 *
 */
@RestController
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private ICustomerService customerService;
	@Value("${couponSystem.admin.name}")
	private String adminName;
	@Value("${couponSystem.admin.password}")
	private String adminPassword;
	/**
	 * ""
	 * method post
	 * Creates a session for the user if details are correct.
	 * @param name the name of the user
	 * @param password the password of the user
	 * @param clientType the client type (Admin,Company or Customer)
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> logIn(@RequestParam String name, @RequestParam String password, @RequestParam ClientType clientType) {
		if (clientType == null) {
			ResponseObject ro = new ResponseObject(false, "Please choose a client type.", null);
			return new ResponseEntity<ResponseObject> (ro,HttpStatus.BAD_REQUEST);
		}
		boolean success = false;
		Object clientEntity = null;
		switch (clientType) {
		case Admin:
			if (name.equals(adminName) && password.equals(adminPassword)) {
				success = true;
			}
			break;
		case Company:
			Company company = companyService.getCompany(name, password);
			if (company != null) {
				success = true;
				clientEntity = company;
			}
			break;
		case Customer:
			Customer customer = customerService.getCustomer(name, password);
			if (customer != null) {
				success = true;
				clientEntity = customer;
			}
			break;
		default:
			break;
		}
		
		if (success) {
			HttpSession session = getSession();
			session.setAttribute("clientEntity", clientEntity);
			session.setAttribute("clientType", clientType);
			ResponseObject ro = new ResponseObject(true, "logged in", null);
			return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
		}
		ResponseObject ro = new ResponseObject(false, "Could not log in.", null);
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private HttpSession getSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true);
	}
}
