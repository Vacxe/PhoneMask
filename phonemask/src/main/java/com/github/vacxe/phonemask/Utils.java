package com.github.vacxe.phonemask;

import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by konstantinaksenov on 21.05.17.
 */

public class Utils {
    public static final Pattern notDigitRegex = Pattern.compile("[^\\d]+");

    public static void validatePresetup(TextView editText, String mask){
        //Check setOnFocusChangeListener
        if(editText.getOnFocusChangeListener() != null){
            throw new RuntimeException("If you wanna to use OnFocusChangeListener add him through withOnFocusChangeListener() method");
        }
        //Check mask
        if(mask == null){
            throw new RuntimeException("Mask can't be null");
        }

    }
}
