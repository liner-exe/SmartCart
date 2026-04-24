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


public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private ShoppingListsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);

//        Product[] products = {
//                new Product(0, "Say", true, 67),
//                new Product(0, "Wallahi", true, 15),
//                new Product(0, "Bro", false, 10),
//                new Product(0, "Say", false, 88),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//        };
//
//        Product[] products2 = {
//                new Product(0, "Say", true, 67),
//                new Product(0, "Wallahi", true, 15),
//                new Product(0, "Bro", false, 10),
//                new Product(0, "Say", false, 88),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", true, 1),
//        };
//
//        Product[] products3 = {
//                new Product(0, "Say", true, 67),
//                new Product(0, "Wallahi", true, 15),
//                new Product(0, "Bro", false, 10),
//                new Product(0, "Say", false, 88),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", true, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", false, 1),
//                new Product(0, "Wallahi", true, 1),
//        };

//        List<ShoppingList> shoppingLists = new ArrayList<>();
//        shoppingLists.add(new ShoppingList(0, "Первый список", List.of(products)));
//        shoppingLists.add(new ShoppingList(0, "Второй список", List.of(products2)));
//        shoppingLists.add(new ShoppingList(0, "Третий список", List.of(products3)));
//        shoppingLists.add(new ShoppingList(0, "Четвертый список", List.of(products)));

        NavController navController = Navigation.findNavController(requireActivity(), R.id.main_nav_host);

//        adapter = new ShoppingListsAdapter(shoppingLists);

//        adapter.setOnItemClickListener((shoppingList, position) -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("listName", shoppingList.getName());
//            navController.navigate(R.id.action_mainFragment_to_fragmentList, bundle);
//        });

//        RecyclerView recyclerView = binding.recyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}