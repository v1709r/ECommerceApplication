package com.example.ecommerceapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RvAdapter (
    private val list : MutableList<Product>,
    private val con : Context
) : RecyclerView.Adapter<RvAdapter.CustomViewHolder>() {
    class CustomViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val pImg : ImageView = view.findViewById(R.id.rviv)
        val pName : TextView = view.findViewById(R.id.rvPName)
        val pPrice : TextView = view.findViewById(R.id.rvPPrice)
        val pDesc : TextView = view.findViewById(R.id.rvPDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentProduct = list[position]
        holder.pName.text =  currentProduct.pName
        holder.pDesc.text =  currentProduct.pDesc
        holder.pPrice.text =  currentProduct.pPrice
        Glide.with(con)
            .load(currentProduct.pImg)
            .into(holder.pImg)
    }
}