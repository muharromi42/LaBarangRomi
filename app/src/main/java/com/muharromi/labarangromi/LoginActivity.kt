package com.muharromi.labarangromi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewErrorMessage = findViewById(R.id.textViewErrorMessage)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Validasi email dan password (contoh sederhana)
            if (isValidCredentials(email, password)) {
                // Lakukan proses login ke server (misalnya menggunakan Retrofit atau Volley)
                // Disini Anda akan mengirimkan permintaan login ke backend
                // dan menangani respons dari server
                performLogin(email, password)
            } else {
                textViewErrorMessage.visibility = TextView.VISIBLE
                textViewErrorMessage.text = "Email atau password tidak valid"
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun performLogin(email: String, password: String) {
        // Contoh sederhana hanya untuk simulasi
        // Biasanya Anda akan melakukan request ke server di sini
        if (email == "admin@gmail.com" && password == "admin") {
            // Login berhasil, navigasi ke halaman lain (misalnya MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Selesai LoginActivity setelah berhasil login
        } else {
            // Login gagal, tampilkan pesan kesalahan
            textViewErrorMessage.visibility = TextView.VISIBLE
            textViewErrorMessage.text = "Email atau password salah"
        }
    }
}