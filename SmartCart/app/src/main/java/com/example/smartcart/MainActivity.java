package com.example.smartcart;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartcart.databinding.ActivityMainBinding;
import com.example.smartcart.fragments.FragmentHome;
import com.example.smartcart.fragments.FragmentMonitoring;
import com.example.smartcart.fragments.FragmentSettings;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        FragmentHome fragmentHome = new FragmentHome();
        setActiveFragment(fragmentHome);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                setActiveFragment(fragmentHome);
                return true;
            }

            if (itemId == R.id.navigation_monitoring) {
                FragmentMonitoring fragmentMonitoring = new FragmentMonitoring();
                setActiveFragment(fragmentMonitoring);
                return true;
            }

            if (itemId == R.id.navigation_settings) {
                FragmentSettings fragmentSettings = new FragmentSettings();
                setActiveFragment(fragmentSettings);
                return true;
            }

            return false;
        });

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setActiveFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}