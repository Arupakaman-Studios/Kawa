package com.arupakaman.kawa.ui.koans

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.databinding.ActivityKoansBinding
import com.arupakaman.kawa.utils.*
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KoansActivity : AppCompatActivity() {

    companion object{
        const val STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val TIME_INTERVAL = 2000 // # milliseconds, desired time passed between two back presses.
    }

    private val koansActivityViewModel:KoansActivitySharedViewModel by viewModels() /* by lazy { ViewModelProvider(this).get(
        KoansActivitySharedViewModel::class.java
    ) }*/

    private val mBinding by lazy { ActivityKoansBinding.inflate(layoutInflater) }

    @Inject
    lateinit var permissionChecker:PermissionChecker // by lazy { PermissionChecker(this) }

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHostFragment) }

    private var mBackPressed: Long = 0

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
        setNotificationSwitchListener()
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

    private fun openDrawer(){
        mBinding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun ActivityKoansBinding.setupNavigationItemClicks(){


        navigationView.setThemeChangeListener()

        navigationView.menu.run {


            findItem(R.id.itemShare).setOnMenuItemClickListener {

                /*showInAppReviewDialog()
                return@setOnMenuItemClickListener true*/

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

        koansActivityViewModel.liveShareData.observeAsEvent(this, {
            val (bitmap, shareText) = it
            shareData(bitmap, shareText)
        })

        koansActivityViewModel.liveOpenDrawer.observeAsEvent(this,{
            openDrawer()
        })
    }

    fun setNotificationSwitchListener(){
        try {
            val itemNotificationSwitch =mBinding.navigationView.menu.findItem(R.id.itemNotificationSwitch)
            val switchNotification= itemNotificationSwitch.actionView.findViewById<SwitchCompat>(R.id.switchNotification)
            switchNotification.isChecked=MyAppPref.isNotificationEnabled
            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                MyAppPref.isNotificationEnabled=isChecked
            }
        }catch (e:Exception){
            Log.e("notificationSwitch",e.toString())
        }
    }

    private fun NavController.setDestinationChangeListener(){

        fun NavigationView.resetMenu(@MenuRes menuRes: Int, selectedItemId: Int){
            menu.clear()
            inflateMenu(menuRes)

            menu.findItem(selectedItemId)?.let {
                //it.isCheckable=true
                it.isChecked=true
            }
        }


        fun setNavigationIcon(/*drawerMode:Int,*/ @DrawableRes navigationIcon: Int){
            //mBinding.drawerLayout.setDrawerLockMode(drawerMode)
            mBinding.partialToolbar.toolbar.setNavigationIcon(navigationIcon)
        }

        addOnDestinationChangedListener { _, destination, arguments ->

            val isOnKoanDetailViaList = arguments?.containsKey("index") == true
            if (destination.id==R.id.itemNow && isOnKoanDetailViaList)
            {
                setNavigationIcon(/*DrawerLayout.LOCK_MODE_LOCKED_CLOSED,*/ R.drawable.ic_semi_arc_left)

                mBinding.partialToolbar.toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            else{
                setNavigationIcon(/*DrawerLayout.LOCK_MODE_UNLOCKED,*/ R.drawable.ic_circle_boundary)

                mBinding.setHomeNavigationToOpenDrawer()
            }


            mBinding.partialToolbar.appBar.setExpanded(true, true)

            when (destination.id) {
                R.id.itemNow, R.id.itemImageInfo -> {

                    if (destination.id == R.id.itemNow) {
                        if (isOnKoanDetailViaList) {
                            mBinding.navigationView.resetMenu(
                                R.menu.menu_navigation3,
                                destination.id
                            )
                        } else if (mBinding.navigationView.menu.size() != 9) {

                            mBinding.navigationView.resetMenu(
                                R.menu.menu_navigation,
                                destination.id
                            )
                            Log.d("destinationChangedList", "adding all items")

                        }
                        mBinding.setupNavigationItemClicks()
                        setNotificationSwitchListener()
                    }

                    // ignore the click itemImageInfo

                }
                else->{
                    if (mBinding.navigationView.menu.size()!=5 || mBinding.navigationView.menu.findItem(
                            R.id.itemAllKoans
                        )==null)
                    {
                        mBinding.navigationView.resetMenu(R.menu.menu_navigation2, destination.id)

                        mBinding.navigationView.setThemeChangeListener()
                        Log.d("destinationChangedList", "adding menu 2")
                    }
                }
            }

            Log.d(
                "destinationChangedList",
                "checkedItem: " + mBinding.navigationView.checkedItem?.title?.toString()
            )
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
            openDrawer()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
            saveImageViaLegacyStorage()
        }
        else{
            Toast.makeText(
                applicationContext,
                getString(R.string.request_for_storage_permission),
                Toast.LENGTH_LONG
            ).show()
            openAppSettings()
        }
    }

    override fun onBackPressed() {
        if (navHostFragment?.childFragmentManager?.backStackEntryCount==0)
        {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                super.onBackPressed()
                return
            }
            else { Toast.makeText(applicationContext, getString(R.string.back_again_to_exit), Toast.LENGTH_SHORT).show(); }

            mBackPressed = System.currentTimeMillis()
        }
        else{
            super.onBackPressed()
        }
    }

}