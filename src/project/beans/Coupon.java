package project.beans;

import project.util.Category;

import java.sql.Date;
import java.util.Objects;

public class Coupon {

    private int id, company_id;
    private Category category;
    private String title, description;
    private Date start_date, end_date;
    private int amount;
    private double price;
    private String image;

    /**
     * @param id          coupon number
     * @param company_id  the company that the coupon belong to
     * @param amount      how many coupons there are
     * @param category    what type of coupon
     * @param title       the name of the coupon
     * @param description what is that coupon
     * @param image       can put a string for file or something
     * @param end_date    when is gonna be expired
     * @param price       what the price of the coupon
     *                    A CTOR to create a coupon
     */
    public Coupon(int id, int company_id, Category category, String title, String description, Date end_date, int amount, double price, String image) {
        this.id = id;
        this.company_id = company_id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.start_date = new Date(System.currentTimeMillis());
        this.end_date = end_date;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     *
     * @param id  coupon number
     * @param company_id the company that the coupon belong to
     * @param category  what type of coupon
     * @param title whats the name of the coupon
     * @param description whatb it is
     * @param start_date when he was created
     * @param end_date when its gonna be expired
     * @param amount how many coupons there are
     * @param price whats his price
     * @param image can put a string for file or something
     *    CTOR to make operation on the coupon
     */
    public Coupon(int id, int company_id, Category category, String title, String description, Date start_date, Date end_date, int amount, double price, String image) {
        this.id = id;
        this.company_id = company_id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }


    /**
     * @return id number
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return company id number
     */
    public int getCompany_id() {
        return company_id;
    }


    /**
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return category
     */
    public Category getCategory() {
        return category;
    }


    /**
     * @return name of the coupon
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return start date
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date
     */
    public void setStart_date(Date start_date) {

        this.start_date = start_date;
    }

    /**
     * @return end date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return string of coupon
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company_id=" + company_id +
                ", amount=" + amount +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", startDate=" + start_date +
                ", endDate=" + end_date +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && company_id == coupon.company_id && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && Objects.equals(category, coupon.category) && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(image, coupon.image) && Objects.equals(start_date, coupon.start_date) && Objects.equals(end_date, coupon.end_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company_id, amount, category, title, description, image, start_date, end_date, price);
    }
}
