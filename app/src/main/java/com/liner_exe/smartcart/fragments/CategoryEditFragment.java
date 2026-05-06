package com.liner_exe.smartcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;

import com.liner_exe.domain.models.Category;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.FragmentCategoryEditBinding;
import com.liner_exe.smartcart.utils.Utils;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryEditFragment extends Fragment {
    private FragmentCategoryEditBinding binding;
    private ShoppingViewModel viewModel;
    private Category category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_edit,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);

        if (getArguments() != null) {
            CategoryEditFragmentArgs args = CategoryEditFragmentArgs.fromBundle(getArguments());
            category = args.getCategory();
        }

        boolean isEdit = category != null;

        if (isEdit) {
            binding.appToolbar.setTitle(R.string.category_edit);
            binding.ilCategoryEditName.setPrefixText(category.getEmoji() + " ");
            binding.etCategoryEditName.setText(category.getName());
            binding.etCategoryEditName.setSelection(category.getName().length());
        }

        binding.appToolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.fabDoneCategory.setOnClickListener(v -> {
            saveCategory();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        setupFabAnimation();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (nextAnim == 0) return super.onCreateAnimation(transit, enter, nextAnim);

        Animation anim = AnimationUtils.loadAnimation(getContext(), nextAnim);
        if (enter) {
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.getRoot().post(() -> {
                        if (isAdded()) startEmojiPickerSmoothAppearance();
                    });
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return anim;
    }

    private void setupFabAnimation() {
        WindowInsetsAnimationCompat.Callback callback = new WindowInsetsAnimationCompat.Callback(
                WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_STOP
        ) {
            @Override
            public @NonNull WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat insets, @NonNull List<WindowInsetsAnimationCompat> runningAnimations) {
                int keyboardHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;

                float margin = keyboardHeight - Utils.convertDpToPx(16);
                binding.fabDoneCategory.setTranslationY(-margin);

                return insets;
            }
        };

        ViewCompat.setWindowInsetsAnimationCallback(binding.fabDoneCategory, callback);
    }

    private void setupEmojiPicker() {
        binding.emojiPickerCategoryEdit.setOnEmojiPickedListener(item -> {
            String selectedEmoji = item.getEmoji();


            binding.ilCategoryEditName.setPrefixText(selectedEmoji + " ");
        });
    }

    private void startEmojiPickerSmoothAppearance() {
        binding.emojiPickerCategoryEdit.setAlpha(0f);
        binding.emojiPickerCategoryEdit.setTranslationY(Utils.convertDpToPx(32));
        binding.emojiPickerCategoryEdit.setVisibility(View.VISIBLE);

        binding.emojiPickerCategoryEdit.post(() -> {
            binding.emojiPickerCategoryEdit.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(400)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .withLayer()
                    .withEndAction(this::setupEmojiPicker)
                    .start();
        });
    }

    private void saveCategory() {
        String name = binding.etCategoryEditName.getText().toString().trim();
        String emoji = binding.ilCategoryEditName.getPrefixText().toString().trim();

        if (name.isEmpty()) {
            binding.ilCategoryEditName.setError("Введите название");
            return;
        }

        if (category == null) {
            viewModel.addCategory(new Category(name, emoji));
        } else {
            Category updatedCategory = new Category(category.getId(), name, emoji);
            viewModel.updateCategory(updatedCategory);
        }
    }
}