package project.util;

import project.DBDAO.CouponsDBDAO;

import java.sql.SQLException;

public class DailyJob extends Thread{

    private boolean isJobRunning;

    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    public void runJob(boolean isJobRunning){
        this.isJobRunning=isJobRunning;
    }

    @Override
    public void run() {
        try {
            while (isJobRunning) {
                couponsDBDAO.deleteCouponJob();
                Thread.sleep(1000 * 60 * 60 * 24);
            }
        } catch (InterruptedException | SQLException error){
            System.out.println(error.getMessage());
        }
    }
}
