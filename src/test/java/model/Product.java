package model;

import java.util.Objects;

public class Product{
    private String name;
    private String price;
    private String priceWithDiscount;
    private String imagePath;
    private String manufacturer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(String priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Product(String name, String price, String priceWithDiscount) {
        this.name = name;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    @Override
    public String toString() {
        return "model.Product{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", priceWithDiscount='" + priceWithDiscount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(priceWithDiscount, product.priceWithDiscount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price, priceWithDiscount);
    }
}
