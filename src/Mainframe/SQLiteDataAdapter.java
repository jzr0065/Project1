package Mainframe;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    Connection conn = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    public boolean saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Products (ProductID, Name, Price, Quantity)" +
                    "VALUES(" + product.mProductID + ",'" + product.mName + "'," + product.mPrice + ',' + product.mQuantity
                    + ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.print(rs);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public CustomerModel loadCustomer(int CustomerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerID, Name, Phone, Address FROM Customers WHERE CustomerId = " + CustomerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerID");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getString("Phone");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public boolean saveCustomer(CustomerModel customer) {
        try {
            String sql = "INSERT INTO Customers (CustomerID, Name, Phone, Address)" +
                    "VALUES(" + customer.mCustomerID + ",'" + customer.mName + "','" + customer.mPhone + "','" + customer.mAddress
                    + "')";
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.print(rs);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }

}
