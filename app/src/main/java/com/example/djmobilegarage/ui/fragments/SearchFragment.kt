package com.example.djmobilegarage.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.djmobilegarage.R
import com.example.djmobilegarage.modles.Product
import com.example.djmobilegarage.ui.fragments.adapter.ProductAdapter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {

    private lateinit var recyleview: RecyclerView
    private lateinit var product: Product
    val firebaseDatabaseReference = FirebaseDatabase.getInstance().reference
    var productList = mutableListOf<Product>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ProductAdapter()
        recyleview = view.recycler_view as RecyclerView
        recyleview.layoutManager = GridLayoutManager(this@SearchFragment.context ,2)

        recyleview.adapter = adapter


//
        firebaseDatabaseReference.child("products").addChildEventListener(
            object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot) {}

                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    product = dataSnapshot.getValue(Product::class.java)!!
                    productList.add(product)
                    Log.d("tag", "$productList")

                    adapter.products = productList as ArrayList<Product>
                    recyleview.adapter = adapter


                }


            }

        )

//
    }
}
