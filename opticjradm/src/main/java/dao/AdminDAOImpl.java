package dao;

import android.content.Context;
import android.database.Cursor;

import database.DatabaseHelper;
import entities.Admin;

public class AdminDAOImpl implements AdminDAO {
    private DatabaseHelper databaseHelper;
    private Context context;

    public AdminDAOImpl(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    @Override
    public Admin getAdmin(String email, String password) {
        databaseHelper = new DatabaseHelper(context);
        Cursor cursor = databaseHelper.getReadableDatabase().query("admins", new String[]{"*"}, "email=? AND password=?", new String[]{email, password}, null, null, null);
        if (cursor.moveToFirst()) {
            return new Admin(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getString(cursor.getColumnIndexOrThrow("email")), cursor.getString(cursor.getColumnIndexOrThrow("password")));
        }

        return null;
    }
}
