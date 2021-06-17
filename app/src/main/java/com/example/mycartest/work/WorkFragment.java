package com.example.mycartest.work;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mycartest.CaptionedImagesAdapter;
import com.example.mycartest.R;
import com.example.mycartest.sqlite.CarDatabaseHelper;

import java.util.Objects;


public class WorkFragment extends Fragment {
    //Эти приватные переменные добавляются для того, чтобы базу данных и курсор мож-
//но было закрыть в методе onDestroy().
    private SQLiteDatabase db;
    private Cursor cursor;

    private String[] replaceNames; //переменная для названия замены
    private   String[] replaceMileage ; //переменная для пробега до замены детали
    private String[] replaceNxtMileage ; //переменная для интервала замены пробега
    private String mainMileage; //переменная для получения пробега авто



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView workRecycle = (RecyclerView) inflater.inflate(R.layout.fragment_work,
                container,false);
        //-----------------------------------------------------------------------------------------------
        //получаем ссылку на базу данных
        SetupCursor();

        //массивы передаются адаптеру
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(replaceNames, replaceMileage, replaceNxtMileage);
        workRecycle.setAdapter(adapter);
        //отображение карточек в виде списка
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        workRecycle.setLayoutManager(layoutManager);
//HORIZONTAL - выбираем горизонтальную ориентацию; если выбрать true - выведет список в обратном порядке

        adapter.setListener(position -> {
            Intent intent = new Intent(getActivity(), WorkDetailActivity.class);
            intent.putExtra(WorkDetailActivity.EXTRA_WORK_ID, position);
            intent.putExtra(WorkDetailActivity.EXTRA_MILEAGE, mainMileage);
            Objects.requireNonNull(getActivity()).startActivity(intent);
        });

        return workRecycle;
    }

    // фрагмент обновляет свои представления при выводе на экран.
    @Override
    public void onStart() {
        super.onStart();
        SetupCursor();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cursor.close();
        db.close();
    }

    public void SetupCursor(){
        SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(getActivity());

        try {
            //будем читать данные из базы
            db = carDatabaseHelper.getWritableDatabase();
//Код чтения всех данных из базы (создаем курсор)
            cursor = db.query("GENERALDATA",
                    new String[] {"NAME", "MILEAGE",
                            "NXTMILEAGE"},
                    null, null, null, null, null); //читаем все данные из таблицы


            int allCount = (cursor.getCount())-1; //получаем общее к-во строк
            int count = 0; //переменная для наращивания счета для массива
            replaceNames = new String[allCount];
            replaceMileage = new String[allCount];
            replaceNxtMileage  = new String[allCount];

            String[] nxtMileage = new String[allCount];
            int[] k = new int[allCount];

            if (cursor.moveToFirst()){
                mainMileage =cursor.getString(1); //получаем пробег
            }
            while (cursor.moveToNext()){

                //создаем строковый массив и заносим туда все имена
                replaceNames[count] = cursor.getString(0);

                //создаем строковый массив и заносим туда все данные о пробеге
                replaceMileage[count] =cursor.getString(1);

                //создаем строковый массив и заносим туда все данные об интервале замен
                nxtMileage[count] = cursor.getString(2);

                //получаем значение для пробега при следующей замене
                //String.valueOf(a) переводит переменную int в String
                k[count] = Integer.parseInt(replaceMileage[count]) + Integer.parseInt(nxtMileage[count]);
                replaceNxtMileage[count]= String.valueOf(k[count]);

                count++;
            }

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(),
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        //-----------------------------------------------------------------------------------------------
    }

}



