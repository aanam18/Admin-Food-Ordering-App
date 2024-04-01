package com.example.adminfoodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderingapp.adapter.DeliveryAdapter
import com.example.adminfoodorderingapp.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener { finish() }


    val customerName = arrayListOf("John Doe", "Jane Smith", "Mike Johnson")
    val moneyStatus = arrayListOf("Received", "Not Received", "Pending")
    val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.deliveryR.adapter = adapter
        binding.deliveryR.layoutManager = LinearLayoutManager(this)
}
}