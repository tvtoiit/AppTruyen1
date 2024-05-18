package com.example.appthibanglaixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.appthibanglaixe.Activity.LoginActivity;
import com.example.appthibanglaixe.Adapter.ViewPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPage;
    private BottomNavigationView mbottomNavigationView;
    // khai báo toobar
    Toolbar toobar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        //khởi tạo adapter view page
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPage.setAdapter(viewPageAdapter);
        XuliTabMenuHome();
    }

    private void XuliTabMenuHome() {
    // xự kiện chuyển page
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mbottomNavigationView.getMenu().findItem(R.id.mh_menu_homepage).setChecked(true);
                        break;
                    case 1:
                        mbottomNavigationView.getMenu().findItem(R.id.mh_menu_test).setChecked(true);
                        break;
                    case 2:
                        mbottomNavigationView.getMenu().findItem(R.id.mh_menu_practice).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mh_menu_homepage:
                        mViewPage.setCurrentItem(0);
                        break;
                    case R.id.mh_menu_test:
                        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                       // boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                        String username = sharedPreferences.getString("username", "");
                        if (username != "") {
                            mViewPage.setCurrentItem(1);
                        } else {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.mh_menu_practice:
                        mViewPage.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }

    private void Anhxa() {
        mViewPage = findViewById(R.id.view_pager);
        mbottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    long countTime;
    Toast toast;
    @Override
    public void onBackPressed() {
        if(countTime+2000>System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }
        else{
            toast = Toast.makeText(this, "Nhấn Back 1 lần nữa để thoát", Toast.LENGTH_SHORT);
            toast.show();
        }
        countTime = System.currentTimeMillis();
    }
}