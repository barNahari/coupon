package project.util;

import project.DAO.CouponsDAO;
import project.DBDAO.CouponsDBDAO;
import project.beans.Coupon;
import project.exceptions.CouponException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;



public class CouponExpirationDailyJob implements Runnable {

        private final CouponsDAO couponsDAO = new CouponsDBDAO();
        private Boolean quit=false;


        @Override
        public void run() {

            System.out.println(" automatic old coupons delete started.");
            while (quit) {
                try {
                    Thread.sleep(1000*5);
                } catch (InterruptedException e) {
                    System.out.println("the old coupons delete daily delay got interrupted");
                    ArrayList<Coupon> coupons=couponsDAO.getAllCoupons();
                    for(Coupon item: coupons){
                        if(item.getEnd_date().before(new Date(System.currentTimeMillis()))){
                        }else{
                            try {
                                couponsDAO.deleteCoupon(item.getId());
                            } catch (CouponException couponException) {
                                System.out.println("no coupon to delete");
                            }
                        }
                    }
                }
                System.out.println("all old coupons are deleted");
            }
        }

        public void setQuit() {
            this.quit = true;
        }
    }

