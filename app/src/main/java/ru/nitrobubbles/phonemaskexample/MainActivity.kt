package ru.nitrobubbles.phonemaskexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.github.vacxe.phonemask.PhoneMaskManager
import com.github.vacxe.phonemask.ValueListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PhoneMaskManager()
                .withMask(" (###) ###-##-##")
                .withRegion("+7")
                .withValueListener(object : ValueListener {
                    override fun onPhoneChanged(phone: String) {
                        System.out.println(phone)
                    }
                })
                .bindTo((findViewById(R.id.text_edit_text) as EditText))



    }
}
