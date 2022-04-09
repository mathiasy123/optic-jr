package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;
import entities.Order;

public class OrderDAOImpl implements OrderDAO {
    private DatabaseHelper databaseHelper;
    private Context context;
    private Cursor cursor;
    private ContentValues orderContentValues;

    private List<Order> orderList;

    public OrderDAOImpl(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orderList = new ArrayList<Order>();
        cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT orders.id AS order_id," +
                                                                        "products.image AS product_image," +
                                                                        "products.name AS product_name," +
                                                                        "products.price AS product_price," +
                                                                        "customer_name," +
                                                                        "customer_email," +
                                                                        "customer_phone_number," +
                                                                         "customer_address," +
                                                                        "DATE(order_at) AS order_at " +
                                                                    "FROM orders " +
                                                                    "INNER JOIN products " +
                                                                    "ON orders.product_id = products.id " +
                                                                    "ORDER BY order_at DESC", null);
        while (cursor.moveToNext()) {
            Order order = new Order(cursor.getInt(cursor.getColumnIndexOrThrow("order_id")), cursor.getString(cursor.getColumnIndexOrThrow("product_image")), cursor.getString(cursor.getColumnIndexOrThrow("product_name")), cursor.getDouble(cursor.getColumnIndexOrThrow("product_price")), cursor.getString(cursor.getColumnIndexOrThrow("customer_name")), cursor.getString(cursor.getColumnIndexOrThrow("customer_email")), cursor.getString(cursor.getColumnIndexOrThrow("customer_phone_number")), cursor.getString(cursor.getColumnIndexOrThrow("customer_address")), cursor.getString(cursor.getColumnIndexOrThrow("order_at")));
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public Order getOrder(int id) {
        cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT orders.id AS order_id," +
                                                                        "products.image AS product_image," +
                                                                        "products.name AS product_name," +
                                                                        "products.price AS product_price," +
                                                                        "customer_name," +
                                                                        "customer_email," +
                                                                        "customer_phone_number," +
                                                                        "customer_address," +
                                                                        "DATE(order_at) AS order_at " +
                                                                    "FROM orders " +
                                                                    "INNER JOIN products " +
                                                                    "ON orders.product_id = products.id " +
                                                                    "WHERE order_id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            return  new Order(cursor.getInt(cursor.getColumnIndexOrThrow("order_id")), cursor.getString(cursor.getColumnIndexOrThrow("product_image")), cursor.getString(cursor.getColumnIndexOrThrow("product_name")), cursor.getDouble(cursor.getColumnIndexOrThrow("product_price")), cursor.getString(cursor.getColumnIndexOrThrow("customer_name")), cursor.getString(cursor.getColumnIndexOrThrow("customer_email")), cursor.getString(cursor.getColumnIndexOrThrow("customer_phone_number")), cursor.getString(cursor.getColumnIndexOrThrow("customer_address")), cursor.getString(cursor.getColumnIndexOrThrow("order_at")));
        }
        return null;
    }

    @Override
    public int getCountOrder() {
        return (int) DatabaseUtils.queryNumEntries(databaseHelper.getReadableDatabase(), "orders");
    }

    @Override
    public double getSumOrder() {
        cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT orders.id AS order_id," +
                                                                        "SUM(products.price) AS total_income " +
                                                                        "FROM orders " +
                                                                        "INNER JOIN products " +
                                                                    "ON orders.product_id = products.id", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndexOrThrow("total_income"));
        }

        return 0;
    }

    @Override
    public long createOrder(Order order) {
        cursor = databaseHelper.getReadableDatabase().query("products", new String[]{"id"}, "name=?", new String[]{order.getProductName()}, null, null, null);

        if (cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            orderContentValues = new ContentValues();
            orderContentValues.put("product_id", productId);
            orderContentValues.put("customer_name", order.getCustomerName());
            orderContentValues.put("customer_email", order.getCustomerEmail());
            orderContentValues.put("customer_phone_number", order.getCustomerPhone());
            orderContentValues.put("customer_address", order.getCustomerAddress());
            orderContentValues.put("order_at", order.getOrderDate());
            return databaseHelper.getWritableDatabase().insert("orders", null, orderContentValues);
        }

        return -1;
    }

    @Override
    public long updateOrder(Order order, int id) {
        cursor = databaseHelper.getReadableDatabase().query("products", new String[]{"id"}, "name=?", new String[]{order.getProductName()}, null, null, null);

        if (cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            orderContentValues = new ContentValues();
            orderContentValues.put("product_id", productId);
            orderContentValues.put("customer_name", order.getCustomerName());
            orderContentValues.put("customer_email", order.getCustomerEmail());
            orderContentValues.put("customer_phone_number", order.getCustomerPhone());
            orderContentValues.put("customer_address", order.getCustomerAddress());
            orderContentValues.put("order_at", order.getOrderDate());
            return databaseHelper.getWritableDatabase().update("orders", orderContentValues, "id=?", new String[]{String.valueOf(id)});
        }
        return -1;
    }

    @Override
    public long deleteOrder(int id) {
        return databaseHelper.getWritableDatabase().delete("orders", "id=?", new String[]{String.valueOf(id)});
    }
}
