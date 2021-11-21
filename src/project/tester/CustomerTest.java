package project.tester;


import project.Facade.CustomerFacade;
import project.Facade.LoginManager;
import project.beans.Coupon;
import project.exceptions.CompanyException;
import project.exceptions.CustomerException;
import project.exceptions.UserException;
import project.util.Category;
import project.util.ClientType;

import java.sql.Date;

public class CustomerTest {
    public static void main(String[] args) {
        CustomerFacade customerFacade;
        LoginManager loginManager = LoginManager.getInstance();


        try {
            customerFacade = (CustomerFacade) loginManager.login("bobcom@gamail.com", "bob1234", ClientType.Customer);
            //System.out.println(customerFacade.getOneCustomer(customerFacade.getCustomerId()));
            //System.out.println(customerFacade.getCustomerId());
            ;
            int param1 = customerFacade.getCustomerId();
            //System.out.println(customerFacade.getOneCustomer(param1));
            //to get the coupon id i need to use hard-coded
            int param2 = customerFacade.getOneCoupon(1).getId();
            int param3 = customerFacade.getOneCoupon(3).getId();
            int param4=customerFacade.getOneCoupon(4).getId();
            int param5=customerFacade.getOneCoupon(5).getId();
/*
            // adding coupon purchase
            customerFacade.addCouponPurchase(param1,param2);
            customerFacade.addCouponPurchase(param1,param2);
            customerFacade.addCouponPurchase(param1,param3);
            customerFacade.addCouponPurchase(param1,param4);
*/
            // get all customer coupons
            System.out.println(customerFacade.getAllCouponsByCustomer(param1));
            // get all customer coupons by category
            System.out.println(customerFacade.getAllCouponsByCategory(Category.Fashion.ordinal()+1));
            //get all customer up to price
            System.out.println(customerFacade.getAllCustomersCouponsUpToPrice(param1,60));
            //get all customer details
            System.out.println(customerFacade.getOneCustomer(2).toString());



        } catch (UserException | CustomerException e) {
            e.printStackTrace();
        }


    }
}
