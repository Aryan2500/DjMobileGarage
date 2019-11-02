package com.example.djmobilegarage.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.djmobilegarage.R
import com.example.djmobilegarage.ui.fragments.AccountFragment
import com.example.djmobilegarage.ui.fragments.CreateProductFragment
import com.example.djmobilegarage.ui.fragments.ScheduleFragment
import com.example.djmobilegarage.ui.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startFragment(SearchFragment())


            popMenu.setOnClickListener {
                showPopMenu(it)
            }



        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.search_product_menu -> startFragment(SearchFragment())
                R.id.schedule_menu -> startFragment(ScheduleFragment())
                else -> startFragment(AccountFragment())
            }
        }
    }

    private fun startFragment(frag: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, frag, "newFrag")
            .commit()
        return true
    }

    private fun showPopMenu(v: View) {
        val popup = PopupMenu(this, v)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.option_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            //Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
            startFragment(CreateProductFragment())
        }
        popup.show()

    }




}
