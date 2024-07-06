package com.muharromi.labarangromi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import android.widget.Toast
import android.content.Intent
import android.app.Activity

class ScanQRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qrcode)

        // Mulai pemindaian QR code
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Pemindaian dibatalkan", Toast.LENGTH_LONG).show()
            } else {
                // Hasil pemindaian
                val scannedData = result.contents
                val intent = Intent()
                intent.putExtra("scannedData", scannedData)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}