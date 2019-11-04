package com.example.djmobilegarage.modles

data class Product(

    val picture: String,
    val brand: String,
    val model_name: String,
    val inStock: Boolean,
    val mrp: String,
    val price_after_discount:String ,
    val discount: String
){
    constructor():this(picture="" ,brand = "" , model_name = "" , inStock = true , mrp = "",price_after_discount = "",discount = "" )
}
