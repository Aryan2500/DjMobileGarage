package com.example.djmobilegarage.ui.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.djmobilegarage.R
import com.example.djmobilegarage.modles.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_create_product.*
import kotlinx.android.synthetic.main.fragment_create_product.view.*
import java.io.IOException
import java.io.InputStream
import java.util.*


class CreateProductFragment : Fragment() {

    private var newPrice :Double = 0.0
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val storageReference = FirebaseStorage.getInstance().reference
    private var imageUri: Uri? = null
    private val dialoagMenuOption = arrayOf("Camera", "Gallery")
    private val GALLERY_REQUEST = 2
    var mrp: Double = 0.0
    var discount: Int = 0
    var inStock = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.camera_button.setOnClickListener {

            //Creating Dialoage OPtions

            val alertDialog = AlertDialog.Builder(this@CreateProductFragment.context!!)
            alertDialog.setTitle("Choose or Capture Photo")
            alertDialog.setItems(dialoagMenuOption) { _, which ->
                when (which) {
                    0 -> {
                        // dispatchTakePictureIntent()
                    }
                    1 -> {
                        openGallery()
                    }
                }
            }
            alertDialog.show()
        }

        save_product_btn.setOnClickListener {
            if (!inStock_check_box.isChecked) {
                inStock = false
            }
            if (brand_field.text.isEmpty() || mrp_input_field.text.isEmpty() || name_model_field.text.isEmpty()) {
                //Validating DAta
                Snackbar.make(it, "Fill Required Fields", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                //Saving DAta
                Snackbar.make(it, "DAta SAved", Snackbar.LENGTH_SHORT).show()

                if (imageUri == null) {
                    //If  imageURi is Empty saveProduct() from uploadPost() will never be called thats why we are calling saveProdcut() directly from here
                    val defaultImgUrl =
                        "https://firebasestorage.googleapis.com/v0/b/djmobilegarage.appspot.com/o/images%2Fnot_available.jpg?alt=media&token=9bde9c7f-909c-442d-aef8-38e7260028ce"
                    saveProductDetails(
                        defaultImgUrl,
                        brand_field.text.toString(),
                        name_model_field.text.toString(),
                        mrp_input_field.text.toString(),
                        discount.toString(),
                        newPrice.toString(),
                        inStock
                    )
                    Log.d("hello", "Hello")
                } else {
                    uploadPost(imageUri!!)
                    Log.d("bye", "Bye")
                }


            }
        }

        view.off_input_field.isEnabled = false
        view.mrp_input_field.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                @SuppressLint("SetTextI18n")
                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (s!!.isNotEmpty() && s.toString().toDouble() > 1) {
                        mrp = s.toString().toDouble()

                        if (view.off_input_field.text.isNotEmpty() && view.off_input_field.text.toString().toInt() > 0) {
                            discount = view.off_input_field.text.toString().toInt()
                            newPrice = mrp - (mrp * discount / 100)
                            view.discount_price_text_view.text = "Price after discount $newPrice"
                        } else {
                            view.discount_price_text_view.text = "Price without discount $mrp"
                            view.off_input_field.isEnabled = true
                        }
//                        Toast.makeText(this@CreateProductFragment.context, s, Toast.LENGTH_LONG)
//                            .show()
                    } else {
                        view.off_input_field.setText("")
                        view.off_input_field.isEnabled = false
                        view.discount_price_text_view.text = "Price after discount $discount"
                    }

                }

            })

        view.off_input_field.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (s!!.isNotEmpty()) {
                        discount = s.toString().toInt()
                        newPrice = mrp - (mrp * discount / 100)
                        view.discount_price_text_view.text = "Price after Discount $newPrice"
                    } else {
                        discount = 0
                        view.discount_price_text_view.text = "Price after Discount $discount"
                    }

                }

            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                val selectedImageUri: Uri? = data?.data
                imageUri = selectedImageUri!!
                Log.d("path", "$selectedImageUri")

                val imgStream: InputStream =
                    activity?.contentResolver?.openInputStream(selectedImageUri)!!

                camera_button.setImageBitmap(BitmapFactory.decodeStream(imgStream))


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    private fun openGallery() {
        Intent().also {
            it.type = "image/*"
            it.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(it, "Select Photo"), GALLERY_REQUEST)
        }
    }

    private fun uploadPost(uri: Uri) {
        val imgId = UUID.randomUUID().toString()
        storageReference.child("images/$imgId").putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                storageReference.child("images/$imgId")
                    .downloadUrl.addOnCompleteListener { imgURL ->

                    saveProductDetails(
                        imgURL.result.toString(),
                        brand_field.text.toString(),
                        name_model_field.text.toString(),
                        mrp_input_field.text.toString(),
                        discount.toString(),
                        newPrice.toString(),
                        inStock


                    )

                }

            }
        }


    }


    private fun saveProductDetails(
        imgUrl: String,
        brand: String,
        model: String,
        mrp: String,
        discount: String,
        price_after_discount: String,
        inStock: Boolean
    ) {
        databaseReference.child("products").push()
            .setValue(Product(imgUrl, brand, model, inStock, mrp, price_after_discount, discount))
    }

}
