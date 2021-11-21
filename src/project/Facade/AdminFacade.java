package project.Facade;


import project.DAO.CompaniesDAO;
import project.DAO.CustomerDAO;
import project.DBDAO.CompaniesDBDAO;

import project.DBDAO.CustomerDBDAO;
import project.beans.Company;
import project.beans.Coupon;
import project.beans.Customer;
import project.exceptions.CompanyException;
import project.exceptions.CouponException;
import project.exceptions.CustomerException;
import project.exceptions.UserException;


import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {

    CompaniesDAO companiesDAO = new CompaniesDBDAO();
    CustomerDAO customerDAO = new CustomerDBDAO();
    AdminFacade adminFacade;


    public AdminFacade() {

    }

    /**
     *
     * @param email admin email
     * @param password admin password
     * @return validation if connected
     * @throws UserException thrown if email or password are incorrect
     */
    public  boolean login(String email, String password) throws UserException {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param company get an company object
     * @throws CompanyException thrown if comapny does not exists
     */
    public void addCompany(Company company) throws CompanyException {
        try {
                companiesDAO.addCompany(company);
        } catch (SQLException|CompanyException throwables) {
            throw new CompanyException("company is already exists");
        }

    }

    /**
     *
     * @param company get an company object
     * @throws CompanyException thrown if company does not exists
     */
    public void updateCompany(Company company) throws CompanyException {
        try {
            companiesDAO.updateCompany(company);
        } catch (CompanyException e) {
            throw new CompanyException("company is not exists");
        }

    }

    /**
     *
     * @param companyID get company id
     * @throws CompanyException thrown if company does not exists
     */
    public void deleteCompany(int companyID) throws CompanyException {
        try {
            companiesDAO.deleteCompany(companyID);
        } catch (CompanyException e) {
            throw new CompanyException("company dose not exists");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /**
     *
     * @param companyID get company id
     * @return company object
     * @throws CompanyException thrown if company does not exists
     */
    public Company getOneCompany(int companyID) throws CompanyException {
        Company myCompany = null;
        try {
            myCompany = companiesDAO.getOneCompany(companyID);
        } catch (CompanyException e) {
            throw new CompanyException("the company does not exists");
        }

        return myCompany;
    }

    /**
     * @return array list of companies
     */
    public List<Company> getAllCompanies()  {
        List<Company> companies = null;

            companies = companiesDAO.getAllCompanies();


        return companies;
    }

    /**
     *
     * @param customer get a customer object
     * @throws CustomerException thrown if customer exists
     */
    public void addCustomer(Customer customer) throws CustomerException {
        customerDAO.addCustomer(customer);

    }

    /**
     *
     * @param customer get a customer object
     * @throws CustomerException thrown is customer dose not exists
     */
    public void updateCustomer(Customer customer) throws CustomerException {
        customerDAO.updateCustomer(customer);
    }

    /**
     *
     * @param customerID get customer id
     * @throws CustomerException thrown if customer dose not exists
     */
    public void deleteCustomer(int customerID) throws CustomerException {
        customerDAO.deleteCustomer(customerID);
    }

    /**
     *
     * @return array list of customers
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        return customers;
    }

    /**
     *
     * @param customerID get customer id
     * @return customer object/null
     * @throws CustomerException thrown if customer dose not exists
     */
    public Customer getOneCustomer(int customerID) throws CustomerException {
        Customer customer = customerDAO.getOneCustomer(customerID);
        return customer;
    }
    public int getCompanyID(String email,String password) {
        int companyId= 0;
        try {
            companyId = companiesDAO.getCompanyID(email,password);
        } catch (CompanyException e) {
            System.out.println("company does not exists");
        }

        return companyId;
    }
    public void addCategory(String name){
        companiesDAO.addCategory(name);
    }



}
