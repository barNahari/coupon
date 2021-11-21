package project.DBDAO;

import project.DAO.CompaniesDAO;
import project.DAO.CouponsDAO;
import project.SQL.ConnectionPool;
import project.beans.Company;
import project.exceptions.CompanyException;



import java.sql.*;
import java.util.ArrayList;



public class CompaniesDBDAO implements CompaniesDAO {

    private static final String IS_COMPANY_EXISTS = "SELECT COUNT(*) FROM `couponsdb`.`companies` WHERE email=? AND password=? ";
    private static final String ADD_COMPANY = "INSERT INTO `couponsdb`.`companies`(`name`,`email`,`password`) VALUES (?,?,?)";
    private static final String UPDATE_COMPANY = " UPDATE `couponsdb`.`companies` set email=?, password=? WHERE id=? ";
    private static final String DELETE_COMPANY_BY_ID = "DELETE FROM `couponsdb`.`companies` WHERE id=?";
    private static final String GET_ONE_COMPANY_BY_ID = "SELECT * FROM `couponsdb`.`companies` WHERE id=?  ";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `couponsdb`.`companies`";
    private static final String GET_COMPANY_ID = "SELECT * FROM `couponsdb`.`companies` WHERE email=? AND password=?";
    private static final String IS_COMPANY_EXISTS_BY_ID = "SELECT COUNT(*) FROM `couponsdb`.`companies` WHERE id=? ";
    private static final String ADD_CATEGORY = "INSERT INTO `couponsdb`.`categories` (name) VALUES (?)";
    private Connection connection;
    CompaniesDAO companiesDAO = null;
    CouponsDAO couponsDAO;

    /*
    Category[] categories=Category.values();
        for(Category item:categories){
            System.out.println(item);

     */

    /**
     * @param name name of the category
     *             that's  a method that i add the categories to their table
     */
    public void addCategory(String name) {

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_CATEGORY);
            statement.setString(1, name);
            statement.execute();
            System.out.println("category was added");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("category already exists");;
        }
    }

    /**
     * @param company_id get the company id
     * @return validation if the company exists
     */
    @Override
    public boolean isCompanyExistsByID(int company_id) {

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS_BY_ID);
            statement.setInt(1, company_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (resultSet.getInt(1) > 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            {
                try {
                    ConnectionPool.getInstance().returnConnection(connection);
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * @param email    company email
     * @param password company password
     * @return validation if the company exists
     * @throws CompanyException thrown if company does not exists
     */
    @Override
    public boolean isCompanyExists(String email, String password) throws CompanyException {
        try {
            //open connection
            connection = ConnectionPool.getInstance().getConnection();
            // prepare sql statement - (IS_COMPANY_EXISTS)
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS);
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

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }


        return false;
    }

    /**
     * @param company get company object
     * @throws CompanyException thrown if company already exists
     */
    @Override
    public void addCompany(Company company) throws CompanyException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            if (!isCompanyExists(company.getEmail(), company.getPassword())) {
                PreparedStatement statement = connection.prepareStatement(ADD_COMPANY);
                statement.setString(1, company.getName());
                statement.setString(2, company.getEmail());
                statement.setString(3, company.getPassword());
                statement.execute();
                System.out.println("company was added");
            }else {
                throw new CompanyException();
            }
        } catch (InterruptedException | SQLException|CompanyException  e) {
            System.out.println("company is already exists");
        } finally {

            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }

        }

    }

    /**
     * @param company get company object
     * @throws CompanyException thrown if company does not exists
     */

    @Override
    public void updateCompany(Company company) throws CompanyException {

        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            //create a prepared sql statement
            if (isCompanyExists(company.getEmail(), company.getPassword())) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
                statement.setString(1, company.getEmail());
                statement.setString(2, company.getPassword());
                statement.setInt(3, company.getId());
                statement.execute();
                System.out.println("company was updated");

            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("sas");
            System.out.println(e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                ;
            }
        }


    }


    /**
     * @param company_id get company id
     * @throws CompanyException thrown if company does not exists
     */
    @Override
    public void deleteCompany(int company_id) throws CompanyException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCompanyExistsByID(company_id)) {
                PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY_BY_ID);
                statement.setInt(1, company_id);
                statement.execute();
                System.out.println("company was deleted");
            } else {
                throw new CompanyException();
            }

        } catch (InterruptedException | SQLException | CompanyException e) {
            System.out.println("company does not exists!!!");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());


            }
        }

    }

    /**
     * @return array list of all the companies that exists
     * @throws CompanyException thrown if there are no companies
     */
    @Override
    public ArrayList<Company> getAllCompanies()  {
        Company company = null;
        ArrayList<Company> companies = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                companies.add(company);
            }

        } catch (SQLException | InterruptedException  throwables) {
            System.out.println("THERE ARE NO COMPANIES!!!");
            ;
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
        return companies;

    }


    /**
     * @param company_id get company id
     * @return company
     * @throws CompanyException thrown if company does not exists
     */
    @Override
    public Company getOneCompany(int company_id) throws CompanyException {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCompanyExistsByID(company_id)) {
                PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_ID);
                statement.setInt(1, company_id); //where id = ? => select * from repair where id = 1
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    company = new Company(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"));
                    CouponsDBDAO coupons = new CouponsDBDAO();
                    Company company1 = new Company(company.getId(), company.getName(), company.getEmail(),
                            company.getPassword(), coupons.getAllCouponsByCompany(company_id));
                    return company1;
                }
            } else {
                throw new CompanyException();
            }


        } catch (SQLException | InterruptedException | CompanyException throwables) {
            //System.out.println(throwables.getMessage());
            System.out.println("company does not exists");

        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
        return company;

    }

    /**
     * @param email    get company email
     * @param password get company password
     * @return company id
     * @throws CompanyException thrown if company does not exists
     */
    public int getCompanyID(String email, String password) throws CompanyException {

        int companyInt = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCompanyExists(email, password)) {
                PreparedStatement statement = connection.prepareStatement(GET_COMPANY_ID);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                //resultSet.next();
                companyInt = resultSet.getInt("id");
                return companyInt;
            } else {
                throw new CompanyException();
            }
        } catch (InterruptedException | SQLException | CompanyException e) {
            System.out.println("company does not exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


        return companyInt;
    }


}
