package project.Facade;

import project.DAO.CouponsDAO;
import project.DAO.CustomerDAO;
import project.DBDAO.CouponsDBDAO;
import project.DBDAO.CustomerDBDAO;
import project.beans.Coupon;
import project.beans.Customer;
import project.exceptions.CouponException;
import project.exceptions.CustomerException;
import project.exceptions.UserException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CustomerFacade extends ClientFacade{
    private int customer_id;
    CustomerDAO customerDAO = new CustomerDBDAO();
    CouponsDAO couponsDAO= new CouponsDBDAO();
    public CustomerFacade() {

    }

    /**
     *
     * @param email get customer email
     * @param password get customer password
     * @return validation that the customer password and email are true
     * @throws CustomerException if the connection details are incorrect
     */
    @Override
    public  boolean login(String email, String password) throws UserException {
    customer_id = customerDAO.getCustomerID(email,password);
    return customer_id != 0;
    }

    /**
     *
     * @return customer id
     */
    public int getCustomerId(){
        return customer_id;
    }

    /**
     *
     * @param customer_id get customer id
     * @return customer object
     * @throws CustomerException thrown if not exists
     */
    public Customer getOneCustomer(int customer_id) throws CustomerException {
        Customer customer = null;
        try {
            customer=customerDAO.getOneCustomer(customer_id);
            return customer;
        } catch (CustomerException e) {
            System.out.println("customer dose not exists");
            throw new CustomerException();

        }
    }

    /**
     *
     * @param customer_id get customer id
     * @param coupon_id get coupon id
     */
    public void addCouponPurchase(int customer_id, int coupon_id){
        Coupon coupon=couponsDAO.getOneCoupon(coupon_id);
        try {
            couponsDAO.addCouponPurchase(customer_id,coupon_id);
            System.out.println("coupon was purchase");
        } catch (CouponException e) {
            System.out.println("customer already have the coupon");
        }
    }

    /**
     *
     * @param couponID get coupon id
     * @return coupon object
     */
    public Coupon getOneCoupon(int couponID){
        Coupon coupon=null;
        try {
            coupon=couponsDAO.getOneCouponById(couponID);
            return coupon;
        } catch (CouponException e) {
            System.out.println("coupon dose not exists");
        }
        return coupon;
    }

    /**
     *
     * @param customer_id get customer id
     * @return array list of all coupons by customer
     */
    public ArrayList<Coupon> getAllCouponsByCustomer(int customer_id){
        ArrayList<Coupon> coupons= couponsDAO.getAllCouponsByCustomer(customer_id);
        return coupons;
    }

    /**
     *
     * @param category_id get category id
     * @return array list of all coupons by category
     */
    public ArrayList<Coupon> getAllCouponsByCategory(int category_id){
        ArrayList<Coupon> coupons1= new ArrayList<>();
        ArrayList<Coupon> coupons= couponsDAO.getAllCoupons();
        for(Coupon item:coupons){
            if (item.getCategory().ordinal()==category_id-1){
                coupons1.add(item);
            }
        }

        return coupons1;

    }

    /**
     *
     * @param customer_id get customer id
     * @param category_id get category id
     * @return array list of all coupons by customer and category
     */
    public  ArrayList<Coupon> getAllCustomersCouponsByCategory(int customer_id,int category_id){
        ArrayList<Coupon> coupons1= new ArrayList<>();
        ArrayList<Coupon> coupons= couponsDAO.getAllCouponsByCustomer(customer_id);
        for(Coupon item:coupons){
            if (item.getCategory().ordinal()==category_id-1){
                coupons1.add(item);
            }
        }

        return coupons1;

    }

    /**
     *
     * @param customer_id get customer id
     * @param max_price get max price
     * @return array list of all coupons by customer and price lower then max
     */
    public  ArrayList<Coupon> getAllCustomersCouponsUpToPrice(int customer_id,double max_price){
        ArrayList<Coupon> coupons1= new ArrayList<>();
        ArrayList<Coupon> coupons= couponsDAO.getAllCouponsByCustomer(customer_id);
        for(Coupon item:coupons){
            if (item.getPrice()<max_price){
                coupons1.add(item);
            }
        }

        return coupons1;

    }
}
