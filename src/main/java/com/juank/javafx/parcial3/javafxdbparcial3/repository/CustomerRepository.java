package com.juank.javafx.parcial3.javafxdbparcial3.repository;

import com.juank.javafx.parcial3.javafxdbparcial3.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BD_NUEVAJAVA;user=sa;password=Password123#;encrypt=false;trustServerCertificate=true";

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers ORDER BY CustomerID";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getString("CustomerID"));
                customer.setCompanyName(rs.getString("CompanyName"));
                customer.setContactName(rs.getString("ContactName"));
                customer.setContactTitle(rs.getString("ContactTitle"));
                customer.setAddress(rs.getString("Address"));
                customer.setCity(rs.getString("City"));
                customer.setRegion(rs.getString("Region"));
                customer.setPostalCode(rs.getString("PostalCode"));
                customer.setCountry(rs.getString("Country"));
                customer.setPhone(rs.getString("Phone"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load customers", e);
        }

        return customers;
    }

    public Customer findById(String customerID) {
        Customer customer = null;
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerID(rs.getString("CustomerID"));
                customer.setCompanyName(rs.getString("CompanyName"));
                customer.setContactName(rs.getString("ContactName"));
                customer.setContactTitle(rs.getString("ContactTitle"));
                customer.setAddress(rs.getString("Address"));
                customer.setCity(rs.getString("City"));
                customer.setRegion(rs.getString("Region"));
                customer.setPostalCode(rs.getString("PostalCode"));
                customer.setCountry(rs.getString("Country"));
                customer.setPhone(rs.getString("Phone"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to find customer", e);
        }

        return customer;
    }

    public void save(Customer customer) {
        String sql = "INSERT INTO Customers (CustomerID, CompanyName, ContactName, ContactTitle, Address, City, Region, PostalCode, Country, Phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getCompanyName());
            stmt.setString(3, customer.getContactName());
            stmt.setString(4, customer.getContactTitle());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getRegion());
            stmt.setString(8, customer.getPostalCode());
            stmt.setString(9, customer.getCountry());
            stmt.setString(10, customer.getPhone());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to save customer");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save customer", e);
        }
    }

    public void update(Customer customer) {
        String sql = "UPDATE Customers SET CompanyName = ?, ContactName = ?, ContactTitle = ?, Address = ?, City = ?, Region = ?, PostalCode = ?, Country = ?, Phone = ? WHERE CustomerID = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCompanyName());
            stmt.setString(2, customer.getContactName());
            stmt.setString(3, customer.getContactTitle());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getCity());
            stmt.setString(6, customer.getRegion());
            stmt.setString(7, customer.getPostalCode());
            stmt.setString(8, customer.getCountry());
            stmt.setString(9, customer.getPhone());
            stmt.setString(10, customer.getCustomerID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Customer not found or failed to update");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update customer", e);
        }
    }

    public void delete(String customerID) {
        String sql = "DELETE FROM Customers WHERE CustomerID = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Customer not found or failed to delete");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete customer", e);
        }
    }

    public List<Customer> searchByCompanyName(String companyName) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers WHERE CompanyName LIKE ? ORDER BY CompanyName";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + companyName + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getString("CustomerID"));
                customer.setCompanyName(rs.getString("CompanyName"));
                customer.setContactName(rs.getString("ContactName"));
                customer.setContactTitle(rs.getString("ContactTitle"));
                customer.setAddress(rs.getString("Address"));
                customer.setCity(rs.getString("City"));
                customer.setRegion(rs.getString("Region"));
                customer.setPostalCode(rs.getString("PostalCode"));
                customer.setCountry(rs.getString("Country"));
                customer.setPhone(rs.getString("Phone"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public List<Customer> searchByCity(String city) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers WHERE City LIKE ? ORDER BY City, CompanyName";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + city + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getString("CustomerID"));
                customer.setCompanyName(rs.getString("CompanyName"));
                customer.setContactName(rs.getString("ContactName"));
                customer.setContactTitle(rs.getString("ContactTitle"));
                customer.setAddress(rs.getString("Address"));
                customer.setCity(rs.getString("City"));
                customer.setRegion(rs.getString("Region"));
                customer.setPostalCode(rs.getString("PostalCode"));
                customer.setCountry(rs.getString("Country"));
                customer.setPhone(rs.getString("Phone"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
