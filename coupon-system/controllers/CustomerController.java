package coupon.system.couponsystemweb.controllers;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import coupon.system.couponsystemweb.entities.Coupon;
import coupon.system.couponsystemweb.entities.CouponType;
import coupon.system.couponsystemweb.entities.Customer;
import coupon.system.couponsystemweb.helpers.ResponseObject;
import coupon.system.couponsystemweb.services.ICouponService;
import coupon.system.couponsystemweb.services.ICustomerService;
/**
 * https://coupon-system.herokuapp.com/customer
 * @author Hazor
 *
 */
@RestController
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private ICouponService couponService;
	@Autowired
	private ICustomerService customerService;
	
	private Customer getEntity() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);
		return (Customer)session.getAttribute("clientEntity");
	}
	
	private void updateEntity() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);
		session.setAttribute("clientEntity", customerService.getCustomerById(getEntity().getId()));
	}
	
	/**
	 * "/getCustomer"
	 * method get
	 * Responds with the customer object.
	 */
	@RequestMapping( value = "/getCustomer", method = RequestMethod.GET )
	public ResponseEntity<ResponseObject> getCustomer() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(getEntity());
		ResponseObject ro = new ResponseObject(true, "OK", customers);
		return new ResponseEntity<ResponseObject>(ro,HttpStatus.OK);
	}
	/**
	 * "/getPurchaseHistory"
	 * method get
	 * @param price
	 * @param couponType
	 * @param start_date
	 * @param end_date
	 */
	@RequestMapping( value = "/getPurchaseHistory", method = RequestMethod.GET )
	public ResponseEntity<ResponseObject> getPurchaseHistory(@RequestParam String price, @RequestParam String couponType, @RequestParam String startDate, @RequestParam String endDate) {
		Double _price = null;
		CouponType _couponType = null;
		Date _startDate = null;
		Date _endDate = null;
		try {
			_price = Double.valueOf(price);
		} catch (Exception e) {
		}
		try {
			_couponType = CouponType.valueOf(couponType);
		} catch (Exception e) {
		}
		try {
			_startDate = Date.valueOf(startDate);
		} catch (Exception e) {
		}
		try {
			_endDate = Date.valueOf(endDate);
		} catch (Exception e) {
		}
		ArrayList<Coupon> coupons = couponService.getCouponsOfCustomer(getEntity(), _startDate, _endDate, _couponType, _price);
		ResponseObject ro = new ResponseObject(true, "OK", coupons);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.OK);
	}
	/**
	 * "/getAllCoupons"
	 * method get
	 * @param price
	 * @param couponType
	 * @param start_date
	 * @param end_date
	 */
	@RequestMapping( value = "/getAllCoupons", method = RequestMethod.GET )
	public ResponseEntity<ResponseObject> getAllCoupons(@RequestParam String price, @RequestParam String couponType, @RequestParam String startDate, @RequestParam String endDate) {
		Double _price = null;
		CouponType _couponType = null;
		Date _startDate = null;
		Date _endDate = null;
		try {
			_price = Double.valueOf(price);
		} catch (Exception e) {
		}
		try {
			_couponType = CouponType.valueOf(couponType);
		} catch (Exception e) {
		}
		try {
			_startDate = Date.valueOf(startDate);
		} catch (Exception e) {
		}
		try {
			_endDate = Date.valueOf(endDate);
		} catch (Exception e) {
		}
		ArrayList<Coupon> coupons = couponService.getCouponsNotOfCustomer(getEntity(), _startDate, _endDate, _couponType, _price);
		updateEntity();
		ResponseObject ro = new ResponseObject(true, "OK", coupons);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.OK);
	}
	/**
	 * "/purchaseCoupon"
	 * method post
	 * @param couponId the id of the coupon.
	 * @return
	 */
	@RequestMapping( value = "/purchaseCoupon", method = RequestMethod.POST )
	public ResponseEntity<ResponseObject> purchaseCoupon(@RequestBody Coupon coupon) {
		long couponId = coupon.getId();
		ResponseObject ro;
		if (couponId == 0) {
			ro = new ResponseObject(false, "Invalid", null);
			return new ResponseEntity<ResponseObject> (ro,HttpStatus.BAD_REQUEST);
		}
		Coupon databaseCoupon = couponService.getCouponById(couponId);
		databaseCoupon.setAmount(databaseCoupon.getAmount() - 1);
		getEntity().getCoupons().add(databaseCoupon);
		couponService.saveCoupon(databaseCoupon);
		customerService.saveCustomer(getEntity());
		ro = new ResponseObject(true, "Purchased coupon", null);
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
}
