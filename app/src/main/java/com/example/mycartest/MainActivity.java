package com.example.mycartest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mycartest.work.WorkFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //добавляем панель инструментов
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Связывание SectionsPagerAdapter с ViewPager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return 2; //Компонент ViewPager содержит 2 страницы, по одной для каждого фрагмента
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) { //Метод getCount() определяет 2 страницы, так что метод getItem() должен
                //  запрашивать только фрагменты для позиций этих 2 страниц.
                case 0:
                    return new WorkFragment();
                case 1:
                    return new TopFragment();
                default: 
            }
            return null;
        }

        //добавляем текст на каждую вкладку
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.work_tab);
                case 1:
                    return getResources().getText(R.string.top_tab);
                default: 
            }
            return null;
        }

    }


}
