package dao;

import java.util.List;

import entities.Customer;

public interface CustomerDAO {
    public List<Customer> getCustomers();
    public Customer getCustomer(int id);
    public int getCountCustomer();
    public long createCustomer(Customer customer);
    public long updateCustomer(Customer customer, int id);
    public long deleteCustomer(int id);
}
