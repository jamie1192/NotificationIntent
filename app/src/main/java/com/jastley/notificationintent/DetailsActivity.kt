package com.jastley.notificationintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        val receivedCode = intent.getIntExtra("notificationType", -1)

        updateText(receivedCode)
    }

    private fun updateText(code: Int) {
        code_result.text = getString(R.string.request_code_title, code)
    }

}
