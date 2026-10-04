package com.industri.smartpos

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNominal = findViewById<EditText>(R.id.etNominal)
        val etQty = findViewById<EditText>(R.id.etQty)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        btnHitung.setOnClickListener {
            // LATIHAN BREAKPOINT: Klik garis margin kiri pada baris di bawah untuk memasang titik merah (Breakpoint)
            val nominalStr = etNominal.text.toString()
            val qtyStr = etQty.text.toString()
            Timber.d("Menghitung transaksi kasir: Nominal=%s, Qty=%s", nominalStr, qtyStr)

            // Menggunakan idiom fungsional runCatching untuk menangkal crash input kosong
            val kalkulasiResult = runCatching {
                val nominal = nominalStr.toDouble()
                val qty = qtyStr.toInt()
                if (qty <= 0) throw IllegalArgumentException("Kuantitas barang minimal 1")
                nominal * qty
            }

            kalkulasiResult.onSuccess { total ->
                Timber.i("Kalkulasi sukses: Total bayar Rp %,.2f", total)
                tvHasil.text = String.format(Locale.getDefault(), "Total Transaksi: Rp %,.2f", total)
            }.onFailure { error ->
                Timber.e(error, "Terjadi kesalahan kalkulasi input kasir")
                tvHasil.text = "Error Input: ${error.message ?: "Input tidak valid"}"
            }
        }
    }
}
