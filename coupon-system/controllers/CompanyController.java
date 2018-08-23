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

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.entities.Coupon;
import coupon.system.couponsystemweb.entities.CouponType;
import coupon.system.couponsystemweb.helpers.ResponseObject;
import coupon.system.couponsystemweb.services.ICouponService;
/**
 * https://coupon-system.herokuapp.com/company
 * @author Hazor
 *
 */
@RestController
@RequestMapping("company")
public class CompanyController {
	
	@Autowired
	private ICouponService couponService;
	
	private Company getEntity() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);
		return (Company)session.getAttribute("clientEntity");
	}
	/**
	 * Responds with the company object.
	 * method get
	 */
	@RequestMapping(value = "/getCompany", method = RequestMethod.GET)
	public ResponseEntity<ResponseObject> getCompany() {
		ArrayList<Company> company = new ArrayList<>();
		company.add(getEntity());
		ResponseObject ro = new ResponseObject(true, "OK", company);
		return new ResponseEntity<ResponseObject>(ro,HttpStatus.OK);
	}
	/**
	 * "/getCoupons"
	 * method get
	 * @param price
	 * @param couponType
	 * @param start_date
	 * @param end_date
	 */
	@RequestMapping(value = "/getCoupons", method = RequestMethod.GET)
	public ResponseEntity<ResponseObject> getCoupons(@RequestParam String price, @RequestParam String couponType, @RequestParam String startDate, @RequestParam String endDate) {
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
		ArrayList<Coupon> coupons = couponService.getCouponsOfCompany(getEntity(), _startDate, _endDate, _couponType, _price);
		ResponseObject ro = new ResponseObject(true, "OK", coupons);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.OK);
	}
	/**
	 * "/getCouponById"
	 * method get
	 * @param id
	 */
	@RequestMapping(value = "/getCouponById", method = RequestMethod.GET)
	public ResponseEntity<ResponseObject> getCouponById(@RequestParam long id) {
		Coupon coupon = couponService.getCouponById(id);
		if (coupon != null && coupon.getCompany().equals(getEntity())) {
			ArrayList<Coupon> coupons = new ArrayList<>();
			coupons.add(coupon);
			ResponseObject ro = new ResponseObject(true, "OK", coupons);
			return new ResponseEntity<ResponseObject> (ro,HttpStatus.OK);
		}
		ResponseObject ro = new ResponseObject(false, "Your company does not have a coupon with this id.", null);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.NOT_FOUND);
	}
	/**
	 * "/createCoupon"
	 * method post
	 * @param coupon
	 */
	@RequestMapping(value = "/createCoupon" , method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> createCoupon(@RequestBody Coupon coupon) {
		ResponseObject ro;
		if (coupon == null) {
			ro = new ResponseObject(false, "Invalid", null);
			return new ResponseEntity<ResponseObject> (ro, HttpStatus.BAD_REQUEST);
		}
		coupon.setCompany(getEntity());
		couponService.saveCoupon(coupon);
		ro = new ResponseObject(true, "Created coupon.", null);
		return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
	}
	
	/**
	 * "/updateCoupon"
	 * method post
	 * @param coupon
	 * @return
	 */
	@RequestMapping(value = "/updateCoupon", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> updateCoupon (@RequestBody Coupon coupon) {
		ResponseObject ro;
		if (coupon == null) {
			ro = new ResponseObject(false, "Invalid", null);
			return new ResponseEntity<ResponseObject> (ro, HttpStatus.BAD_REQUEST);	
		}
		Coupon databaseCoupon = couponService.getCouponById(coupon.getId());
		if (databaseCoupon != null) {
		coupon.setCompany(databaseCoupon.getCompany());
		} else {
			ro = new ResponseObject(false, "Your company does not have a coupon with this id.", null);
			return new ResponseEntity<ResponseObject> (ro,HttpStatus.NOT_FOUND);
		}
		if (coupon.getCompany().equals(getEntity())) {
			couponService.saveCoupon(coupon);
			ro = new ResponseObject(true, "Updated coupon", null);
			return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
		}
		ro = new ResponseObject(false, "Your company does not have a coupon with this id.", null);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.NOT_FOUND);
	}
	/**
	 * "/deleteCoupon"
	 * method delete
	 * @param id
	 */
	@RequestMapping(value = "/deleteCoupon", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseObject> deleteCoupon(@RequestParam long id) {
		ResponseObject ro;
		Coupon coupon = couponService.getCouponById(id);
		if (coupon != null && coupon.getCompany().equals(getEntity())) {
			couponService.deleteCoupon(id);
			ro = new ResponseObject(true, "Coupon deleted.", null);
			return new ResponseEntity<ResponseObject> (ro, HttpStatus.OK);
		}
		ro = new ResponseObject(false, "Your company does not have a coupon with this id.", null);
		return new ResponseEntity<ResponseObject> (ro,HttpStatus.NOT_FOUND);
	}
}
