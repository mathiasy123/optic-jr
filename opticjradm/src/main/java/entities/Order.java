package entities;

import java.text.DecimalFormat;

public class Order {
    private int id;
    private int productId;
    private String productImage;
    private String productName;
    private double productPrice;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String orderDate;

    public Order(int id, String productImage, String productName, double productPrice, String customerName, String customerEmail, String customerPhoneNumber, String customerAddress, String orderDate) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.orderDate = orderDate;
    }

    public Order(String productName, String customerName, String customerEmail, String customerPhoneNumber, String customerAddress, String orderDate) {
        this.productName = productName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return Double.parseDouble(new DecimalFormat("0.00").format(productPrice));
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
