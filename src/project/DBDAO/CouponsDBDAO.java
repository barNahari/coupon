package project.DBDAO;

import project.DAO.CouponsDAO;
import project.SQL.ConnectionPool;
import project.beans.Coupon;
import project.beans.Customer;
import project.exceptions.CouponException;
import project.exceptions.CustomerException;
import project.util.Category;

import java.sql.*;
import java.util.ArrayList;


public class CouponsDBDAO implements CouponsDAO {
    //order everything from the picture and use gray ones. make sure resultset is just for results and make sure to change all delete methods
    private static final String ADD_COUPON = " INSERT INTO `couponsdb`.`coupons` (`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON_BY_ID = " UPDATE  `couponsdb`.`coupons` SET  category_id=? , title=?, description =? ,  start_date=? ,  end_date=?,  amount=? , price =?,image=?   WHERE id =? AND company_id=? ";
    private static final String DELETE_COUPON_BY_ID = "DELETE FROM `couponsdb`.`coupons` WHERE id=?";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `couponsdb`.`coupons`";
    private static final String GET_ALL_COUPONS_BY_COMPANY = " SELECT * FROM `couponsdb`.`coupons` WHERE company_id=?";
    private static final String GET_ALL_COUPONS_BY_CATEGORY_AND_COMPANY = "SELECT *FROM `couponsdb`.`coupons` WHERE company_id=? AND category_id=?";
    private static final String GET_ONE_COUPON_BY_ID = "SELECT * FROM `couponsdb`.`coupons` WHERE id=?";
    private static final String ADD_COUPONS_PURCHASE = "INSERT INTO `couponsdb`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES (?,?) ";
    private static final String DELETE_COUPONS_PURCHASE = "DELETE FROM `couponsdb`.`customers_vs_coupons` WHERE `customer_id`=? AND `coupon_id`=? ";
    private static final String IS_COUPON_EXISTS = "SELECT COUNT(*) FROM `couponsdb`.`coupons` WHERE `company_id`=? AND `id`=? ";
    private static final String GET_ALL_COUPONS_UP_TO_PRICE = "SELECT * FROM `couponsdb`.`coupons` WHERE company_id=? AND price BETWEEN 0 AND VALUES (?) ";
    private static final String SUB_COUPON_AMOUNT_BY1 = "UPDATE `couponsdb`.`coupons` SET amount=amount-1 WHERE id=?";
    private static final String IS_COUPON_EXPIRED = " SELECT * FROM `couponsdb`.`coupons` WHERE id =? AND endDate<NOW() ";
    private static final String GET_ALL_COUPONS_BY_CATEGORY = "SELECT * FROM `couponsdb`.`coupons` WHERE category_id=?";
    private static final String GET_ALL_COUPONS_BY_CUSTOMER = "SELECT `id`,`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`" +
            "    FROM `couponsdb`.`coupons`" +
            "    INNER JOIN `couponsdb`.`customers_vs_coupons`" +
            "    ON coupons.id = customers_vs_coupons.coupon_id" +
            "    WHERE customers_vs_coupons.customer_id = ?";
    private static final String IS_COUPON_EMPTY = "SELECT amount FROM `couponsdb`.`coupons` WHERE id =?";
    private static final String GET_ALL_CUSTOMER_COUPONS_BY_CATEGORY="SELECT `id`,`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`" +
            "  FROM `couponsdb`.`coupons`" +
            " INNER JOIN `couponsdb`.`customers_vs_coupons`" +
            " ON coupons.id = customers_vs_coupons.coupon_id"+
            " WHERE customers_vs_coupons.customer_id = ? AND category_id=?";
    private static final String GET_ALL_CUSTOMER_COUPONS_UP_TO_PRICE="SELECT `id`,`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`" +
            "  FROM `couponsdb`.`coupons`" +
            " INNER JOIN `couponsdb`.`customers_vs_coupons`" +
            " ON coupons.id = customers_vs_coupons.coupon_id"+
            " WHERE customers_vs_coupons.customer_id = ? AND price<max_price";

    private final String DELETE_COUPON_JOB = "DELETE FROM 'couponsdb'.'coupons' WHERE ? > END_DATE";
    Connection connection;
    Category category = null;
    Coupon coupon = null;
    CustomerDBDAO customerDBDAO;
    Date ct;

