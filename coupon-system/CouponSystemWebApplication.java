package coupon.system.couponsystemweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import coupon.system.couponsystemweb.filters.LoginFilter;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class CouponSystemWebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CouponSystemWebApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<LoginFilter> exampleFilter() {
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		
		filter.setFilter(new LoginFilter());
		filter.addUrlPatterns("/admin/*","/company/*","/customer/*");
		
		return filter;
	}
	
}
