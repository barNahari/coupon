package project.tester;

import project.DAO.CouponsDAO;
import project.DAO.CustomerDAO;
import project.Facade.AdminFacade;
import project.Facade.LoginManager;
import project.SQL.DataBaseManager;
import project.beans.Company;
import project.beans.Coupon;
import project.beans.Customer;
import project.exceptions.CompanyException;
import project.exceptions.CustomerException;

import project.exceptions.UserException;
import project.util.Category;
import project.util.ClientType;
import project.util.CouponExpirationDailyJob;
import project.util.DailyJob;

public class AdminTest {

    public static void main(String[] args) throws UserException {
        //start daily job - clear all jobs by sql command (faster method)
        DailyJob job = new DailyJob();
        job.start();
        job.runJob(true);

        //clear all jobs by iteration, slower method
        //CouponExpirationDailyJob job= new CouponExpirationDailyJob();



        Coupon coupon;
        CouponsDAO couponsDAO;
        CustomerDAO customerDAO;
        DataBaseManager db = null;
        AdminFacade adminFacade=null;
        LoginManager loginManager = LoginManager.getInstance();
        Category[] categories=Category.values();

        // companyA= new Company(0,"companylol","companylol.com","companylol1234", null);
        Company companyB= new Company(0,"companylol2","companylol2.com","companylol21234",null);
        Company companyA= new Company(0,"companylol","companylol.com","companylol1234", null);
        Company companyC= new Company(0,"companylolc","companylolc.com","companylolc1234", null);

        Customer customer1= new Customer(0,"meni","com","menicom@gamail.com","meni1234",null);
        Customer customer2= new Customer(0,"roni","com","bobcom@gamail.com","bob1234",null);
        Customer customer3= new Customer(0,"eli","com","elicom@gamail.com","eli1234",null);

            db.CreateDataBase();
            db.CreateCompaniesTable();
            db.CreateCustomerTable();
            db.CreateCategoriesTable();
            db.CreateCouponsTable();
            db.CreateCustomers_vs_CouponsTable();


        job.run();

        try {

            adminFacade=(AdminFacade) loginManager.login("admin@admin.com","admin",ClientType.Administartor);
            adminFacade.addCompany(companyA);
            adminFacade.addCompany(companyA);
            adminFacade.addCompany(companyB);
            adminFacade.addCompany(companyC);
            //System.out.println(adminFacade.getOneCompany(9).toString());
            //System.out.println(adminFacade.getCompanyID(adminFacade.getOneCompany(1).getEmail(), adminFacade.getOneCompany(1).getPassword()));
           // System.out.println(adminFacade.getCompanyID(companyA.getEmail(),companyA.getPassword()));
            //adminFacade.deleteCompany(1);
           //adminFacade.deleteCompany(14);
            //adminFacade.deleteCompany(9);
            //adminFacade.addCompany(companyA);
            //adminFacade.updateCompany(companyA);
            //System.out.println(adminFacade.getAllCompanies().toString());
            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer2);
            adminFacade.addCustomer(customer3);
            //adminFacade.getOneCompany(45);
            //adminFacade.deleteCustomer(4);
            //System.out.println(adminFacade.getAllCustomers());
            //adminFacade.updateCustomer(customer2);
            //System.out.println(adminFacade.getOneCustomer(3));

            for (Category item : categories) {
                adminFacade.addCategory(item.toString());
            }
        } catch (UserException e) {
            System.out.println("the email or password are incorrect");;
        } catch (CustomerException e) {
            e.printStackTrace();
        } catch (CompanyException e) {
            e.printStackTrace();
        }


        //db. deleteDataBase();
    }


}
