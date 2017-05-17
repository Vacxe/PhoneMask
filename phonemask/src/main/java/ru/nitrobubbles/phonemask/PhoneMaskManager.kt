package ru.nitrobubbles.phonemask

import android.widget.EditText

/**
 * Created by konstantinaksenov on 17.05.17.
 */

class PhoneMaskManager {
    var mask: String? = null
    var region: String = ""
    var valueListener: ValueListener? = null

    /**
     * @field mask - default mask symbol is #
     */
    fun withMask(mask: String): PhoneMaskManager {
        this.mask = mask
        return this
    }

    fun withRegion(region: String): PhoneMaskManager {
        this.region = region
        return this
    }

    fun withValueListener(valueListener: ValueListener): PhoneMaskManager {
        this.valueListener = valueListener
        return this
    }

    fun bindTo(editText: EditText) {
        if (mask.isNullOrEmpty()) {
            throw PhoneMaskException("Mask can't be null or empty")
        } else {
            editText.setOnFocusChangeListener { _, hasFocus ->
                run {
                    if (hasFocus) {
                        if (editText.text.toString().isEmpty()) {
                            editText.setText(region)
                        }
                    }
                }
            }
            editText.addTextChangedListener(PhoneMaskWatcher(mask!!, region, valueListener))
        }

    }
}