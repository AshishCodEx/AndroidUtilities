package com.codExalters.androidutilities


import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by codexalters on 10/3/18.
 */
object LayoutUtils {

    fun disableUI(activity: Activity) {
        disableViewGroup(activity.window.decorView as ViewGroup)
    }

    fun disableUI(fragment: Fragment) {
        disableViewGroup(fragment.view as ViewGroup)
    }

    fun enableUI(activity: Activity) {
        enableViewGroup(activity.window.decorView as ViewGroup)
    }

    fun enableUI(fragment: Fragment) {
        enableViewGroup(fragment.view as ViewGroup)
    }

    fun disableViewGroup(layout: ViewGroup) {
        layout.isEnabled = false
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                disableViewGroup(child)
            } else {
                child.isEnabled = false
            }
        }
    }

    fun enableViewGroup(layout: ViewGroup) {
        layout.isEnabled = true
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                enableViewGroup(child)
            } else {
                child.isEnabled = true
            }
        }
    }

    fun hideViewGroup(layout: ViewGroup) {
        layout.visibility = GONE
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                hideViewGroup(child)
            } else {
                child.visibility = GONE
            }
        }
    }

    fun showViewGroup(layout: ViewGroup) {
        layout.visibility = VISIBLE
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                hideViewGroup(child)
            } else {
                child.visibility = VISIBLE
            }
        }
    }


    fun changeViewSelectionFromViewGroup(layout: ViewGroup, childViewToBeSelected: View) {
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                changeViewSelectionFromViewGroup(child, childViewToBeSelected)
            } else {
                child.isSelected = child.id == childViewToBeSelected.id
            }
        }
    }



}