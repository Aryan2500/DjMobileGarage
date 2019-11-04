package com.example.djmobilegarage.ui.fragments.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.djmobilegarage.R
import com.example.djmobilegarage.modles.Product
import com.example.djmobilegarage.ui.activity.HomeActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_layout.view.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var products = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        Log.d("size" , "${products.size}")
        return products.size
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.itemView.mrp_value.text = currentProduct.mrp
        holder.itemView.discount_value.text = currentProduct.discount
        holder.itemView.new_price_value.text = currentProduct.price_after_discount
        Log.d("imageLink" , "{$currentProduct.picture}")
        Picasso.get().load(currentProduct.picture).resize(100, 100)
            .into(holder.itemView.product_image)

    }


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
