package com.liner_exe.smartcart.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ShoppingListsAdapter;
import com.liner_exe.smartcart.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private ShoppingListsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);
        return binding.getRoot();
    }
}