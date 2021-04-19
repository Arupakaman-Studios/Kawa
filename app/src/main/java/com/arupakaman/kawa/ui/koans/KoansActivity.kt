package com.arupakaman.kawa.ui.koans

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.contains
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.databinding.ActivityKoansBinding
import com.arupakaman.kawa.utils.*
import com.google.android.material.navigation.NavigationView


class KoansActivity : AppCompatActivity() {

    companion object{
        const val STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    private val koansActivityViewModel by lazy { ViewModelProvider(this).get(
        KoansActivitySharedViewModel::class.java
    ) }
    private val mBinding by lazy { ActivityKoansBinding.inflate(layoutInflater) }
    private val permissionChecker by lazy { PermissionChecker(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MyAppPref.themeMode)
        setContentView(mBinding.root)


        this.setupToolbar("", false, showNavigationIcon = true)


        val navController = findNavController(R.id.navHostFragment)

        mBinding.setupNavigationView(navController)
        mBinding.setListeners()
        mBinding.setupNavigationItemClicks()
        navController.setDestinationChangeListener()
        setObserver()

    }

    private fun NavigationView.setThemeChangeListener(){

        menu.findItem(R.id.itemTheme).setOnMenuItemClickListener {
            showChangeThemeDialog(koansActivityViewModel)
            closeDrawer()
            true
        }
    }

    private fun closeDrawer() {
        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun ActivityKoansBinding.setupNavigationItemClicks(){


        navigationView.setThemeChangeListener()

        navigationView.menu.run {

            findItem(R.id.itemShare).setOnMenuItemClickListener {
                koansActivityViewModel.liveCurrentKoan.value?.let { currentKoan->
                    koansActivityViewModel.shareKoan(currentKoan)
                }
                closeDrawer()
                true
            }

            findItem(R.id.itemDownloadImage).setOnMenuItemClickListener {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    {
                        koansActivityViewModel.currentImage?.let { currentKoanImage->
                            // need to save file via scoped storage so don't need the permission
                            koansActivityViewModel.saveFileViaScopedStorage(
                                currentKoanImage.artName,
                                currentKoanImage.imgResId
                            )
                        }
                    }
                    else{
                        saveImageViaLegacyStorage()
                    }
                closeDrawer()
                true
            }

            findItem(R.id.itemSetAsWallpaper).setOnMenuItemClickListener {
                koansActivityViewModel.currentImage?.let { currentKoanImage->
                    koansActivityViewModel.setWallpaper(currentKoanImage.imgResId)
                }
                closeDrawer()
                true
            }
        }
    }

    private fun saveImageViaLegacyStorage(){
        koansActivityViewModel.currentImage?.let { currentKoanImage ->
            // need to get the permission first
            if (permissionChecker.checkPermission(STORAGE_PERMISSION)){
                // save file from here
                koansActivityViewModel.saveFileViaLegacyStorage(
                    currentKoanImage.artName,
                    currentKoanImage.imgResId
                )
            }
            else{
                // ask for permission
                permissionChecker.requestPermission(STORAGE_PERMISSION)
            }
        }
    }

    private fun setObserver()
    {
        koansActivityViewModel.liveWallpaperResult.observeAsEvent(this, { isSuccess ->
            if (isSuccess) {
                showSnackBarMessage(mBinding.root, getString(R.string.success_set_wallpaper))
            } else {
                showSnackBarMessage(mBinding.root, getString(R.string.failure_set_wallpaper))
            }
        })
    }



    private fun NavController.setDestinationChangeListener(){

        fun NavigationView.resetMenu(@MenuRes menuRes: Int, selectedItemId:Int){
            menu.clear()
            inflateMenu(menuRes)

            menu.findItem(selectedItemId)?.let {
                //it.isCheckable=true
                it.isChecked=true
            }
        }


        fun setDrawerMode(drawerMode:Int, @DrawableRes navigationIcon:Int){
            mBinding.drawerLayout.setDrawerLockMode(drawerMode)
            mBinding.partialToolbar.toolbar.setNavigationIcon(navigationIcon)
        }

        addOnDestinationChangedListener { _, destination, arguments ->

            /*if (destination.id==R.id.itemNow && arguments?.containsKey("koan") == true)
            {
                setDrawerMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, R.drawable.ic_arrow_back)

                mBinding.partialToolbar.toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            else{
                setDrawerMode(DrawerLayout.LOCK_MODE_UNLOCKED, R.drawable.ic_circle_boundary)

                mBinding.setHomeNavigationToOpenDrawer()
            }*/


            mBinding.partialToolbar.appBar.setExpanded(true,true)

            when (destination.id) {
                R.id.itemNow, R.id.itemImageInfo -> {

                    if (destination.id==R.id.itemNow)
                    {
                        if (arguments?.containsKey("koan") == true)
                        {
                            mBinding.navigationView.resetMenu(R.menu.menu_navigation3,destination.id)
                        }
                        else if (mBinding.navigationView.menu.size()!=9){

                            mBinding.navigationView.resetMenu(R.menu.menu_navigation,destination.id)
                            Log.d("destinationChangedList", "adding all items")

                        }
                        mBinding.setupNavigationItemClicks()
                    }

                    // ignore the click itemImageInfo

                }
                else->{
                    if (mBinding.navigationView.menu.size()!=5 || mBinding.navigationView.menu.findItem(R.id.itemAllKoans)==null)
                    {
                        mBinding.navigationView.resetMenu(R.menu.menu_navigation2,destination.id)

                        mBinding.navigationView.setThemeChangeListener()
                        Log.d("destinationChangedList", "adding menu 2")
                    }
                }
            }

            Log.d("destinationChangedList", "checkedItem: "+mBinding.navigationView.checkedItem?.title?.toString())
        }
    }

    private fun ActivityKoansBinding.setupNavigationView(navController: NavController){
                //setupActionBarWithNavController(navController,mBinding.drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    private fun ActivityKoansBinding.setListeners(){

        setHomeNavigationToOpenDrawer()

        mBinding.drawerLayout.onDrawerOpened {
            hideKeyboard()
        }
    }

    private fun ActivityKoansBinding.setHomeNavigationToOpenDrawer(){
        partialToolbar.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
            saveImageViaLegacyStorage()
        }
        else{
            Toast.makeText(applicationContext, getString(R.string.request_for_storage_permission), Toast.LENGTH_LONG).show()
            openAppSettings()
        }

    }

}