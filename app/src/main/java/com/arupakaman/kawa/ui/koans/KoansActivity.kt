package com.arupakaman.kawa.ui.koans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.ActivityKoansBinding
import com.arupakaman.kawa.utils.setupToolbar

class KoansActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mBinding = ActivityKoansBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        this.setupToolbar("",false, showNavigationIcon = true)


        mBinding.setupNavigationView()
        mBinding.setListeners()
    }

    private fun ActivityKoansBinding.setupNavigationView(){
        val navController = findNavController(R.id.navHostFragment)
        //setupActionBarWithNavController(navController,mBinding.drawerLayout)
        NavigationUI.setupWithNavController(navigationView,navController)
    }

    private fun ActivityKoansBinding.setListeners(){
        partialToolbar.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}