package com.example.djmobilegarage.modles

data class Product(
    val brand: String,
    val name: String,
    val inStock: Boolean,
    val real_price: String,
    val off: String,
    val price_after_off:String ,
    val picture: String
)