package project.tester;

import project.DAO.CompaniesDAO;
import project.Facade.ClientFacade;
import project.Facade.CompanyFacade;
import project.Facade.LoginManager;
import project.beans.Company;
import project.beans.Coupon;
import project.exceptions.CompanyException;
import project.exceptions.CouponException;
import project.exceptions.CustomerException;
import project.exceptions.UserException;
import project.util.Category;
import project.util.ClientType;

import java.sql.Date;
import java.util.ArrayList;

public class CompanyTest {
    private static CompanyFacade companyFacade;
    public static void main(String[] args) {



        LoginManager loginManager = LoginManager.getInstance();
        CompaniesDAO companiesDAO=null;
        Company companyA= new Company(0,"companylol","companylol.com","companylol1234", null);

        //coupon= new Coupon(1, cf.getCompanyID(), Category.valueOf("Fashion"),"pants","blue", Date.valueOf("2021-06-23"),Date.valueOf("2021-06-30"),5,60.0,"blabla");

        try {
            companyFacade=(CompanyFacade) loginManager.login("companylolc.com","companylolc1234", ClientType.Company);
            Coupon coupon1= new Coupon(0, companyFacade.getCompanyID(), Category.Fashion, "T-shirt","blue",Date.valueOf("2021-07-28"),5,60.0,"blabla");
            Coupon coupon2= new Coupon(0, companyFacade.getCompanyID(), Category.Food, "burger","big", Date.valueOf("2021-07-30"),5,80.0,"blabla");
            Coupon coupon3= new Coupon(0, companyFacade.getCompanyID(), Category.Fashion,"pants","long", Date.valueOf("2021-07-31"),5,55.0,"blabla");
            Coupon coupon4= new Coupon(0, companyFacade.getCompanyID(), Category.Fashion,"hat","black", Date.valueOf("2021-07-20"),1,50.0,"blabla");

            System.out.println(companyFacade.getCompanyID());
            int param1 =companyFacade.getCompanyID();
            //int param2 = companyFacade.;
/*
            //companyFacade.isCouponExists(param1,1);
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            companyFacade.addCoupon(coupon3);
            companyFacade.addCoupon(coupon4);
*/
            companyFacade.deleteCoupon(5);
            companyFacade.deleteCoupon(5);
            coupon1 = new Coupon(1, companyFacade.getCompanyID(), Category.valueOf("Fashion"), "T-shirt", "blue", Date.valueOf("2021-06-23"), Date.valueOf("2021-06-28"), 5, 60.0, "blabla");
            companyFacade.updateCoupon(coupon1);
            //get all coupons by company
            System.out.println(companyFacade.getAllCouponsByCompany(companyFacade.getCompanyID()));
            //get all coupons by company and category
            System.out.println(companyFacade.getAllCouponsByCompanyAndCategory(companyFacade.getCompanyID(),Category.Fashion.ordinal()));
            //get all coupons by company and price
            System.out.println(companyFacade.getAllCouponsByCompanyAndPrice(companyFacade.getCompanyID(),70.0));
            // get company details
            companyFacade.getCompanyDetails(companyFacade.getCompanyID());



            //System.out.println(companyFacade.getCompanyID());
            System.out.println("it worked");
        } catch (UserException | CouponException | CompanyException e) {
            e.printStackTrace();
        }


    }
}
