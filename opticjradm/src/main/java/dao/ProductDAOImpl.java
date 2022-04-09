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
    private Cursor cursor;

    public ProductDAOImpl(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    @Override
    public List<String> getProducts() {
        List<String> productList = new ArrayList<String>();
        cursor = databaseHelper.getReadableDatabase().query("products", new String[]{"name"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            productList.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        }
        return productList;
    }
}
