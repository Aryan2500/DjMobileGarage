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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.djmobilegarage.R
import kotlinx.android.synthetic.main.fragment_create_product.*
import kotlinx.android.synthetic.main.fragment_create_product.view.*
import java.io.IOException
import java.io.InputStream


class CreateProductFragment : Fragment() {

    private var imageUri: Uri? = null
    private val dialoagMenuOption = arrayOf("Camera", "Gallery")
    private val GALLERY_REQUEST = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        var mrp = 0.0
        var discount: Double
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

                    if (s!!.isNotEmpty()) {
                        mrp = s.toString().toDouble()
                        view.discount_price_text_view.text = "Price after Discount $mrp"
                        view.off_input_field.isEnabled = true
                        Toast.makeText(this@CreateProductFragment.context, s, Toast.LENGTH_LONG)
                            .show()
                    } else {
                        view.off_input_field.setText("0")
                        view.off_input_field.isEnabled = false
                        view.discount_price_text_view.text = "Price after Discount 0"
                    }

                }

            })

        view.off_input_field.addTextChangedListener(
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

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (s!!.isNotEmpty()) {
                        discount = s.toString().toDouble()
                        val newPrice = mrp * discount / 100
                        view.discount_price_text_view.text = "Price after Discount $newPrice"
                    } else {
                        view.discount_price_text_view.text = "Price after Discount 0"
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
                Log.d("path2", "$imgStream")

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

}
