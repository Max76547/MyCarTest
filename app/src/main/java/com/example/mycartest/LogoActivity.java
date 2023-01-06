package com.example.mycartest;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {
TextView textView;
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        textView = findViewById(R.id.textLogo);
        imageView = findViewById(R.id.imageLogo);
        //создаем переменные для анимации
        Animation textAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_logo);// создаем анимацию
        imageView.startAnimation(textAnim); //запускаем анимацию
        textView.startAnimation(textAnim); //запускаем анимацию
        startMainActivity();
    }

    private void startMainActivity(){

        //создаем отдельный поток для счетчика--------------------
        //чтобы отобразить изменения в основном потоке
        new Thread(() -> {
            //исключение
            try {
                Thread.sleep(2000); //задержка в 2 сек
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(LogoActivity.this, MainActivity.class);
            startActivity(intent);
        }).start();
        //конец отдельного потока-----------------------------------------------------------

    }

}