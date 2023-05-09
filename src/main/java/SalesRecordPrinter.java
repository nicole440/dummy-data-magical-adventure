import model.Transaction;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class SalesRecordPrinter {

    /** Print methods called within each method to print results to the console*/

    public void printAllTransactions() {
        for (Transaction transaction : SaleRecords.allTransactions) {
            System.out.println(transaction.toString());
        }
    }

    public void printTotalRevenue(BigDecimal totalRevenue) {
        System.out.println("Total revenue: $" + totalRevenue);
    }

    public void printPopularProduct(String mostPopularProduct) {
        System.out.println("Most popular product: " + mostPopularProduct);
    }

    public void printAveragePrice(BigDecimal averagePrice) {
        System.out.println("Average product price: $" + averagePrice.toString());
    }

    public void printTotalSoldOfEachProduct(Map<String, Integer> totalProductsSold) {
        String productType;
        int quantitySold;
        System.out.println("Total quantity sold of each product:");
        for (Map.Entry<String, Integer> product : totalProductsSold.entrySet()) {
            productType = product.getKey();
            quantitySold = product.getValue();
            System.out.println(productType + " : " + quantitySold);
        }
    }

    public void printDate(LocalDate date, String productType) {
        System.out.println("Date of highest quantity sales of " + productType + " : " + date);
    }

    public void printDayOfWeek(DayOfWeek dayOfWeek) {
        System.out.println("Day of week with highest revenue: " + dayOfWeek);
    }
}
