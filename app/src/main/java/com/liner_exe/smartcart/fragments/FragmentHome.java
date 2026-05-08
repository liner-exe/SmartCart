package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ShoppingListsAdapter;
import com.liner_exe.smartcart.databinding.FragmentHomeBinding;
import com.liner_exe.smartcart.dialogs.ShoppingListDialogFragment;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private ShoppingListsAdapter adapter;
    private ShoppingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);

        RecyclerView recyclerView = binding.recyclerViewLists;
        adapter = new ShoppingListsAdapter(
                new ShoppingListsAdapter.OnShoppingListActionListener() {
                    @Override
                    public void onRename(ShoppingList shoppingList) {
                        ShoppingListDialogFragment.newInstance(shoppingList.getName(), newName -> {
                            ShoppingList updatedShoppingList = new ShoppingList(
                                    shoppingList.getId(), newName,
                                    shoppingList.getTotalItems(), shoppingList.getBoughtItems()
                            );
                            viewModel.updateList(updatedShoppingList);
                        }).show(getChildFragmentManager(), "RenameListDialog");
                    }

                    @Override
                    public void onDelete(ShoppingList shoppingList) {
                        viewModel.deleteListById(shoppingList.getId());
                    }
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((shoppingList, position) -> {
            NavDirections action = MainFragmentDirections
                    .actionMainFragmentToFragmentList()
                    .setListId(shoppingList.getId())
                    .setListName(shoppingList.getName());

            NavHostFragment.findNavController(requireParentFragment().requireParentFragment()).navigate(action);
        });

        viewModel.shoppingLists.observe(getViewLifecycleOwner(), newLists -> {
            if (newLists != null) {
                adapter.setItems(newLists);
            }
        });

        bindDialog();
    }

    private void bindDialog() {
        binding.fabAddList.setOnClickListener(v -> {
            ShoppingListDialogFragment.newInstance(null, name -> {
                viewModel.addList(new ShoppingList(name));
            }).show(getChildFragmentManager(), "AddListDialog");
        });
    }
}