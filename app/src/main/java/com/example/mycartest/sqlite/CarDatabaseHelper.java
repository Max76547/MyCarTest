package com.example.mycartest.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mycar"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных

    //вызовем конструктор суперкласса и передадим имя и версию базы данных
    public CarDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0);
    }

    //при обновлении версии выполняется данный метод
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion);
    }

    //при откате версии выполняется данный метод
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //создаем объект, куда загружаем данные
    private static void insertGeneralData(SQLiteDatabase db, String name,
                                          int mileage, int nxt_mileage) {
        ContentValues generalValues = new ContentValues();
        generalValues.put("NAME", name);
        generalValues.put("MILEAGE", mileage);
        generalValues.put("NXTMILEAGE", nxt_mileage);
        db.insert("GENERALDATA", null, generalValues); //загружаем непосредственно в таблицу
    }

    //заполняем таблицу
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion) {
        if (oldVersion < 1) {
            //создаем базу данных с id строки, именем, пробегом и картинкой
            db.execSQL("CREATE TABLE GENERALDATA ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "MILEAGE INTEGER, "
                    + "NXTMILEAGE INTEGER); ");
            //заполняем данную таблицу информацией
            insertGeneralData(db, "Chevrolet Lacetti", 94800, 2007);
            insertGeneralData(db, "Замена масла", 94300, 10000);
            insertGeneralData(db, "Замена ремня ГРМ", 94600, 50000);
        }

    }
}