    @Override
    public ArrayList<Coupon> getAllCouponsByCategory(int category_id) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_CATEGORY);
            statement.setInt(1, category_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));
                coupons.add(coupon);
            }
            return coupons;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param id get coupon id
     * @return coupon object
     */
    @Override
    public Coupon getOneCoupon(int id) {
        Coupon coupon = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON_BY_ID);
            statement.setInt(1, id); //where id = ? => select * from repair where id = 1
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

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
        return coupon;
    }

    @Override
    public void deleteCouponJob() throws SQLException {
        try{
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_JOB);
            String myDate = String.valueOf(new Date(new java.util.Date().getTime()));
            statement.setString(1,myDate);
            statement.execute();
        } catch (SQLException | InterruptedException error){
            System.out.println(error.getMessage());
        }
    }

    /**
     *
     * @param coupon_id get coupon id
     * @return validation if you still have any
     */
    @Override
    public boolean isCouponEmpty(int coupon_id) {

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_EMPTY);
            statement.setInt(1, coupon_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (resultSet.getInt(1) <= 0);

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
        return true;
    }

    /**
     *
     * @param customer_id get customer id
     * @param max_price get max price
     * @return array list of all coupons by customer and les then max price
     */
    @Override
    public  ArrayList<Coupon> getAllCustomersCouponsUpToPrice(int customer_id,double max_price){

        ArrayList<Coupon> coupons = new ArrayList<>();


        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER_COUPONS_UP_TO_PRICE);
            statement.setInt(1, customer_id);
            statement.setDouble(2,max_price);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));
                coupons.add(coupon);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("customer does not exists");

        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return coupons;
    }

    /**
     *
     * @param customer_id get customer id
     * @param category_id get category id
     * @return array list of all coupons by customer and category
     */
    @Override
    public  ArrayList<Coupon> getAllCustomersCouponsByCategory(int customer_id,int category_id){
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER_COUPONS_BY_CATEGORY);
            statement.setInt(1, customer_id);
            statement.setInt(2,category_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));
                coupons.add(coupon);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("customer does not exists");

        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return coupons;
    }
    /**
     * @param customer_id get customer id
     * @return array list of coupons that the customer bought
     */
    public ArrayList<Coupon> getAllCouponsByCustomer(int customer_id) {
        ArrayList<Coupon> coupons = new ArrayList<>();


        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_CUSTOMER);
            statement.setInt(1, customer_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));
                coupons.add(coupon);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("customer does not exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return coupons;
    }


    /**
     * @param couponID get coupon id
     * @return validation  that the coupon exists
     * @throws CouponException if the coupon does not exists
     */
    @Override
    public boolean isCouponExpired(int couponID) throws CouponException {
        Coupon c1 = getOneCouponById(couponID);
        try {

            connection = ConnectionPool.getInstance().getConnection();
            if (isCouponExists(c1.getCompany_id(), c1.getId())) {
                PreparedStatement statement = connection.prepareStatement(IS_COUPON_EXPIRED);
                statement.setInt(1, couponID);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                if (resultSet.getDate("end_date").before(Date.valueOf(String.valueOf(System.currentTimeMillis())))) {
                    return false;
                } else {
                    throw new CouponException();
                }
            }

        } catch (InterruptedException | SQLException | CouponException e) {
            System.out.println("coupon is expierd");

        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;


    }

    /**
     * @param company_id get company id
     * @param category   get the category
     * @return the combine list of them
     */
    @Override
    public ArrayList<Coupon> getAllCouponsByCompanyAndCategory(int company_id, Category category) {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_CATEGORY_AND_COMPANY);
            statement.setInt(1, company_id); //where id = ? => select * from repair where id = 1
            statement.setInt(2, category.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
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
        return coupons;


    }

    /**
     * @param company_id get company id
     * @return the list of coupons that the company got/
     */
    @Override
    public ArrayList<Coupon> getAllCouponsByCompany(int company_id) {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_COMPANY);
            statement.setInt(1, company_id); //where id = ? => select * from repair where id = 1
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
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
        return coupons;

    }

    /**
     * @param couponID get coupon id
     * @throws CouponException thrown if the amount of the coupon is 0
     */
    @Override
    public void subCouponAmountBy1(int couponID) throws CouponException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            //create a prepared sql statement
            if (isCouponExists(coupon.getCompany_id(), coupon.getId())) {
                if (!isCouponEmpty(couponID)) {
                    PreparedStatement statement = connection.prepareStatement(SUB_COUPON_AMOUNT_BY1);
                    statement.setInt(1, coupon.getCompany_id());
                    statement.setString(2, coupon.getTitle());
                    statement.setInt(3, coupon.getAmount() - 1);
                    statement.execute();
                } else {
                    throw new CouponException();
                }
            }
        } catch (InterruptedException | SQLException | CouponException e) {
            System.out.println("coupon amount is 0");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param company_id get company id
     * @param maxPrice   get the max price
     * @return array list of al the coupons that below the max price
     */
    @Override
    public ArrayList<Coupon> getAllCouponsUpToPrice(int company_id, double maxPrice) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_UP_TO_PRICE);
            statement.setInt(1, company_id);
            statement.setDouble(2, maxPrice);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);


            }

        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return coupons;
    }

    /**
     * @param id get coupon id
     * @return validation if the coupon exists
     * @throws CouponException thrown if the coupon does not exists
     */
    @Override
    public boolean isCouponExists(int company_id, int id) throws CouponException {
        try {
            //open connection
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_EXISTS);
            statement.setInt(1, company_id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }

        } catch (InterruptedException e) {
            System.out.println("there is a problem");
        } catch (SQLException throwables) {
            System.out.println("coupon does not exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return false;
    }

    /**
     * @param coupon get a coupon
     * @throws CouponException thrown if coupon already exists
     */
    @Override
    public void addCoupon(Coupon coupon)  {

        try {

            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            if(!isCouponExists(coupon.getCompany_id(),coupon.getId())) {
                PreparedStatement statement = connection.prepareStatement(ADD_COUPON);
                statement.setInt(1, coupon.getCompany_id());
                statement.setInt(2, coupon.getCategory().ordinal()+1);
                statement.setString(3, coupon.getTitle());
                statement.setString(4, coupon.getDescription());
                statement.setDate(5, coupon.getStart_date());
                statement.setDate(6, coupon.getEnd_date());
                statement.setInt(7, coupon.getAmount());
                statement.setDouble(8, coupon.getPrice());
                statement.setString(9, coupon.getImage());
                statement.execute();
                System.out.println("coupon was added");
            }else {
                throw new CouponException();
            }

        } catch (InterruptedException | SQLException|CouponException e) {
            System.out.println("coupon already exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }


    /**
     * @param coupon get a coupon
     * @throws CouponException thrown if coupon does not exists
     */
    @Override
    public void updateCoupon(Coupon coupon) throws CouponException {

        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            if (isCouponExists(coupon.getCompany_id(), coupon.getId())) {
                //create a prepared sql statement
                PreparedStatement statement = connection.prepareStatement(UPDATE_COUPON_BY_ID);
                statement.setInt(1, coupon.getCategory().ordinal()+1);
                statement.setString(2, coupon.getTitle());
                statement.setString(3, coupon.getDescription());
                statement.setDate(4, coupon.getStart_date());
                statement.setDate(5, coupon.getEnd_date());
                statement.setInt(6, coupon.getAmount());
                statement.setDouble(7, coupon.getPrice());
                statement.setString(8, coupon.getImage());
                statement.setInt(9, coupon.getId());
                statement.setInt(10, coupon.getCompany_id());
                statement.execute();
                System.out.println("coupon was updated");

            } else {
                throw new CouponException();
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } catch (CouponException e) {
            System.out.println("coupon does not exists ");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    /**
     * @param couponID get coupon id
     * @throws CouponException thrown if coupon does not exists
     */
    @Override
    public void deleteCoupon(int couponID) throws CouponException {
        Coupon c1 = getOneCouponById(couponID);
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCouponExists(c1.getCompany_id(), c1.getId())) {
                PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_BY_ID);
                statement.setInt(1, couponID);
                statement.execute();
                System.out.println("coupon was deleted");
            } else {
                throw new CouponException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException | CouponException | NullPointerException throwables) {
            System.out.println("coupon does not exists");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    /**
     * @return all the coupons on the data base
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
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
        return coupons;
    }

    /**
     * @param couponID get coupon id
     * @return the coupon
     * @throws CouponException if coupon does not exists
     */
    @Override
    public Coupon getOneCouponById(int couponID) throws CouponException {

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON_BY_ID);
            statement.setInt(1, couponID); //where id = ? => select * from repair where id = 1
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id")-1],
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("coupon does not exists");
            ;
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return coupon;

    }

    /**
     * @param customer_id get customer id
     * @param coupon_id   get coupon id
     * @throws CouponException thrown if the customer have the coupon already
     */
    @Override
    public void addCouponPurchase(int customer_id, int coupon_id) throws CouponException {
        Coupon c1 = getOneCouponById(coupon_id);
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(ADD_COUPONS_PURCHASE);
            statement.setInt(1, customer_id);
            statement.setInt(2, coupon_id);
            statement.execute();
            PreparedStatement statement1=connection.prepareStatement(SUB_COUPON_AMOUNT_BY1);
            statement1.setInt(1,coupon_id);
            statement1.execute();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("customer have the coupon already");
            ;
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param customer_id get customer id
     * @param coupon_id   get coupon id
     * @throws CouponException thrown if a purchase didn't happen
     */
    @Override
    public void deleteCouponPurchase(int customer_id, int coupon_id) throws CouponException {
        Coupon c1 = getOneCouponById(coupon_id);
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isCouponExists(c1.getCompany_id(), c1.getId())) {
                PreparedStatement statement = connection.prepareStatement(DELETE_COUPONS_PURCHASE);
                statement.setInt(1, customer_id);
                statement.setInt(2, coupon_id);
                statement.execute();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("no buy to delete");;
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
