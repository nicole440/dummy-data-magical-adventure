import model.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class SaleRecords {

    static List<Transaction> allTransactions = new ArrayList<>();
    static SalesRecordPrinter printer = new SalesRecordPrinter();

    public static void main(String[] args) throws FileNotFoundException {
        SaleRecords records = new SaleRecords();
        records.parseData();
        records.getTotalRevenue(allTransactions);
        records.getMostPopularProduct(allTransactions);
        records.getAveragePrice(allTransactions);
        records.getTotalProductsSoldByType(allTransactions);
        records.getDateOfHighestQuantitySalesOfAProduct(allTransactions, "Shirt");
        records.getDayOfWeekWithHighestRevenue(allTransactions);
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

    public BigDecimal getTotalRevenue(List<Transaction> allTransactions) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Transaction transaction : allTransactions) {
            totalRevenue = totalRevenue.add(transaction.getRevenue());
        }
        printer.printTotalRevenue(totalRevenue);
        return totalRevenue;
    }

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
        // Handles when the quantity sold of the most popular product is the same as other product quantities sold
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            if (highestCount == entry.getValue() && !Objects.equals(entry.getKey(), mostPopularProduct)) {
                mostPopularProduct += ", " + entry.getKey();
            }
        }
        printer.printPopularProduct(mostPopularProduct);
        return mostPopularProduct;
    }

    public BigDecimal getAveragePrice(List<Transaction> allTransactions) {
        // Assign each product and its price to key/value pairs in a map
        Map<String, BigDecimal> productsPriceMap = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            String product = transaction.getProduct();
            BigDecimal price = transaction.getSalePrice();
            productsPriceMap.put(product, price);
        }
        // For each loop through map to count the number of products and add all price values together by product type
        BigDecimal sumOfPrices = BigDecimal.ZERO;
        int numberOfProducts = 0;
        for (Map.Entry<String, BigDecimal> product : productsPriceMap.entrySet()) {
            numberOfProducts = productsPriceMap.size();
            sumOfPrices = sumOfPrices.add(product.getValue());
        }
        BigDecimal averagePrice = sumOfPrices.divide(BigDecimal.valueOf(numberOfProducts), 2, RoundingMode.HALF_UP);
        printer.printAveragePrice(averagePrice);
        return averagePrice;
    }

    public Map<String, Integer> getTotalProductsSoldByType(List<Transaction> allTransactions) {
        Map<String, Integer> totalProductsSold = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            String product = transaction.getProduct();
            int count = totalProductsSold.getOrDefault(product, 0);
            totalProductsSold.put(product, count + transaction.getQuantitySold());
        }
        printer.printTotalSoldOfEachProduct(totalProductsSold);
        return totalProductsSold;
    }

    public LocalDate getDateOfHighestQuantitySalesOfAProduct(List<Transaction> allTransactions, String productType) {
        Map<LocalDate, Integer> dateAndQuantityMap = new HashMap<>();
        LocalDate date = null;
        int highestQuantity = 0;
        for (Transaction transaction : allTransactions) {
            String product = transaction.getProduct();
            if (product.equals(productType)) {
                date = LocalDate.parse(transaction.getDate());
                int quantitySold = transaction.getQuantitySold();
                dateAndQuantityMap.put(date, quantitySold);
                if (quantitySold > highestQuantity) {
                    highestQuantity = quantitySold;
                }
            }
        }
        LocalDate dateWithHighestQuantity = null;
        for (Map.Entry<LocalDate, Integer> transaction : dateAndQuantityMap.entrySet()) {
            if (transaction.getValue() == highestQuantity) {
                dateWithHighestQuantity = transaction.getKey();
                break;
            }
        }
        printer.printDate(dateWithHighestQuantity, productType);
        return dateWithHighestQuantity;
    }

    // TODO  write additional methods to find values based on below questions

    public DayOfWeek getDayOfWeekWithHighestRevenue(List<Transaction> allTransactions) {
        // find transaction with highest revenue
        Map<LocalDate, BigDecimal> dateAndRevenueMap = new HashMap<>();
        BigDecimal revenue;
        LocalDate date;
        for (Transaction transaction : allTransactions) {
            date = LocalDate.parse(transaction.getDate());
            revenue = transaction.getRevenue();
            dateAndRevenueMap.put(date, revenue);
        }
        BigDecimal highestRevenue = BigDecimal.ZERO;
        BigDecimal currentRevenue;
        DayOfWeek dayOfWeek = null;
        for (Map.Entry<LocalDate, BigDecimal> entry : dateAndRevenueMap.entrySet()) {
            LocalDate transactionDate = entry.getKey();
            currentRevenue = entry.getValue();
            if (currentRevenue.compareTo(highestRevenue) > 0) {
                highestRevenue = currentRevenue;
                dayOfWeek = transactionDate.getDayOfWeek();
            }
        }
        printer.printDayOfWeek(dayOfWeek);
        return dayOfWeek;
    }


    // putInMap method taking in key, value params?

// Question 7: Calculate the total revenue for a specific date range.
// Question 8: Find the average sale price for each day of the week.

}