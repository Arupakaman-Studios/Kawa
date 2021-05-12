package com.arupakaman.kawa.utils.motions

import android.graphics.Color
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.arupakaman.kawa.R
import com.arupakaman.kawa.utils.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale


/**
 * Step 1: add transition for list item, to keep it unique append item id with transition name
 * for ex:
 *
 * In String.xml:
 * <string name="transition_koan_item">koan_item_%1$s</string>
 *
 * and in item.xml:
 * android:transitionName="@{@string/transition_koan_item(koan.id)}"
 *
 * ---
 *
 * Step 2: add transition for detail'screen root view
 *
 * for ex:
 *
 * In String.xml:
 * <string name="transition_koan_detail">koan_detail</string>
 *
 * and in detail.xml layout file:
 * android:transitionName="@string/transition_koan_detail"
 *
 * ---
 *
 * Step 3: In listing fragment's recycler view add property
 *  android:transitionGroup="true"
 *
 */

/**
 * Step 4: call this method to navigate from listing screen to detail screen
 */
fun Fragment.navigateToContainerTransform(
    itemView: View,
    @StringRes detailTransitionId: Int,
    directions: NavDirections
) {
    exitTransition = MaterialElevationScale(false).apply {
        duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
    }
    reenterTransition = MaterialElevationScale(true).apply {
        duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
    }

    val detailTransition = getString(detailTransitionId)
    val extras = FragmentNavigatorExtras(itemView to detailTransition)
    //val direction = KoansListingFragmentDirections.actionKoansListingFragmentToKoanDetailFragment(koan)

    findNavController().navigate(directions, extras)
}

/**
 * Step 5: call this method(from listing fragment) to postpone enter transition, this will be used when user gets back to this screen from detail screen
 * call this from listing fragment's onViewCreated method
 */
fun Fragment.postponeEnterTrans() {
    postponeEnterTransition()
    view?.doOnPreDraw {
        startPostponedEnterTransition()
    }
}

/**
 * Step 6: Finally in detail fragment's onCreate call this method to set sharedElementEnterTransition to apply MaterialContainerTransform animation
 */
fun Fragment.setupSharedElementTransitionToContainerTransform(){
    sharedElementEnterTransition = MaterialContainerTransform().apply {
        drawingViewId = R.id.navHostFragment
        duration = context?.resources?.getInteger(R.integer.reply_motion_duration_large)?.toLong()?:300L
        scrimColor = Color.TRANSPARENT
        setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
    }
}
