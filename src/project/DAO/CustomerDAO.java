package project.DAO;

import project.beans.Customer;
import project.exceptions.CustomerException;

import java.util.ArrayList;

public interface CustomerDAO {

    boolean isCustomerExists(String email, String password) throws CustomerException;


    void addCustomer(Customer customer) throws CustomerException;


    void updateCustomer(Customer customer) throws CustomerException;


    void deleteCustomer(int customerID) throws CustomerException;

    ArrayList<Customer> getAllCustomers() ;


    Customer getOneCustomer(int customerID) throws CustomerException;

    int getCustomerID(String email, String password) ;

    boolean isCustomerExistsById(int customerId);


}
