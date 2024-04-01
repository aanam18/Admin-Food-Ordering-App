package com.example.adminfoodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderingapp.adapter.DeliveryAdapter
import com.example.adminfoodorderingapp.adapter.PendingOrderAdapter
import com.example.adminfoodorderingapp.databinding.ActivityPendingOrderBinding
import com.example.adminfoodorderingapp.databinding.PendingOrderItemBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener { finish() }


        val orderedCustomerName = arrayListOf("John Doe", "Jane Smith", "Mike Johnson")
        val orderedQuantity = arrayListOf("8", "9", "4")
        val orderedFoodImage = arrayListOf(R.drawable.menu1,R.drawable.menu1,R.drawable.menu1)
        val adapter = PendingOrderAdapter(orderedCustomerName, orderedQuantity, orderedFoodImage, this)

        binding.pendinOrderRecyclerView.adapter = adapter
        binding.pendinOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}