package com.github.vacxe.phonemask;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static com.github.vacxe.phonemask.Utils.notDigitRegex;

/**
 * Created by konstantinaksenov on 17.05.17.
 */

public class PhoneMaskManager {
    private String mask = null;
    private String region = "";
    private ValueListener valueListener = null;
    private View.OnFocusChangeListener onFocusChangeListener = null;
    private String maskSymbol = "#";

    public PhoneMaskManager withMask(String mask) {
        this.mask = mask;
        return this;
    }

    public PhoneMaskManager withRegion(String region) {
        this.region = region;
        return this;
    }

    public PhoneMaskManager withValueListener(ValueListener valueListener) {
        this.valueListener = valueListener;
        return this;
    }

    public PhoneMaskManager withOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
        return this;
    }

    public PhoneMaskManager withMaskSymbol(String maskSymbol) {
        this.maskSymbol = maskSymbol;
        return this;
    }


    public void bindTo(final EditText editText) {
        if (mask == null) {
            Log.e("PhoneMaskManager", "Mask can't be null");
        } else {
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (editText.getText().toString().isEmpty()) {
                            editText.setText(region);
                        }
                    } else {
                        String input = notDigitRegex.matcher(editText.getText().toString()).replaceAll("");
                        String region = notDigitRegex.matcher(PhoneMaskManager.this.region).replaceAll("");
                        if (input.equals(region)) {
                            editText.setText("");
                        }
                    }

                    if (onFocusChangeListener != null) {
                        onFocusChangeListener.onFocusChange(v, hasFocus);
                    }
                }
            });
            editText.addTextChangedListener(new PhoneMaskWatcher(mask, region, valueListener, maskSymbol));
        }

    }
}