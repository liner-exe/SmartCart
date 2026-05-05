package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.Category;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.CategoriesManagementAdapter;
import com.liner_exe.smartcart.databinding.FragmentCategoriesManagementBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesManagementFragment extends Fragment {
    private FragmentCategoriesManagementBinding binding;
    private CategoriesManagementAdapter adapter;
    private ShoppingViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories_management,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);

        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        RecyclerView recyclerView = binding.rvCategories;
        adapter = new CategoriesManagementAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setItems(List.of(
                new Category("Vegetables", "🍅"),
                new Category("Fruits", "🍎")
        ));

        adapter.setOnItemClickListener((category, position) -> {
            NavController navController = Navigation.findNavController(requireActivity(),
                    R.id.main_nav_host);
            navController.navigate(R.id.action_categoryManagementFragment_to_categoryEditFragment);
        });
    }
}