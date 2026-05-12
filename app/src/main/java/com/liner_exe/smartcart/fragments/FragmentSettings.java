package com.liner_exe.smartcart.fragments;

import android.app.AlertDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentSettingsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentSettings extends Fragment {
    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation
                .findNavController(requireActivity(), R.id.main_nav_host);

        binding.productsManagementButton.setOnClickListener(v -> {
            NavDirections action = MainFragmentDirections
                    .actionMainFragmentToProductsManagementFragment();
            navController.navigate(action);
        });

        binding.categoriesManagementButton.setOnClickListener(v -> {
            NavDirections action = MainFragmentDirections
                    .actionMainFragmentToCategoryManagementFragment();
            navController.navigate(action);
        });

        binding.storesManagementButton.setOnClickListener(v -> {
            NavDirections action = MainFragmentDirections
                    .actionMainFragmentToStoresManagementFragment();
            navController.navigate(action);
        });

        binding.aboutButton.setOnClickListener(v -> {
            showAboutDialog();
        });
    }

    private void showAboutDialog() {
        PackageInfo packageInfo = null;
        ApplicationInfo ai = null;

        try {
            PackageManager pm = getContext().getPackageManager();
            String packageName = getContext().getPackageName();
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        new AlertDialog.Builder(getContext())
                .setTitle("О приложении")
                .setMessage(String.format("Версия %s\nРазработчик: %s", packageInfo.versionName, getDeveloperName()))
                .setPositiveButton("ОК", null)
                .show();
    }

    private String getDeveloperName() {
        try {
            ApplicationInfo ai = getContext().getPackageManager()
                    .getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = ai.metaData;
            return bundle.getString("developer_name");
        } catch (Exception e) {
            return "Unknown";
        }
    }
}