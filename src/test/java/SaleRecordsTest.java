import model.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleRecordsTest {

    private static final Transaction TEST_TRANSACTION_1 = new Transaction("2022-01-01", "Shirt", 10, BigDecimal.valueOf(20.00));
    private static final Transaction TEST_TRANSACTION_2 = new Transaction("2022-01-02", "Pants", 5, BigDecimal.valueOf(25.00));
    private static final Transaction TEST_TRANSACTION_3 = new Transaction("2022-01-03", "Shoes", 3, BigDecimal.valueOf(50.00));
    private static final Transaction TEST_TRANSACTION_4 = new Transaction("2022-01-11", "Shoes", 9, BigDecimal.valueOf(50.00));

    // TODO write test for parseData method

    @Test
    public void getTotalRevenue_returns_sum_of_quantity_times_price_for_each_transaction() {
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

}
