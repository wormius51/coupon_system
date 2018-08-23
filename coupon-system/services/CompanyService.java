package coupon.system.couponsystemweb.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coupon.system.couponsystemweb.entities.Company;
import coupon.system.couponsystemweb.repositories.CompanyRepository;
@Service
public class CompanyService implements ICompanyService {
	@Autowired
	private CompanyRepository repository;
	
	@Override
	public ArrayList<Company> getAllCompanies() {
		return (ArrayList<Company>) repository.findAll();
	}

	@Override
	public Company getCompanyById(long id) {
		Optional<Company> company = repository.findById(id);
		if (company.isPresent())
			return company.get();
		return null;
	}

	@Override
	public void saveCompany(Company company) {
		repository.save(company);
	}

	@Override
	public void deleteCompany(long id) {
		repository.deleteById(id);
	}

	@Override
	public Company getCompany(String name, String password) {
		Optional<Company> company = repository.getComapny(name, password);
		if (company.isPresent())
			return company.get();
		return null;
	}

}
