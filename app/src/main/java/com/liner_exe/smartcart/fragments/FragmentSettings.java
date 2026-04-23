package com.liner_exe.smartcart.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentSettingsBinding;

public class FragmentSettings extends Fragment {
    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.productsManagementButton.setOnClickListener(view -> {
            NavController navController = Navigation
                    .findNavController(requireActivity(), R.id.main_nav_host);
            navController.navigate(R.id.action_mainFragment_to_productsManagementFragment);
        });

        binding.aboutButton.setOnClickListener(view -> {
            showAboutDialog();
        });

        return binding.getRoot();
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("О приложении")
                .setMessage("Версия 0.1.0\nРазработчик: liner.exe")
                .setPositiveButton("ОК", null)
                .show();
    }
}