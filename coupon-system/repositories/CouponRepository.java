package coupon.system.couponsystemweb.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.entities.Coupon;
import coupon.system.couponsystemweb.entities.CouponType;
import coupon.system.couponsystemweb.entities.Customer;

public interface CouponRepository extends CrudRepository<Coupon, Long>{
	@Query(value = "select c from Coupon c where "
			+ "(:company is null or :company = c.company) and "
			+ "(:type is null or :type = c.type) and "
			+ "(:price is null or :price > c.price)", nativeQuery = false)
	public ArrayList<Coupon> getCouponsByVariables(@Param("company")Company company,@Param("type")CouponType type,@Param("price")Double price);
	
	@Query(value = "select c from Coupon c where "
			+ "(not exists (select cu from Customer cu where cu = :customer and c member of cu.coupons)) and "
			+ "(:type is null or :type = c.type) and "
			+ "(:price is null or :price > c.price)", nativeQuery = false)
	public ArrayList<Coupon> getCouponsNotPurchased(@Param("customer")Customer customer,@Param("type")CouponType type,@Param("price")Double price);
}
