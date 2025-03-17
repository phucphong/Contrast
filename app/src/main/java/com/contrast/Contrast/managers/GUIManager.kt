package com.contrast.Contrast.managers



import android.annotation.SuppressLint
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import java.text.SimpleDateFormat
import java.util.Date

object GUIManager {
    const val PANEL_CORNER_RADIUS_ITEM = 20.0f
    const val BUTTON_CORNER_RADIUS_ITEM = 10.0f
    const val VIEW_MORE_CORNER_RADIUS_ITEM = 50.0f

    @SuppressLint("SimpleDateFormat")
    fun getHour(): Int {
        return SimpleDateFormat("HH").format(Date()).toInt()
    }

    fun isNight(): Boolean {
        val hour = getHour()
        return hour >= 18 || hour < 6
    }

    private val panelOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0, 0, view!!.width, view.height,
                PANEL_CORNER_RADIUS_ITEM
            )
        }
    }

    private val buttonOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0, 0, view!!.width, view.height,
                BUTTON_CORNER_RADIUS_ITEM
            )
        }
    }

    private val viewMoreOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0, 0, view!!.width, view.height,
                VIEW_MORE_CORNER_RADIUS_ITEM
            )
        }
    }

    fun applyCornerRadiusForPanel(view: View) {
        view.outlineProvider = panelOutlineProvider
        view.clipToOutline = true
    }

    fun applyCornerRadiusForButton(view: View) {
        view.outlineProvider = buttonOutlineProvider
        view.clipToOutline = true
    }

    fun applyCornerRadiusForViewMore(view: View) {
        view.outlineProvider = viewMoreOutlineProvider
        view.clipToOutline = true
    }
}