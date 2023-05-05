package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Transaction {
    private String date;
    private String product;
    private int quantitySold;
    private BigDecimal salePrice;

    public Transaction(String date, String product, int quantitySold, BigDecimal salePrice) {
        this.date = date;
        this.product = product;
        this.quantitySold = quantitySold;
        this.salePrice = salePrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getRevenue() {
        BigDecimal revenue = this.salePrice.multiply(BigDecimal.valueOf(this.quantitySold)).setScale(2, RoundingMode.HALF_UP);
        return revenue;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%.2f", date, product, quantitySold, salePrice);
    }
}
