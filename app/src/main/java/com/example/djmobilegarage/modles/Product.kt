package com.example.djmobilegarage.modles

data class Product(

    val picture: String,
    val brand: String,
    val name_model: String,
    val inStock: Boolean,
    val mrp: String,
    val price_after_discount:String ,
    val discount: String
)