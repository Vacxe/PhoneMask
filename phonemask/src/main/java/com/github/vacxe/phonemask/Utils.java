package com.github.vacxe.phonemask;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by konstantinaksenov on 21.05.17.
 */

public class Utils {
    public static final Pattern notDigitRegex = Pattern.compile("[^\\d]+");

    public static void validatePresetup(TextView editText, String mask){

        //Check TextWatchers
        try {
            Field listeners = TextView.class.getDeclaredField("mListeners");
            listeners.setAccessible(true);
            List list = (List) listeners.get(editText);
            if(list!= null && !list.isEmpty()){
                throw new RuntimeException("list of TextWatchers is not empty");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

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
