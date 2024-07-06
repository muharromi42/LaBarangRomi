package com.muharromi.labarangromi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarangAdapter(private val barangList: List<Barang>) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kodeBarang: TextView = itemView.findViewById(R.id.textViewKodeBarang)
        val namaBarang: TextView = itemView.findViewById(R.id.textViewNamaBarang)
        val jumlah: TextView = itemView.findViewById(R.id.textViewJumlah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]
        holder.kodeBarang.text = barang.kode_barang
        holder.namaBarang.text = barang.nama_barang
        holder.jumlah.text = barang.jumlah.toString()
    }

    override fun getItemCount(): Int {
        return barangList.size
    }
}
