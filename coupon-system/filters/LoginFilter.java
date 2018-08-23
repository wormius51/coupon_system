package coupon.system.couponsystemweb.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;

import coupon.system.couponsystemweb.helpers.ClientType;

@Order(1)
public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("login filter");
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		if (session == null) {
			System.out.println("no session");
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/public/checkSession");
			return;
		}
		String path = ((HttpServletRequest) request).getRequestURI();
		String controller = path.split("/")[1];
		if (controller.toLowerCase().equals(((ClientType)session.getAttribute("clientType")).toString().toLowerCase()))
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
