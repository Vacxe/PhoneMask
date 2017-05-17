package com.github.vacxe.phonemask;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by konstantinaksenov on 17.05.17.
 */

public class PhoneMaskManager {
    private String mask = null;
    private String region = "";
    private ValueListener valueListener = null;

    public PhoneMaskManager withMask(String mask)  {
        this.mask = mask;
        return this;
    }

    public PhoneMaskManager withRegion(String region)  {
        this.region = region;
        return this;
    }

    public PhoneMaskManager withValueListener(ValueListener valueListener)  {
        this.valueListener = valueListener;
        return this;
    }

    public void bindTo(final EditText editText) {
        if (mask == null) {
            Log.e("PhoneMaskManager", "Mask can't be null");
        } else {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (editText.getText().toString().isEmpty()) {
                            editText.setText(region);
                        }
                    }
                }
            });
            editText.addTextChangedListener(new PhoneMaskWatcher(mask, region, valueListener));
        }

    }
}