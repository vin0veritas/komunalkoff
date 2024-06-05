package com.example.komunalkoff;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "komunalka.db";
    private static final int DATABASE_VERSION = 2; // Обновлено с версии 1 до 2

    // БД
    public static final String TABLE_RATES = "rates"; // тарифы
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_LIGHT = "light";
    public static final String COLUMN_WATER = "water";
    public static final String COLUMN_HEATING = "heating"; //отопление
    public static final String COLUMN_STOCK = "stock";

    public static final String TABLE_PAYMENTS = "payments";
    public static final String COLUMN_PAYMENT_ID = "payment_id";
    public static final String COLUMN_SERVICE_TYPE = "service_type";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";

    // Конструктор для пердачи данных контекста, имени и версии
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // превая  таблица
        String TABLE_CREATE_RATES =
                "CREATE TABLE " + TABLE_RATES + " (" +
                        COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_LIGHT + " REAL, " +
                        COLUMN_WATER + " REAL, " +
                        COLUMN_HEATING + " REAL, " +
                        COLUMN_STOCK + " REAL);";
        //второая таблица
        String TABLE_CREATE_PAYMENTS =
                "CREATE TABLE " + TABLE_PAYMENTS + " (" +
                        COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USER_ID + " TEXT, " +
                        COLUMN_SERVICE_TYPE + " TEXT, " +
                        COLUMN_AMOUNT + " REAL, " +
                        COLUMN_DATE + " TEXT);";

        db.execSQL(TABLE_CREATE_RATES);
        db.execSQL(TABLE_CREATE_PAYMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // создание одной таблицы при устаревшей версии
        if (oldVersion < 2) {
            String TABLE_CREATE_PAYMENTS =
                    "CREATE TABLE " + TABLE_PAYMENTS + " (" +
                            COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_USER_ID + " TEXT, " +
                            COLUMN_SERVICE_TYPE + " TEXT, " +
                            COLUMN_AMOUNT + " REAL, " +
                            COLUMN_DATE + " TEXT);";
            db.execSQL(TABLE_CREATE_PAYMENTS);
        }
    }

    // новая запись о платежах
    public void addPayment(String userId, String serviceType, double amount, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_SERVICE_TYPE, serviceType);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_PAYMENTS, null, values);
        db.close();
    }

}

