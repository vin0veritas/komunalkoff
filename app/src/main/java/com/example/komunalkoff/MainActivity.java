package com.example.komunalkoff;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigationrail.NavigationRailView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this); // поддержка полноэкранного режима
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); // экземпляр
        NavigationRailView navigationRailView = findViewById(R.id.navigation_rail);
        navigationRailView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment;
                if (item.getItemId() == R.id.home) {
                    fragment = new HomeFragment();
                } else if (item.getItemId() == R.id.profile) {
                    checkAuthentication();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        // Пользователь вошел в систему, открываем профиль
                        fragment = new ProfileLoggedFragment();
                    } else {
                        fragment = new ProfileFragment();
                    }
                } else if (item.getItemId() == R.id.income) {
                    fragment = new HistoryFragment();
                } else if (item.getItemId() == R.id.expense) {
                    fragment = new ExpenseFragment();
                } else if (item.getItemId() == R.id.rate) {
                    fragment = new RateFragment();
                } else if (item.getItemId() == R.id.app_info) {
                    fragment = new AppInfoFragment();
                } else {
                    return false;
                }
                // замена текущего фрагмента выбранным фрагментом
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });

        // при первом запуске
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    private void checkAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Здесь вы можете добавить дополнительные действия, если пользователь вошел в систему
    }
}
