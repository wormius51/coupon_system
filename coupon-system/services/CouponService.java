package coupon.system.couponsystemweb.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.entities.Coupon;
import coupon.system.couponsystemweb.entities.CouponType;
import coupon.system.couponsystemweb.entities.Customer;
import coupon.system.couponsystemweb.repositories.CouponRepository;
@Service
public class CouponService implements ICouponService {
	
	@Autowired
	CouponRepository repository;
	
	@Override
	public ArrayList<Coupon> getAllCoupons() {
		return repository.getCouponsByVariables(null, null, null);
	}

	@Override
	public ArrayList<Coupon> getAllCoupons(Date startDate, Date endDate, CouponType type, Double price) {
		
		ArrayList<Coupon> coupons = repository.getCouponsByVariables(null, type, price);
		return filterByDate(coupons, startDate, endDate);
	}

	@Override
	public ArrayList<Coupon> getCouponsOfCompany(Company company) {
		return repository.getCouponsByVariables(company, null, null);
	}

	@Override
	public ArrayList<Coupon> getCouponsOfCompany(Company company, Date startDate, Date endDate, CouponType type,
			Double price) {
		ArrayList<Coupon> coupons = repository.getCouponsByVariables(company, type, price);
		return filterByDate(coupons, startDate, endDate);
	}
	
	private ArrayList<Coupon> filterByDate(ArrayList<Coupon> coupons,Date startDate, Date endDate) {
		Stream<Coupon> stream = coupons.stream();
		List<Coupon> list = stream.filter(
				c->((startDate == null || c.getStartDate().after(startDate) || c.getStartDate().equals(startDate)) && 
				(endDate == null || c.getEndDate().after(endDate) || c.getEndDate().equals(endDate)))
				).collect(Collectors.toList());
		return new ArrayList<>(list);
	}

	@Override
	public ArrayList<Coupon> getCouponsOfCustomer(Customer customer) {
		ArrayList<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : customer.getCoupons())
			coupons.add(coupon);
		return coupons;
	}

	@Override
	public ArrayList<Coupon> getCouponsOfCustomer(Customer customer, Date startDate, Date endDate, CouponType type,
			Double price) {
		Set<Coupon> coupons = customer.getCoupons();
		ArrayList<Coupon> filteredCoupons = new ArrayList<>();
		for (Coupon coupon : coupons) {
			if((startDate == null || startDate.after(coupon.getStartDate())) &&
					(endDate == null || endDate.after(coupon.getEndDate())) &&
					(type == null || type.equals(coupon.getType())) &&
					(price == null || price < coupon.getPrice()))
					filteredCoupons.add(coupon);
		}
		return filteredCoupons;
	}

	@Override
	public void saveCoupon(Coupon coupon) {
		repository.save(coupon);

	}

	@Override
	public void deleteCoupon(long id) {
		repository.deleteById(id);
	}

	@Override
	public Coupon getCouponById(long id) {
		Optional<Coupon> coupon = repository.findById(id);
		if (coupon.isPresent())
			return coupon.get();
		return null;
	}

	@Override
	public ArrayList<Coupon> getCouponsNotOfCustomer(Customer customer, Date startDate, Date endDate, CouponType type,
			Double price) {
		ArrayList<Coupon> coupons = repository.getCouponsNotPurchased(customer, type, price);
		return filterByDate(coupons, startDate, endDate);
	}

}
