import model.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SaleRecords {

    static List<Transaction> allTransactions = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        SaleRecords records = new SaleRecords();
        records.parseData();
        records.getTotalRevenue(allTransactions);
        records.getMostPopularProduct(allTransactions);
        records.getAveragePrice(allTransactions);
        records.getTotalProductsSoldByType(allTransactions);
    }
    
    public List<Transaction> parseData() throws FileNotFoundException {
        String dataSet = "dataset.csv";
        File fileName = new File(dataSet);
        try (Scanner fileScanner = new Scanner(fileName)) {
            fileScanner.nextLine(); // Skips first row
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] columns = line.split(",");
                String date = columns[0];
                String product = columns[1];
                int quantitySold = Integer.parseInt(columns[2]);
                BigDecimal salePrice = new BigDecimal(columns[3]);
                Transaction transaction = new Transaction(date, product, quantitySold, salePrice);
                allTransactions.add(transaction);
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
        System.out.println("Total revenue: $" + totalRevenue.toString());
        return totalRevenue.setScale(2); // ensures two decimal places
    }

    // Question 2: What was the most commonly sold product?
    public String getMostPopularProduct(List<Transaction> allTransactions) {
        // Place product type and quantity sold for each product into a map, adding additional quantities to each value
        Map<String, Integer> productCountMap = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            String product = transaction.getProduct();
            int count = productCountMap.getOrDefault(product, 0);
            productCountMap.put(product, count + transaction.getQuantitySold());
        }
        // Iterate through map to compare total quantities of each product sold
        String mostPopularProduct = "";
        int highestCount = 0;
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            String product = entry.getKey();
            int count = entry.getValue();
            if (count > highestCount) {
                mostPopularProduct = product;
                highestCount = count;
            }
        }
        printPopularProduct(mostPopularProduct);
        return mostPopularProduct;
    }



    // Question 3: What was the average price between each product type?
    public BigDecimal getAveragePrice(List<Transaction> allTransactions) {
        // assign each product and its price to key/value pairs in a map
        Map<String, BigDecimal> productsPriceMap = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            String product = transaction.getProduct();
            BigDecimal price = transaction.getSalePrice();
            productsPriceMap.put(product, price);
        }
        // for each loop through map to count the number of products and add all price values together by product type
        BigDecimal sumOfPrices = BigDecimal.ZERO;
        int numberOfProducts = 0;
        for (Map.Entry<String, BigDecimal> product : productsPriceMap.entrySet()) {
            numberOfProducts = productsPriceMap.size();
            sumOfPrices = sumOfPrices.add(product.getValue());
        }
        BigDecimal averagePrice = sumOfPrices.divide(BigDecimal.valueOf(numberOfProducts), 2, RoundingMode.HALF_UP);

        printAveragePrice(averagePrice);
        return averagePrice;
    }


    // TODO finish this method
//  Find the total number of products sold for each product type.
    public Map<String, Integer> getTotalProductsSoldByType(List<Transaction> allTransactions) {
        Map<String, Integer> totalProductsSold = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            String product = transaction.getProduct();
            int count = transaction.getQuantitySold();
            totalProductsSold.put(product, count + transaction.getQuantitySold());

        }
        System.out.println(totalProductsSold.toString());
        return totalProductsSold;
    }

    // TODO  write additional methods to find values based on below questions

//    Find the date on which the most units of a particular product were sold.
//    Identify which day of the week had the most sales, or the highest revenue.
//    Calculate the total revenue for a specific date range.
//    Find the average sale price for each day of the week.


    public void printAllTransactions() {
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction.toString());
        }
    }

    public void printPopularProduct(String mostPopularProduct) {
        System.out.println("Most commonly sold product: " + mostPopularProduct);
    }

    public void printAveragePrice(BigDecimal averagePrice) {
        System.out.println("Average product price: " + averagePrice.toString());
    }

}

