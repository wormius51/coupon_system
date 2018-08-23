package coupon.system.couponsystemweb.services;

import java.util.ArrayList;

import coupon.system.couponsystemweb.entities.Company;

public interface ICompanyService {
	public ArrayList<Company> getAllCompanies();
	public Company getCompanyById(long id);
	public Company getCompany(String name,String password);
	public void saveCompany(Company company);
	public void deleteCompany(long id);
}