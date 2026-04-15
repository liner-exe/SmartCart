package com.example.smartcart.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.models.Product;
import com.example.domain.models.ShoppingList;
import com.example.smartcart.R;
import com.example.smartcart.adapters.ProductAdapter;
import com.example.smartcart.adapters.ShoppingListsAdapter;
import com.example.smartcart.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private ShoppingListsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);

        Product[] products = {
                new Product(0, "Say", true, 67),
                new Product(0, "Wallahi", true, 15),
                new Product(0, "Bro", false, 10),
                new Product(0, "Say", false, 88),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
                new Product(0, "Wallahi", true, 1),
        };

        List<ShoppingList> shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList(0, "Первый список", List.of(products)));
        shoppingLists.add(new ShoppingList(0, "Второй список", List.of(products)));
        shoppingLists.add(new ShoppingList(0, "Третий список", List.of(products)));
        shoppingLists.add(new ShoppingList(0, "Четвертый список", List.of(products)));

        adapter = new ShoppingListsAdapter(shoppingLists);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}