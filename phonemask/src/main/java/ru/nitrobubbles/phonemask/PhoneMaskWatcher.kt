package ru.nitrobubbles.phonemask

import android.text.Editable
import android.text.TextWatcher
import java.lang.StringBuilder
import java.util.*
import java.util.regex.Pattern

/**
 * Created by konstantinaksenov on 17.05.17.
 */
internal class PhoneMaskWatcher(val mask: String, val region: String, val valueListener: ValueListener?) : TextWatcher {
    var result: String = ""
    var state: EditState = EditState.IDLE
    val notDigitRegex = Regex("[^\\d]+")

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val value = s.toString()
        if (state == EditState.RELEASE) {
            state = EditState.IDLE
            return
        }

        if (state == EditState.IDLE) {
            var rawString = value.replace(region, "")
            rawString = notDigitRegex.replace(rawString, "")
            var charsQueue: Queue<Char> = LinkedList<Char>(rawString.asSequence().toList())

            var rawMaskBuilder = StringBuilder(region + mask)
            val pattern = Pattern.compile("#")
            val matcher = pattern.matcher(region + mask)
            while (matcher.find()) {
                var start = matcher.start()
                if (!charsQueue.isEmpty()) {
                    rawMaskBuilder.replace(start, start + 1, charsQueue.poll().toString())
                    if (charsQueue.isEmpty()) {
                        result = rawMaskBuilder.substring(0, start + 1)
                        break
                    }
                } else {
                    result = rawMaskBuilder.substring(0, start)
                    break
                }
            }

            val phone = notDigitRegex.replace(rawMaskBuilder.toString(), "")
            valueListener?.onPhoneChanged("+$phone")
            state = EditState.EDIT
        }

        when (state) {
            EditState.EDIT -> {
                state = EditState.CLEAR
                s?.clear()
            }
            EditState.CLEAR -> {
                state = EditState.RELEASE
                s?.append(result, 0, result.length)
            }
            else -> throw Throwable("illegal state")
        }
    }

    enum class EditState {
        IDLE, EDIT, CLEAR, RELEASE
    }
}