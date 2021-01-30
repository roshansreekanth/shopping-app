package com.roshan.project2_roshan_sreekanth.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.roshan.project2_roshan_sreekanth.models.CustomerModel;
import com.roshan.project2_roshan_sreekanth.models.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "online_shop";

    private  static final String FIRST_TABLE_NAME = "customers";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADDRESS = "address";

    private static final String SECOND_TABLE_NAME = "products";
    private static final String KEY_ITEMNAME = "item_name";
    private static final String KEY_ITEMCODE = "item_code";
    private static final String KEY_ITEMPRICE = "item_price";
    private static final String KEY_ITEMQUANTITY = "item_quantity";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + FIRST_TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL, " +
                KEY_ADDRESS + " TEXT NOT NULL)";

        String CREATE_PRODUCT_TABLE  =  "CREATE TABLE " + SECOND_TABLE_NAME + " (" +
                KEY_ITEMCODE + " TEXT PRIMARY KEY, " +
                KEY_ITEMNAME + " TEXT NOT NULL, " +
                KEY_ITEMQUANTITY + " INTEGER NOT NULL, " +
                KEY_ITEMPRICE + " REAL NOT NULL)";

        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + FIRST_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCustomer(CustomerModel customerModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_USERNAME, customerModel.getUsername());
        cv.put(KEY_PASSWORD, customerModel.getPassword());
        cv.put(KEY_ADDRESS, customerModel.getAddress());

        long status = db.insert(FIRST_TABLE_NAME, null, cv);

        return status != -1;
    }

    public ArrayList<CustomerModel> getUsers(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<CustomerModel> userList = new ArrayList<CustomerModel>();

        String query = "SELECT username, password, address FROM " + FIRST_TABLE_NAME + " WHERE username = '" + username + "' AND password = '" + password + "'";
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String currentUsername = cursor.getString(cursor.getColumnIndex("username"));
            String currentPassword = cursor.getString(cursor.getColumnIndex("password"));
            String currentAddress = cursor.getString(cursor.getColumnIndex("address"));
            CustomerModel customer = new CustomerModel(currentUsername, currentPassword, currentAddress);
            userList.add(customer);
        }
        return userList;
    }

    public void insertProduct(ProductModel productModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();

        cv.put(KEY_ITEMNAME, productModel.getProductName());
        cv.put(KEY_ITEMCODE, productModel.getProductCode());
        cv.put(KEY_ITEMQUANTITY, getQuantity(productModel) + 1);
        cv.put(KEY_ITEMPRICE, productModel.getProductPrice());

        if(findProduct(productModel.getProductCode()))
        {
            db.update(SECOND_TABLE_NAME, cv, KEY_ITEMCODE + "= ?", new String[]{productModel.getProductCode()});
        }
        else
        {
            db.insert(SECOND_TABLE_NAME, null, cv);
        }
    }

    public ArrayList<ProductModel> getProducts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SECOND_TABLE_NAME;

        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String productName = cursor.getString(cursor.getColumnIndex(KEY_ITEMNAME));
            String productCode = cursor.getString(cursor.getColumnIndex(KEY_ITEMCODE));
            int productPrice = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ITEMPRICE)));
            ProductModel product = new ProductModel(productName, productCode, productPrice);
            products.add(product);
        }
        return products;
    }

    public boolean findProduct(String productCode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SECOND_TABLE_NAME + " WHERE " + KEY_ITEMCODE + " = '" + productCode + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToNext();
    }

    public int getQuantity(ProductModel product)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_ITEMQUANTITY + " FROM " + SECOND_TABLE_NAME + " WHERE " + KEY_ITEMCODE + " = " + "'" + product.getProductCode() + "'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext())
        {
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ITEMQUANTITY)));
        }
        return 0;
    }

    public void deleteItem(String productCode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SECOND_TABLE_NAME, KEY_ITEMCODE + "= ?", new String[]{productCode});
    }

    public void deleteAllItems()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SECOND_TABLE_NAME, null, null);
    }
}
