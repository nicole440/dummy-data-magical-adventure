# dummy-data-magical-adventure
This is a Java program that processes a dataset of transactions, and provides information and statistics about the sales. The program reads a CSV file that contains records of transactions, with information about the date, the product type, the quantity sold, and the sale price. The program then calculates various metrics and outputs them to the console.

## Requirements
To run this program, you need to have Java installed on your computer. The program was developed with Java 11, but should work with other versions as well.

## Usage
To use the program, follow these steps:

1. Clone the repository to your computer.
2. Open a terminal or command prompt in the project directory.
3. Compile the program by running the following command: javac SaleRecords.java
4. Run the program by running the following command: java SaleRecords

The program will read the dataset.csv file in the same directory as the program, parse the data, and output the following information:

- Total revenue: the sum of all sales revenue.
- Most popular product: the product type that was sold the most, along with its quantity sold.
- Average price: the average sale price of all products.
- Total products sold by type: the number of products sold for each product type.
- Date of highest quantity sales of a product: the date when the highest quantity of a given product type was sold.
- Day of week with highest revenue: the day of the week when the most revenue was generated.

- The program uses a Transaction class to represent each transaction, and a SalesRecordPrinter class to output the results to the console. The main class is SaleRecords, which contains the main method that runs the program.

## Limitations
This program has a few limitations:

- The dataset is assumed to be in a CSV file with a specific format: date, product type, quantity sold, sale price.
- The program assumes that the dataset contains valid and consistent data.
- The program outputs the results to the console, but could be extended to output to a file or a database.
- The program does not have a user interface, and is run from the command line.