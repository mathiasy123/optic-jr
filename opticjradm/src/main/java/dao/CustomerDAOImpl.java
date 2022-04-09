package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;
import entities.Customer;

public class CustomerDAOImpl implements CustomerDAO {
    private DatabaseHelper databaseHelper;
    private Context context;
    private Cursor cursor;
    private ContentValues customerContentValues;

    public CustomerDAOImpl(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customerList = new ArrayList<Customer>();
        cursor = databaseHelper.getReadableDatabase().query("customers", new String[]{"*"}, null, null, null, null, "name");
        while (cursor.moveToNext()) {
            Customer customer = new Customer(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getString(cursor.getColumnIndexOrThrow("email")), cursor.getString(cursor.getColumnIndexOrThrow("phone_number")), cursor.getString(cursor.getColumnIndexOrThrow("address")));
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public Customer getCustomer(int id) {
        cursor = databaseHelper.getReadableDatabase().query("customers", new String[]{"*"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            return new Customer(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getString(cursor.getColumnIndexOrThrow("email")), cursor.getString(cursor.getColumnIndexOrThrow("phone_number")), cursor.getString(cursor.getColumnIndexOrThrow("address")));
        }
        return null;
    }

    @Override
    public int getCountCustomer() {
        return (int) DatabaseUtils.queryNumEntries(databaseHelper.getReadableDatabase(), "customers");
    }

    @Override
    public long createCustomer(Customer customer) {
        customerContentValues = new ContentValues();
        customerContentValues.put("name", customer.getName());
        customerContentValues.put("email", customer.getEmail());
        customerContentValues.put("phone_number", customer.getPhone());
        customerContentValues.put("address", customer.getAddress());

        return databaseHelper.getWritableDatabase().insert("customers", null, customerContentValues);
    }

    @Override
    public long updateCustomer(Customer customer, int id) {
        customerContentValues = new ContentValues();
        customerContentValues.put("name", customer.getName());
        customerContentValues.put("email", customer.getEmail());
        customerContentValues.put("phone_number", customer.getPhone());
        customerContentValues.put("address", customer.getAddress());
        return databaseHelper.getWritableDatabase().update("customers", customerContentValues, "id=?", new String[]{String.valueOf(id)});
    }

    @Override
    public long deleteCustomer(int id) {
        return databaseHelper.getWritableDatabase().delete("customers", "id=?", new String[]{String.valueOf(id)});
    }
}
