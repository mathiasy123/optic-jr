package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static Context context;
    private final static String DB_NAME = "opticjr.db";
    private final static SQLiteDatabase.CursorFactory DB_FACTORY = null;
    private final static int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, DB_FACTORY, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "image VARCHAR(255)," +
                "name VARCHAR(50)," +
                "price REAL" +
                ")");

        sqLiteDatabase.execSQL("INSERT INTO products (image, name, price) " +
                "VALUES" +
                "('product_1.jpg', 'Semi-Rimless Titanium Rectangle', 20.90)," +
                "('product_2.jpg', 'Full-Rimmed Acectate Square', 15.00)," +
                "('product_3.jpg', 'Full-Rimmed Plastic Round', 12.00)," +
                "('product_4.jpg', 'Low-Bridge Metal Square', 24.00)," +
                "('product_5.jpg', 'Low-Bridge Titanium Round', 30.00)," +
                "('product_6.jpg', 'Low-Bridge Plastic Oval', 12.00)," +
                "('product_7.jpg', 'Full-Rimmed Acetate Round', 15.00)," +
                "('product_8.jpg', 'Full-Rimmed Acetate Round', 15.00)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS products");
        this.onCreate(sqLiteDatabase);
    }
}

