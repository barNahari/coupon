package project.beans;

import java.util.ArrayList;
import java.util.Objects;

public class Customer {
    private int id;
    private String first_name, last_name, email, password;
    private ArrayList<Coupon> coupons;


    /**
     * @param id         customer id number
     * @param first_name customer first name
     * @param last_name  customer last name
     * @param email      customer email
     * @param password   customer password
     * @param coupons    what coupons teh customer got
     */
    public Customer(int id, String first_name, String last_name, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * @param id         customer id number
     * @param first_name customer first name
     * @param last_name  customer last name
     * @param email      customer email
     * @param password   customer password
     */
    public Customer(int id, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;

    }


    /**
     * @return id
     */
    public int getId() {
        return id;
    }
/*
    public void setId(int id) {
        this.id = id;
    }
*/

    /**
     * @return string first name
     */
    public String getFirstName() {
        return first_name;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    /**
     * @return string last name
     */
    public String getLastName() {
        return last_name;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    /**
     * @return customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return coupons list of the customer
     */
    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * @param coupons
     */
    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * @return string of the customer and his coupons
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(first_name, customer.first_name) && Objects.equals(last_name, customer.last_name) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && Objects.equals(coupons, customer.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, email, password, coupons);
    }

    public String getPassword() {
        return password;
    }
}
