package project.beans;

import java.util.ArrayList;

import java.util.Objects;


public class Company {
    private int id;
    private String name, email, password;
    private ArrayList<Coupon> coupons;



    /**
     * @param id       the id number of the company
     * @param name     the name of the company
     * @param email    the email of the company
     * @param password the password of the company
     * @param coupons  a list of coupon that the company have
     *    CTOR to show all company details
     */
    public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     *
     * @param id the id number of the company
     * @param name the name of the company
     * @param email the email of the company
     * @param password the password of the company
     *   @CTOR that i use to
     */
    public Company(int id,String name, String email, String password) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.password = password;
    }



    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return email
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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return ArrayList of the coupons
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
     * @return string of the company
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", e_mail='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(password, company.password) && Objects.equals(coupons, company.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, coupons);
    }
}
