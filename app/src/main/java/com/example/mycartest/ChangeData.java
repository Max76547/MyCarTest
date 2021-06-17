package com.example.mycartest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycartest.sqlite.CarDatabaseHelper;

public class ChangeData extends AppCompatActivity {
    private Button button;
    private EditText editName, editYear, editMileage;
    private String topNames;
    private String topMileage;
    private String topYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        button = findViewById(R.id.button);
        editName = findViewById(R.id.editName);
        editMileage = findViewById(R.id.editMileage);
        editYear = findViewById(R.id.editYear);

        SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(this);

        try {
            //будем читать данные из базы
            SQLiteDatabase db = carDatabaseHelper.getReadableDatabase();
//Код чтения всех данных из базы (создаем курсор)
            Cursor cursor = db.query("GENERALDATA",
                    new String[] {"NAME", "MILEAGE", "NXTMILEAGE"},
                    null, null, null, null, null); //читаем все данные из таблицы
            if (cursor.moveToFirst()){

                //создаем строковый массив и заносим туда все имена
                topNames = cursor.getString(0);

                //создаем численный массив и заносим туда все данные о пробеге
                topMileage =cursor.getString(1);

                //аналогично и с изображениями
                topYear =cursor.getString(2);
            }
            editName.setText(topNames);
            editYear.setText(topYear);
            editMileage.setText(topMileage);

            cursor.close();
            db.close();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void onClick(View view) {
       topNames = editName.getText().toString();
       topYear = editYear.getText().toString();
       topMileage = editMileage.getText().toString();
        //обновляем данные в базе
        ContentValues topValues = new ContentValues();
        topValues.put("NAME", topNames);
        topValues.put ("MILEAGE", topMileage);
        topValues.put ("NXTMILEAGE", topYear);
//получаем ссылку на помощника SQLite
        SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper( this );
// Чтобы избежать исключения, добавляем обработчик исключения
        try {
//доступ к базе данных
            SQLiteDatabase db = carDatabaseHelper.getWritableDatabase();
            db.update("GENERALDATA",
                    topValues,
                    "_id=?",
                    new String[] { Integer.toString(1) } );
            button.setText("Изменить");
            db.close();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        Toast toast = Toast.makeText(this,
                "Данные загружены",
                Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}