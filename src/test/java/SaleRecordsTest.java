import model.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleRecordsTest {

    private static final Transaction TEST_TRANSACTION_1 = new Transaction("2022-01-01", "Shirt", 10, BigDecimal.valueOf(20.00));
    private static final Transaction TEST_TRANSACTION_2 = new Transaction("2022-01-02", "Pants", 5, BigDecimal.valueOf(25.00));
    private static final Transaction TEST_TRANSACTION_3 = new Transaction("2022-01-03", "Shoes", 3, BigDecimal.valueOf(50.00));
    private static final Transaction TEST_TRANSACTION_4 = new Transaction("2022-01-11", "Shoes", 9, BigDecimal.valueOf(50.00));

    private static final Transaction TEST_TRANSACTION_5 = new Transaction("2022-01-28", "Shirt", 4, BigDecimal.valueOf(20.00));
    private static final Transaction TEST_TRANSACTION_6 = new Transaction("2022-02-02", "Pants", 4, BigDecimal.valueOf(25.00));
    private static final Transaction TEST_TRANSACTION_7 = new Transaction("2022-02-03", "Shoes", 4, BigDecimal.valueOf(50.00));

    private static final Transaction TEST_TRANSACTION_8 = new Transaction("2022-02-03", "Shirt", 4, BigDecimal.valueOf(20.00));

    @Test
    public void getTotalRevenue_returns_sum_of_quantity_times_price_for_each_transaction_happy_path() {
        // Arrange
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1);
        testTransactionList.add(TEST_TRANSACTION_2);
        testTransactionList.add(TEST_TRANSACTION_3);
        SaleRecords testRecords = new SaleRecords();
        // Act
        BigDecimal expected = new BigDecimal("475.00");
        BigDecimal actual = testRecords.getTotalRevenue(testTransactionList);
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMostPopularProduct_returns_product_type_with_highest_quantity_sales() {
        // Arrange
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1);
        testTransactionList.add(TEST_TRANSACTION_2);
        testTransactionList.add(TEST_TRANSACTION_3);
        testTransactionList.add(TEST_TRANSACTION_4);
        SaleRecords testRecords = new SaleRecords();
        // Act
        String expected = "Shoes";
        String actual = testRecords.getMostPopularProduct(testTransactionList);
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMostPopularProduct_when_two_products_have_highest_quantities_sold() {
        // Arrange
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_5);
        testTransactionList.add(TEST_TRANSACTION_6);
        testTransactionList.add(TEST_TRANSACTION_3);
        SaleRecords testRecords = new SaleRecords();
        // Act
        String expected = "Shirt, Pants";
        String actual = testRecords.getMostPopularProduct(testTransactionList);
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMostPopularProduct_when_products_all_have_same_quantities_sold() {
        // Arrange
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_5);
        testTransactionList.add(TEST_TRANSACTION_6);
        testTransactionList.add(TEST_TRANSACTION_7);
        SaleRecords testRecords = new SaleRecords();
        // Act
        String expected = "Shirt, Pants, Shoes";
        String actual = testRecords.getMostPopularProduct(testTransactionList);
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAverageSalePrice_returns_sum_of_sale_prices_divided_by_number_of_different_product_types_listed() {
        // Arrange
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1);
        testTransactionList.add(TEST_TRANSACTION_2);
        testTransactionList.add(TEST_TRANSACTION_3);
        testTransactionList.add(TEST_TRANSACTION_4);
        SaleRecords testRecords = new SaleRecords();
        // Act
        BigDecimal expected = new BigDecimal("31.67");
        BigDecimal actual = testRecords.getAveragePrice(testTransactionList);
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTotalProductsSoldByType_returns_total_quantity_of_products_sold_by_type() {
        // Arrange
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1);
        testTransactionList.add(TEST_TRANSACTION_2);
        testTransactionList.add(TEST_TRANSACTION_3);
        testTransactionList.add(TEST_TRANSACTION_4);
        // Act
        SaleRecords testRecords = new SaleRecords();
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Shirt", 10);
        expected.put("Pants", 5);
        expected.put("Shoes", 12);
        Map<String, Integer> result = testRecords.getTotalProductsSoldByType(testTransactionList);
        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDateOfHighestQuantitySalesOfAProduct_returns_LocalDate_of_highest_sales_of_product() {
        // Arrange
        SaleRecords testRecords = new SaleRecords();
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1);
        testTransactionList.add(TEST_TRANSACTION_2);
        testTransactionList.add(TEST_TRANSACTION_3);
        testTransactionList.add(TEST_TRANSACTION_4);
        String testProductType = "Shoes";
        // Act
        LocalDate expected = LocalDate.of(2022, 01, 11);
        LocalDate result = testRecords.getDateOfHighestQuantitySalesOfAProduct(testTransactionList, testProductType);
        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDayOfWeekWithHighestRevenue_returns_DayOfWeek_with_highest_revenue() {
        // Arrange
        SaleRecords testRecords = new SaleRecords();
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1); // Saturday, $200
        testTransactionList.add(TEST_TRANSACTION_2); // Sunday, $125
        testTransactionList.add(TEST_TRANSACTION_3); // Monday, $150
        testTransactionList.add(TEST_TRANSACTION_4); // Tuesday, $450
        Map<LocalDate, BigDecimal> dateAndRevenueMap = testRecords.createMapOfAllDatesAndRevenues(testTransactionList);
        // Act
        DayOfWeek expected = DayOfWeek.TUESDAY;
        DayOfWeek result = testRecords.getDayOfWeekWithHighestRevenue(dateAndRevenueMap);
        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getRevenueByTimePeriod_returns_total_revenue_for_given_time_period() {
        // Arrange
        SaleRecords testRecords = new SaleRecords();
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1); // $200
        testTransactionList.add(TEST_TRANSACTION_2); // $125
        testTransactionList.add(TEST_TRANSACTION_3); // $150
        testTransactionList.add(TEST_TRANSACTION_4); // $450
        // Act
        BigDecimal expected = BigDecimal.valueOf(925.00).setScale(2);
        BigDecimal result = testRecords.getRevenueByTimePeriod(testTransactionList, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 11));
        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getRevenueByTimePeriod_returns_correct_revenue_when_given_single_day_range() {
        // Arrange
        SaleRecords testRecords = new SaleRecords();
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_7); // $200
        testTransactionList.add(TEST_TRANSACTION_8); // $80
        // Act
        BigDecimal expected = BigDecimal.valueOf(280.00).setScale(2);
        BigDecimal result = testRecords.getRevenueByTimePeriod(testTransactionList, LocalDate.of(2022, 02, 03), LocalDate.of(2022, 02, 03));
        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getAverageRevenueForEachDayOfWeek_happy_path() {
        // Arrange
        SaleRecords testRecords = new SaleRecords();
        List<Transaction> testTransactionList = new ArrayList<>();
        testTransactionList.add(TEST_TRANSACTION_1); // Saturday, $200
        testTransactionList.add(TEST_TRANSACTION_2); // Sunday, $125
        testTransactionList.add(TEST_TRANSACTION_3); // Monday, $150
        testTransactionList.add(TEST_TRANSACTION_4); // Tuesday, $450
        testTransactionList.add(TEST_TRANSACTION_5); // Friday, $80
        testTransactionList.add(TEST_TRANSACTION_6); // Wednesday, $100
        testTransactionList.add(TEST_TRANSACTION_7); // Thursday, $200
        testTransactionList.add(TEST_TRANSACTION_8); // Thursday, $80

        Map<DayOfWeek, BigDecimal> expected = new HashMap<>();
        testRecords.createMapOfAllDatesAndRevenues(testTransactionList);
        // Act
        Map<DayOfWeek, BigDecimal> result = testRecords.getAverageRevenueForEachDayOfWeek(testTransactionList);
        // Assert
        Assert.assertEquals(expected, result);
    }

    // TODO create additional test cases
}
