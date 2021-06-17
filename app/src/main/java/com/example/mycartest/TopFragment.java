package com.example.mycartest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycartest.sqlite.CarDatabaseHelper;

public class TopFragment extends Fragment implements View.OnClickListener {
   private String topNames;
   private String topMileage;
   private String topYear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        Button b = v.findViewById(R.id.button);
        b.setOnClickListener(this);

        return v;
    }


    // фрагмент обновляет свои представления при выводе на экран.
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        //получаем ссылку на базу данных и создаем курсор
        SetupCoursor();

        if (view != null) {
            //присваиваем название авто
          TextView textName = view.findViewById(R.id.textName);
            textName.setText(topNames);
            //присваиваем год выпуска
         TextView  textYear = view.findViewById(R.id.textYear);
            textYear.setText(topYear);
            //присваиваем пробег
          TextView textMileage = view.findViewById(R.id.textMileage);
            textMileage.setText(topMileage);
        }

    }

    //получаем ссылку на базу данных
   private void SetupCoursor (){

       SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(getActivity());

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

           cursor.close();
           db.close();

       } catch(SQLiteException e) {
           Toast toast = Toast.makeText(getActivity(),
                   "Database unavailable",
                   Toast.LENGTH_SHORT);
           toast.show();
       }
   }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ChangeData.class);
        startActivity(intent);
    }
}