package coupon.system.couponsystemweb.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import coupon.system.couponsystemweb.entities.Customer;
import coupon.system.couponsystemweb.helpers.ClientType;
import coupon.system.couponsystemweb.helpers.ResponseObject;
import coupon.system.couponsystemweb.services.ICustomerService;

@RestController
@RequestMapping("public")
public class PublicController {
	
	@Autowired
	private ICustomerService customerService;
	
	private HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);
		return session;
	}
	
	@RequestMapping("checkSession")
	public ResponseEntity<ResponseObject> checkSession() {
		HttpSession session = getSession();
		ResponseObject ro;
		if (session == null) {
			ro = new ResponseObject(false, "ERROR: Session has been timed-out.", null);
			return new ResponseEntity<ResponseObject>(ro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ClientType clientType = (ClientType) session.getAttribute("clientType");
		ro = new ResponseObject(true, clientType.toString(), null);
		return new ResponseEntity<ResponseObject>(ro, HttpStatus.OK);
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> register(@RequestBody Customer customer) {
		ResponseObject ro;
		
		String errMessage = "";
		if ( customerService.getCustomerById(customer.getId()) != null ) {
			errMessage += "A customer with this id allready exists. ";
		}
		
		if (customerService.nameTaken(customer.getName())) {
			errMessage += "This name is allready taken. Try to be more creative will you !?";
		}
		
		try {
			customerService.saveCustomer(customer);
			ro = new ResponseObject(true, "OK", null);
		} catch (Exception e) {
			ro = new ResponseObject(false, errMessage, null);
		}
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
	
	@RequestMapping("logout")
	public ResponseEntity<ResponseObject> logout() {
		HttpSession session = getSession();
		ResponseObject ro;
		if (session != null)
			session.invalidate();
		ro = new ResponseObject(true, "logout", null);
		return new ResponseEntity<ResponseObject>(ro, HttpStatus.OK);
	}
}
