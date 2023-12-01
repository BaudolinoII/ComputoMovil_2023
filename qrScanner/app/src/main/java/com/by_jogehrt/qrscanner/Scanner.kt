package com.by_jogehrt.qrscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.by_jogehrt.qrscanner.databinding.ActivityScannerBinding
import java.net.MalformedURLException
import java.net.URL

class Scanner : AppCompatActivity() {
    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cbvScanner.decodeContinuous { result ->
            binding.cbvScanner.pause()

            try{
                val url = URL(result.text)
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(result.text)
                startActivity(i)
                finish()
            }catch(e: MalformedURLException){
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("El código QR no es válido para la aplicación")
                    .setNeutralButton("Aceptar"){ dialog, _ ->
                        dialog.dismiss()
                        startActivity(Intent(this, MainAct::class.java))
                        finish()
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .create()
                    .show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.cbvScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.cbvScanner.pause()
    }

}