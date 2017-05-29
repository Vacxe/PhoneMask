package com.github.vacxe.phonemask;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.vacxe.phonemask.Utils.notDigitRegex;

/**
 * Created by konstantinaksenov on 17.05.17.
 */
class PhoneMaskWatcher implements TextWatcher {
    private final String mask;
    private final String region;
    private final ValueListener valueListener;
    private final Pattern maskPattern;
    private final EditText editText;

    PhoneMaskWatcher(String mask, String region, ValueListener valueListener, String maskSymbol, EditText editText) {
        this.mask = mask;
        this.region = region;
        this.valueListener = valueListener;
        this.maskPattern = Pattern.compile(maskSymbol);
        this.editText = editText;
    }

    private String result = "";
    private EditState state = EditState.IDLE;
    private Integer cursorPosition;
    private Integer cursorShifting;

    private String phoneString = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (state == EditState.IDLE) {
            cursorShifting = s.length();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        final String value = s.toString();
        if (state == EditState.RELEASE) {
            cursorShifting = s.length() - cursorShifting;
            cursorPosition = cursorPosition + cursorShifting;
            if (cursorShifting > 0) {
                if (cursorPosition < value.length()) {
                    cursorPosition--;
                    if (!Character.isDigit(value.charAt(cursorPosition))) {
                        cursorPosition++;
                    }
                }
            } else {
                cursorPosition++;
            }

            editText.setSelection(Math.max(0, Math.min(cursorPosition, value.length())));

            state = EditState.IDLE;
            return;
        }

        if (state == EditState.IDLE) {
            if (s.toString().isEmpty()) {
                phoneString = "";
                cursorPosition = 0;
                return;
            }

            cursorPosition = editText.getSelectionStart();

            String rawString = value.replace(region, "");
            rawString = notDigitRegex.matcher(rawString).replaceAll("");
            Queue<Character> charsQueue = new LinkedList<>();
            for (char c : rawString.toCharArray()) {
                charsQueue.add(c);
            }

            StringBuilder rawMaskBuilder = new StringBuilder(region + mask);
            Matcher matcher = maskPattern.matcher(region + mask);
            while (matcher.find()) {
                int start = matcher.start();
                if (!charsQueue.isEmpty()) {
                    rawMaskBuilder.replace(start, start + 1, charsQueue.poll().toString());
                    if (charsQueue.isEmpty()) {
                        result = rawMaskBuilder.substring(0, start + 1);
                        break;
                    }
                } else {
                    result = rawMaskBuilder.substring(0, start);
                    break;
                }
            }

            String phone = notDigitRegex.matcher(rawMaskBuilder.toString()).replaceAll("");
            this.phoneString = "+" + phone;

            if (valueListener != null) {
                valueListener.onPhoneChanged(this.phoneString);
            }
            state = EditState.EDIT;
        }

        switch (state) {
            case EDIT:
                state = EditState.CLEAR;
                s.clear();
                break;
            case CLEAR:
                state = EditState.RELEASE;
                s.append(result, 0, result.length());
                break;
            default:
                break;
        }
    }

    public String getPhone() {
        return phoneString;
    }

    enum EditState {
        IDLE, EDIT, CLEAR, RELEASE
    }

}
