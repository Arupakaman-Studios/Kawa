package com.arupakaman.kawa.ui.koans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.ActivityKoansBinding
import com.arupakaman.kawa.ui.koans.img.info.KoanImageInfoFragment
import com.arupakaman.kawa.utils.observeAsEvent
import com.arupakaman.kawa.utils.setupToolbar
import com.arupakaman.kawa.utils.showSnackBarMessage

class KoansActivity : AppCompatActivity() {

    private val koansActivityViewModel by lazy { ViewModelProvider(this).get(KoansActivityViewModel::class.java) }
    private val mBinding by lazy { ActivityKoansBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(mBinding.root)


        this.setupToolbar("",false, showNavigationIcon = true)


        val navController = findNavController(R.id.navHostFragment)

        mBinding.setupNavigationView(navController)
        mBinding.setListeners()
        mBinding.setupNavigationItemClicks()
        setObserver()
    }

    private fun ActivityKoansBinding.setupNavigationItemClicks(){
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.itemTheme->{

                }

                R.id.itemShare->{
                    KoanImageInfoFragment.currentKoanImage?.let {currentKoan->
                        koansActivityViewModel.shareKoan(currentKoan)
                    }
                }

                R.id.itemDownloadImage->{
                    KoanImageInfoFragment.currentKoanImage?.let {currentKoan->
                        koansActivityViewModel.saveFile(currentKoan.artName,currentKoan.imgResId)
                    }
                }

                R.id.itemSetAsWallpaper->{
                    KoanImageInfoFragment.currentKoanImage?.let {currentKoan->
                        koansActivityViewModel.setWallpaper(currentKoan.imgResId)
                    }
                }
            }
            return@setNavigationItemSelectedListener false
        }
    }

    private fun setObserver()
    {
        koansActivityViewModel.liveWallpaperResult.observeAsEvent(this,{isSuccess->
            if (isSuccess)
            {
                showSnackBarMessage(mBinding.root,getString(R.string.success_set_wallpaper))
            }
            else{
                showSnackBarMessage(mBinding.root,getString(R.string.failure_set_wallpaper))
            }
        })
    }



    fun NavController.destinationChangeListener(){
        addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){

            }
        })
    }

    private fun ActivityKoansBinding.setupNavigationView(navController: NavController){
                //setupActionBarWithNavController(navController,mBinding.drawerLayout)
        NavigationUI.setupWithNavController(navigationView,navController)
    }

    private fun ActivityKoansBinding.setListeners(){
        partialToolbar.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}