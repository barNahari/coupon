package project.Facade;

import project.DAO.CompaniesDAO;
import project.DAO.CouponsDAO;
import project.DBDAO.CompaniesDBDAO;
import project.DBDAO.CouponsDBDAO;
import project.beans.Coupon;
import project.exceptions.CompanyException;
import project.exceptions.CouponException;
import project.exceptions.UserException;
import project.util.Category;

import java.sql.Connection;
import java.util.ArrayList;


public class CompanyFacade  extends ClientFacade{

    Connection connections;
    private int company_id;
    CouponsDAO couponsDAO= new CouponsDBDAO();
    CompaniesDAO companiesDAO= new CompaniesDBDAO();
    public CompanyFacade() {

    }
    public boolean isCouponExists(int company_id,int id) throws CouponException{
        if(couponsDAO.isCouponExists(company_id,id)){
            System.out.println("coupon exists");
            return true;
        }else {
            System.out.println("coupon not exists");
            return false;
        }

    }
@Override
   public  boolean login(String email, String password) {
    try {
        company_id = companiesDAO.getCompanyID(email,password);
        return company_id != 0;
    } catch (CompanyException e) {
        System.out.println("sdfdsf");
    }
    return false;

    }

    public void addCoupon(Coupon coupon)   {

        try {
            couponsDAO.addCoupon(coupon);
        } catch (CouponException e) {
            e.printStackTrace();
        }
    }


    public void updateCoupon(Coupon coupon)  {
        try {
            couponsDAO.updateCoupon(coupon);
        } catch (CouponException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoupon(int couponID) throws CouponException {
        couponsDAO.deleteCoupon(couponID);
    }



    public ArrayList<Coupon> getAllCouponsByCompany(int company_id) throws CouponException {
        ArrayList<Coupon> coupons= couponsDAO.getAllCouponsByCompany(company_id);
        return coupons;
    }

    public ArrayList<Coupon> getAllCouponsByCompanyAndCategory(int companyID, int category) throws CouponException {
        ArrayList<Coupon> coupons= couponsDAO.getAllCouponsByCompany(company_id);
        ArrayList<Coupon> coupons2= new ArrayList<>();
        for(Coupon item:coupons){
            if(item.getCategory().ordinal()==category){
                coupons2.add(item);
            }
        }
        return coupons2;
    }

    /**
     *
     * @param company_id get company id
     * @param price get price
     * @return array list of all coupons by company up to price
     */
    public ArrayList<Coupon> getAllCouponsByCompanyAndPrice(int company_id,double price)  {
        ArrayList<Coupon> coupons= couponsDAO.getAllCouponsByCompany(company_id);
        ArrayList<Coupon> coupons2= new ArrayList<>();
        for(Coupon item:coupons){
            if(item.getPrice()<price){
                coupons2.add(item);
            }
        }
        return coupons2;
    }

    /**
     *
     * @param company_id get comapny id
     * @throws CompanyException thrown if not exists
     */
    public void getCompanyDetails(int company_id) throws CompanyException {
        System.out.println(companiesDAO.getOneCompany(company_id));
    }

    /**
     *
     * @return company id
     */
    public int getCompanyID() {
        return company_id;
    }


}
