package com.example.lab8_mob201;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab8_mob201.Fragment.Fragment_Bai1;
import com.example.lab8_mob201.Fragment.Fragment_Bai2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initByViewId();
        this.initToolBar();
        this.initBottomNav();
    }

    private void initBottomNav() {
        OnNavigationItemSelectedListener onNavigationItemSelectedListener = new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_bai1:
                        loadFragment(new Fragment_Bai1());
                        actionBar.setTitle("Bài 1 - Lab8 - MOB201");
                        checkedIdNav(R.id.nav_bai1);
                        break;
                    case R.id.nav_bai2:
                        loadFragment(new Fragment_Bai2());
                        actionBar.setTitle("Bài 2 - Lab8 - MOB201");
                        checkedIdNav(R.id.nav_bai2);
                        break;

                }
                return true;
            }
        };
        this.bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(new Fragment_Bai1());
        this.actionBar.setTitle("Bài 1 - Lab8 - MOB201");
        this.checkedIdNav(R.id.nav_bai1);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initToolBar() {
        this.actionBar = getSupportActionBar();
    }

    private void initByViewId() {
        this.bottomNavigationView = findViewById(R.id.nav_bottom);
    }

    private void checkedIdNav(int item) {
        this.bottomNavigationView.getMenu().findItem(item).setChecked(true);
    }
}