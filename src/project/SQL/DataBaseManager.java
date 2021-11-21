package project.SQL;

import java.sql.SQLException;

import project.DBDAO.CouponExpirationDailyJob;
import project.beans.Company;

public class DataBaseManager {
    //connection DB
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "12345678";

    //create & drop database
    private static final String CREATE_DB = "CREATE SCHEMA if not exists couponsdb";
    private static final String DROP_DB = "DROP SCHEMA couponsdb ";

    //create table
    //Companies table
    private static final String CREATE_COMPANIES_TABLE = "CREATE TABLE if not exists `couponsdb`.`companies`  (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`)," +
            "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE," +
            "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)";


    //customer table
    private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE if not exists `couponsdb`.`customers`  (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `first_name` VARCHAR(45) NOT NULL," +
            "  `last_name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`)," +
            "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)";
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE if not exists `couponsdb`.`categories` (" +
            "  `id` int NOT NULL AUTO_INCREMENT," +
            "  `NAME` varchar(45) NOT NULL," +
            "  PRIMARY KEY (`id`)" +
            ")";
    private static final String CREATE_COUPONS_TABLE = "CREATE TABLE if not exists `couponsdb`.`coupons`" +
            " (`id` INT NOT NULL AUTO_INCREMENT," +
            " `company_id` INT NOT NULL," +
            " `category_id` INT NOT NULL," +
            " `title` VARCHAR(40) NOT NULL," +
            " `description` VARCHAR(200) NOT NULL," +
            "`start_date` DATE NOT NULL," +
            "`end_date` DATE NOT NULL," +
            " `amount` INT NOT NULL," +
            " `price` DOUBLE NOT NULL, " +
            "`image` VARCHAR(150) NOT NULL," +
            " PRIMARY KEY(`id`)," +
            "FOREIGN KEY(`company_id`) REFERENCES `couponsdb`.`companies`(`id`) ON DELETE CASCADE," +
            "FOREIGN KEY(`category_id`) REFERENCES `couponsdb`.`categories`(`id`)," +
            "UNIQUE INDEX (`company_id`,`title`), CHECK (`amount` > 0));";
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPON = "CREATE TABLE if not exists `couponsdb`.`customers_vs_coupons` (" +
            "  `customer_id` INT NOT NULL," +
            "  `coupon_id` INT NOT NULL," +
            "  PRIMARY KEY (`customer_id`, `coupon_id`)," +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "  CONSTRAINT `customer_id`" +
            "    FOREIGN KEY (`customer_id`)" +
            "    REFERENCES `couponsdb`.`customers` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE NO ACTION," +
            "  CONSTRAINT `coupon_id`" +
            "    FOREIGN KEY (`coupon_id`)" +
            "    REFERENCES `couponsdb`.`coupons` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE NO ACTION);";


    public static void CreateCompaniesTable() {
        try {
            DButils.runQuery(CREATE_COMPANIES_TABLE);
        } catch (SQLException err) {
            System.out.println("companies table is already exists");
        }
    }

    public static void CreateCategoriesTable() {
        try {
            DButils.runQuery(CREATE_CATEGORIES_TABLE);
            System.out.println("categories table was created");
        } catch (SQLException throwables) {
            System.out.println("categories table is already exists");
        }
    }

    public static void CreateCustomerTable() {
        try {
            DButils.runQuery(CREATE_CUSTOMER_TABLE);
            System.out.println("customer table was created");

        } catch (SQLException throwables) {
            System.out.println("coupon table is already exists");
        }
    }

    public static void CreateCustomers_vs_CouponsTable() {
        try {
            DButils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPON);
            System.out.println("customers vs coupons table was created");
        } catch (SQLException throwables) {
            System.out.println("customers vs coupons table is already exists");
        }
    }

    public static void CreateCouponsTable() {
        try {
            DButils.runQuery(CREATE_COUPONS_TABLE);
            System.out.println("coupon table was created");
        } catch (SQLException throwables) {
            System.out.println("coupon table is already exists");
        }
    }

    public static void CreateDataBase() {
        try {
            DButils.runQuery(CREATE_DB);
            System.out.println("data base was created");
        } catch (SQLException throwables) {
            System.out.println("data base is already exists");
        }
    }

    /*
        public static void createTables(){
            try {
                DButils.runQuery(CREATE_TABLE);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

     */
    public static void deleteDataBase() {
        try {
            DButils.runQuery(DROP_DB);
            System.out.println("data base was deleted");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
