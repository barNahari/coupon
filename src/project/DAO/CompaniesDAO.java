package project.DAO;

import project.beans.Company;
import project.exceptions.CompanyException;


import java.sql.SQLException;
import java.util.ArrayList;


public interface CompaniesDAO {
    Company company = null;

    void addCategory(String name);

    boolean isCompanyExistsByID(int companyId);

    boolean isCompanyExists(String email, String password) throws CompanyException;

    void addCompany(Company company) throws CompanyException, SQLException;

    void updateCompany(Company company) throws CompanyException;

    void deleteCompany(int companyID) throws CompanyException, SQLException;

    ArrayList<Company> getAllCompanies();

    Company getOneCompany(int companyID) throws CompanyException;

    int getCompanyID(String email, String password) throws CompanyException;


}
