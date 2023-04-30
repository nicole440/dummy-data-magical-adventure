import model.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class SaleRecords {

    static List<Transaction> allTransactions = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        SaleRecords records = new SaleRecords();
        records.parseData();
        records.getTotalRevenue(allTransactions);
    }
    
    public List<Transaction> parseData() throws FileNotFoundException {

        String dataSet = "dataset.csv";
        File fileName = new File(dataSet);
        try (Scanner fileScanner = new Scanner(fileName)) {
            fileScanner.nextLine(); // Skips first row
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] columns = line.split(","); // Splits each column by the comma and puts it into an array
                String date = columns[0];
                String product = columns[1];
                int quantitySold = Integer.parseInt(columns[2]);
                BigDecimal salePrice = new BigDecimal(columns[3]);
                Transaction transaction = new Transaction(date, product, quantitySold, salePrice); // Creates new transaction for each line of data
                allTransactions.add(transaction);
                }
            // Print each line of data (now Transaction objects) to console
            for (Transaction transaction : allTransactions) {
                System.out.println(transaction.toString());
            }
        }
        return allTransactions;
    }

    // Question 1: What was the total revenue for the year?
    public BigDecimal getTotalRevenue(List<Transaction> allTransactions) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Transaction transaction : allTransactions) {
            totalRevenue = totalRevenue.add(BigDecimal.valueOf(transaction.getQuantitySold()).multiply(transaction.getSalePrice()));
        }
        System.out.println(totalRevenue.toString());
        return totalRevenue.setScale(2); // ensures two decimal places
    }

    // Question 2: What was the most commonly sold product?
    public String getMostPopularProduct(List<Transaction> allTransactions) {
        String mostPopularProduct = "";
        int counter = 0;
        Map<String, Integer> productsMap = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            productsMap.put(transaction.getProduct(), transaction.getQuantitySold());
        }

        return mostPopularProduct;
    }

    // Question 3: What was the average sale price for each product?
    public BigDecimal getAverageSalePrice(List<Transaction> allTransactions){
        return null;
    }
}