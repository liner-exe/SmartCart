package com.liner_exe.smartcart.utils;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.ColorInt;

public class ThemeUtils {
    @ColorInt
    public static int getThemeColor(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }
}
