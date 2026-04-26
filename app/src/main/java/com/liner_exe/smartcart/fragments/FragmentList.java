package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentListBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentList extends Fragment {
    private FragmentListBinding binding;
    private String listName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            listName = getArguments().getString("listName");
        }

        binding.listAppBar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.listAppBar.setTitle(listName);
    }
}