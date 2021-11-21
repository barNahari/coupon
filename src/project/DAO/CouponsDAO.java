package project.DAO;

import project.beans.Coupon;
import project.exceptions.CouponException;
import project.util.Category;


import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CouponsDAO {

    ArrayList<Coupon> getAllCouponsByCompany(int companyID);


    ArrayList<Coupon> getAllCouponsByCustomer(int customer_id);

    void subCouponAmountBy1(int couponID) throws CouponException;

    ArrayList<Coupon> getAllCouponsUpToPrice(int companyID, double maxPrice) throws CouponException;

    void addCoupon(Coupon coupon) throws CouponException;


    void updateCoupon(Coupon coupon) throws CouponException;


    void deleteCoupon(int couponID) throws CouponException;


    ArrayList<Coupon> getAllCoupons();


    Coupon getOneCouponById(int couponID) throws CouponException;

    public ArrayList<Coupon> getAllCouponsByCategory(int category_id);

    void addCouponPurchase(int customerID, int couponsID) throws CouponException;


    void deleteCouponPurchase(int customerID, int couponID) throws CouponException;

    boolean isCouponExists(int company_id,int id) throws CouponException;

    ArrayList<Coupon> getAllCouponsByCompanyAndCategory(int company_id, Category category) throws CouponException;

    boolean isCouponExpired(int couponID) throws CouponException;

    ArrayList<Coupon> getAllCustomersCouponsByCategory(int customer_id,int category_id);

    boolean isCouponEmpty(int coupon_id);

    ArrayList<Coupon> getAllCustomersCouponsUpToPrice(int customer_id,double max_price);

    Coupon getOneCoupon(int id);

    void deleteCouponJob() throws SQLException;
}
