package project.DBDAO;

import project.DAO.CustomerDAO;
import project.SQL.ConnectionPool;
import project.beans.Company;
import project.beans.Customer;
import project.exceptions.CustomerException;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDBDAO implements CustomerDAO {

    private static final String IS_CUSTOMER_EXISTS = " SELECT COUNT(*) FROM `couponsDB`.`customers` WHERE email=? AND password =?  ";
    private static final String ADD_CUSTOMER = " INSERT INTO `couponsDB`.`customers` (`first_name`,`last_name`,`email`,`password`) VALUES (?,?,?,?)";
    private static final String UPDATE_CUSTOMER = " UPDATE `couponsDB`.`customers` set first_name=?, last_name=?, email=?, password=? WHERE id=? ";
    private static final String DELETE_CUSTOMER = " DELETE FROM `couponsDB`.`customers` WHERE id=? ";
    private static final String GET_ALL_CUSTOMERS = " SELECT * FROM `couponsdb`.`customers`";
    private static final String GET_ONE_CUSTOMER = " SELECT * FROM `couponsDB`.`customers` WHERE id=?";
    private static final String GET_CUSTOMER_ID = " SELECT * FROM `couponsDB`.`customers` WHERE email=? and password=?";
    private static final String DID_CUSTOMER_BUY_COUPON = " SELECT * FROM  `couponsDB`.`customers_vs_coupons` WHERE coupon_id=? AND customer id=? ";
    private static final String IS_CUSTOMER_EXISTS_BY_ID = " SELECT * FROM `couponsDB`.`customers` WHERE id=?";

    Connection connection;
    Customer customer = null;

    public boolean didCustomerBuyCoupon(int customer_id, int coupon_id) {
        try {

            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DID_CUSTOMER_BUY_COUPON);
            statement.setInt(1, customer_id);
            statement.setInt(2, coupon_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            if(resultSet.getInt(1) > 0){
                return true;
            }else {
                throw new CustomerException();
            }

        } catch (InterruptedException | NullPointerException|CustomerException e) {
            System.out.println("customer already have a this coupon");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean isCustomerExistsById(int customerId) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS_BY_ID);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (resultSet.getInt(1) > 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isCustomerExists(String email, String password) throws CustomerException {
        try {
            //open connection
            connection = ConnectionPool.getInstance().getConnection();
            // prepare sql statement - (IS_COMPANY_EXISTS)
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS);
            //set the email that i want to check
            statement.setString(1, email);
            //set the password i want to check
            statement.setString(2, password);
            // get the result set
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            // return with a short if there is a match so it will bring
            //back the int 1 so the value is true and if it doesn't exists will return 0 so its false
            return (resultSet.getInt(1) > 0);

        } catch (InterruptedException | NullPointerException e) {
            System.out.println("the email or the password are incorrect");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }


    @Override
    public void addCustomer(Customer customer) throws CustomerException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            //create a prepared sql statement
            if (!isCustomerExists(customer.getEmail(), customer.getPassword())) {
                PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getPassword());
                statement.execute();
                System.out.println("customer was created");
            } else {
                throw new CustomerException();
            }

        } catch (InterruptedException | SQLException | CustomerException e) {
            System.out.println("customer is already exists");
            ;
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    @Override
    public void updateCustomer(Customer customer) throws CustomerException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
                //create a prepared sql statement
                PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getPassword());
                statement.setInt(5, customer.getId());
                statement.execute();
                System.out.println("customer was updated");
            } else {
                throw new CustomerException();
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } catch (CustomerException e) {
            System.out.println("customer is not exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    @Override
    public void deleteCustomer(int customerID) throws CustomerException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCustomerExistsById(customerID)) {
                PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
                statement.setInt(1, customerID);
                statement.execute();
                System.out.println("customer was deleted");
            } else {
                throw new CustomerException();
            }
        } catch (InterruptedException | CustomerException e) {
            System.out.println("customer dose not exists");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    @Override
    public ArrayList<Customer> getAllCustomers()  {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), null);
                customers.add(customer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customers;


    }


    @Override
    public Customer getOneCustomer(int customerID) throws CustomerException {
        //TODO check if its ok
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCustomerExistsById(customerID)) {
                PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER);
                statement.setInt(1, customerID);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    customer = new Customer(resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"));
                    CouponsDBDAO coupons = new CouponsDBDAO();
                    Customer customer1 = new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(),
                            customer.getEmail(), customer.getPassword(), coupons.getAllCouponsByCustomer(customer.getId()));
                    return customer1;

                }
            } else {
                throw new CustomerException();
            }
        } catch (InterruptedException | CustomerException e) {
            System.out.println("customer does not exists");
            ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customer;

    }


    @Override
    public int getCustomerID(String email, String password) {
        int customerInt = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCustomerExists(email, password)) {
                PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_ID);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                customerInt = resultSet.getInt(1);
                return customerInt;
            } else {
                throw new CustomerException();
            }

        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } catch (CustomerException e) {
            System.out.println("customer dose not exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customerInt;
    }
}