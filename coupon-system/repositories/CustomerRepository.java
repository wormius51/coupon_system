package coupon.system.couponsystemweb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import coupon.system.couponsystemweb.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	@Query(value = "select * from CUSTOMER where name = :name and password = :password", nativeQuery = true)
	public Optional<Customer> getCustomer(@Param("name")String name, @Param("password")String password);
	@Query(value = "select * from CUSTOMER where name = :name",nativeQuery = true)
	public Optional<Customer> getCustomerByName(@Param("name") String name);
}