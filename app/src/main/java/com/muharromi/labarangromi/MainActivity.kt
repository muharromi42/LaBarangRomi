package com.muharromi.labarangromi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText



class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextKodeBarang: EditText
    private lateinit var editTextJumlahTambahan: EditText
    private lateinit var buttonIncreaseQuantity: Button
    private lateinit var barangAdapter: BarangAdapter
    private var barangList: MutableList<Barang> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewBarang)
        editTextKodeBarang = findViewById(R.id.editTextKodeBarang)
        editTextJumlahTambahan = findViewById(R.id.editTextJumlahTambahan)
        buttonIncreaseQuantity = findViewById(R.id.buttonIncreaseQuantity)

        recyclerView.layoutManager = LinearLayoutManager(this)
        barangAdapter = BarangAdapter(barangList)
        recyclerView.adapter = barangAdapter

        // Mengambil data barang
        ApiClient.apiService.getBarang().enqueue(object : Callback<ApiResponse<List<Barang>>> {
            override fun onResponse(call: Call<ApiResponse<List<Barang>>>, response: Response<ApiResponse<List<Barang>>>) {
                if (response.isSuccessful) {
                    barangList.clear()
                    response.body()?.data?.let { barangList.addAll(it) }
                    barangAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Gagal mengambil data barang", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<Barang>>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Menambah jumlah barang
        buttonIncreaseQuantity.setOnClickListener {
            val kodeBarang = editTextKodeBarang.text.toString()
            val jumlahTambahan = editTextJumlahTambahan.text.toString().toIntOrNull()

            if (kodeBarang.isEmpty() || jumlahTambahan == null || jumlahTambahan <= 0) {
                Toast.makeText(this@MainActivity, "Input tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = IncreaseQuantityRequest(jumlah_tambahan = jumlahTambahan)
            ApiClient.apiService.increaseQuantity(kodeBarang, request).enqueue(object : Callback<ApiResponse<Barang>> {
                override fun onResponse(call: Call<ApiResponse<Barang>>, response: Response<ApiResponse<Barang>>) {
                    if (response.isSuccessful) {
                        val updatedBarang = response.body()?.data
                        updatedBarang?.let { barang ->
                            val index = barangList.indexOfFirst { it.kode_barang == barang.kode_barang }
                            if (index != -1) {
                                barangList[index] = barang
                                barangAdapter.notifyItemChanged(index)
                            } else {
                                barangList.add(barang)
                                barangAdapter.notifyItemInserted(barangList.size - 1)
                            }
                        }
                        Toast.makeText(this@MainActivity, "Jumlah barang berhasil ditingkatkan", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Gagal menambah jumlah barang", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Barang>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}