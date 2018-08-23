package coupon.system.couponsystemweb.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
	@Id
	private long id;
	@Column(nullable = false)
	private String name;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "CUSTOMER_COUPON",joinColumns = @JoinColumn(name = "customerId")
	,inverseJoinColumns = @JoinColumn(name = "couponId"))
	private Set<Coupon> coupons;
	
	public Customer() {
		//coupons = new HashSet<>();
	}

	public Customer(long id, String name, String password, Set<Coupon> coupons) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.coupons = (Set<Coupon>) coupons;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + ", coupons=" + coupons + "]";
	}
}
