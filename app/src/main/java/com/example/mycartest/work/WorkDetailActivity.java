package com.example.mycartest.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.ImageView;
import com.example.mycartest.MainActivity;
import com.example.mycartest.R;
import com.example.mycartest.sqlite.CarDatabaseHelper;
import android.widget.TextView;
import android.widget.Toast;

public class WorkDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WORK_ID = "workId";
    public static final String EXTRA_MILEAGE = "workMileage";

   private EditText textName;
   private EditText textMileage;
   private EditText textNxtMileage;
    private  Button button;
   private int workId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
//Set the toolbar as the activity's app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workId = (Integer)getIntent().getExtras().get(EXTRA_WORK_ID);
        String workMileage = (String)getIntent().getExtras().get(EXTRA_MILEAGE);

        TextView textMyMileage= findViewById(R.id.edit_my_mileage);
        textMyMileage.setText(workMileage);
        TextView textSummaMileage = findViewById(R.id.itog_zamen);

      button = findViewById(R.id.btn_change);
//(создаем курсор)----------------------------
//получаем ссылку на помощника SQLite
        SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(this);
// Чтобы избежать исключения, добавляем обработчик исключения
        try {
//доступ к базе данных только для чтения
            SQLiteDatabase db = carDatabaseHelper.getReadableDatabase();
            //Код чтения данных из базы
            Cursor cursor = db.query("GENERALDATA",
                    new String[]{"NAME", "MILEAGE", "IMAGE_RESOURCE_ID", "NXTMILEAGE"},
                    "_id = ?",
                    new String[] {Integer.toString(workId+2)}, //позицию нужно увеличить на 2, потому что адаптер передает позицию с 0, a 1 позиция это имя авто
                    null, null, null);

//переход к первой записи курсора
            if (cursor.moveToFirst()) {
//получение данных напитка из курсора
                String name = cursor.getString(0);
                String mileage = cursor.getString(1);
                int imageId =  cursor.getInt(2);
                String nxtMileage = cursor.getString(3);


//заполнение названия замены
                textName = findViewById(R.id.work_text);
                textName.setText(name);
                textMileage = findViewById(R.id.edit_change_mileage);
                textMileage.setText(mileage);
                textNxtMileage = findViewById(R.id.edit_change_nxt_mileage);
                textNxtMileage.setText(nxtMileage);

                int k = Integer.parseInt(mileage) + Integer.parseInt(nxtMileage);
                String strNxtMileage = String.valueOf(k);
                textSummaMileage.setText(strNxtMileage);

                //заполнение изображения
                ImageView imageView = findViewById(R.id.work_image);
                imageView.setImageDrawable(ContextCompat.getDrawable(this,imageId));
                imageView.setContentDescription(name);

            }
//закрываем курсор и базу данных
            cursor.close();
            db.close();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


        //------------------------------------------------------------------

    }

    public void onClickDetail(View view) {

        if (button.getText().equals("Изменить данные")){
          textName.setFocusableInTouchMode(true);
          textMileage.setFocusableInTouchMode(true);
          textNxtMileage.setFocusableInTouchMode(true);
           button.setText("Сохранить изменения");

        }
        else {
            String topNames = textName.getText().toString();
            String topMileage = textMileage.getText().toString();
            String topNxtMileage = textNxtMileage.getText().toString();
            //обновляем данные в базе
            ContentValues topValues = new ContentValues();
            topValues.put("NAME", topNames);
            topValues.put ("MILEAGE", topMileage);
            topValues.put ("NXTMILEAGE", topNxtMileage);
//получаем ссылку на помощника SQLite
            SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper( this );
// Чтобы избежать исключения, добавляем обработчик исключения
            try {
//доступ к базе данных только для чтения
                SQLiteDatabase db = carDatabaseHelper.getReadableDatabase();
                db.update("GENERALDATA",
                        topValues,
                        "_id=?",
                        new String[] { Integer.toString(workId+2) } );
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


        button.setText("Изменить данные");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
