package coupon.system.couponsystemweb.services;

import java.sql.Date;
import java.util.ArrayList;

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.entities.Coupon;
import coupon.system.couponsystemweb.entities.CouponType;
import coupon.system.couponsystemweb.entities.Customer;

public interface ICouponService {
	public Coupon getCouponById(long id);
	public ArrayList<Coupon> getAllCoupons();
	public ArrayList<Coupon> getAllCoupons(Date startDate,Date endDate,CouponType type,Double price);
	public ArrayList<Coupon> getCouponsOfCompany(Company company);
	public ArrayList<Coupon> getCouponsOfCompany(Company company,Date startDate,Date endDate,CouponType type,Double price);
	public ArrayList<Coupon> getCouponsOfCustomer(Customer customer);
	public ArrayList<Coupon> getCouponsOfCustomer(Customer customer,Date startDate,Date endDate,CouponType type,Double price);
	public ArrayList<Coupon> getCouponsNotOfCustomer(Customer customer,Date startDate,Date endDate,CouponType type,Double price);
	public void saveCoupon(Coupon coupon);
	public void deleteCoupon(long id);
}
