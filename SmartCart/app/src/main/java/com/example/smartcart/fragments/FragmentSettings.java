package com.example.smartcart.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcart.R;
import com.example.smartcart.databinding.FragmentSettingsBinding;

public class FragmentSettings extends Fragment {
    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

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