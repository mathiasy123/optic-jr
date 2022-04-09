package dao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;
import entities.Product;

public class ProductDAOImpl implements ProductDAO {
    private DatabaseHelper databaseHelper;
    private Context context;

    public ProductDAOImpl(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    @Override
    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<Product>();
        Cursor cursor = databaseHelper.getReadableDatabase().query("products", new String[]{"*"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Product product = new Product(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("image")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
            productList.add(product);
        }
        return productList;
    }
}

