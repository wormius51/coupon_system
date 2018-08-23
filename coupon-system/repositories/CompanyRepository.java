package coupon.system.couponsystemweb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import coupon.system.couponsystemweb.entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
	@Query(value = "select * from COMPANY where name = :name and password = :password", nativeQuery = true)
	public Optional<Company> getComapny(@Param("name")String name,@Param("password") String password);
}
