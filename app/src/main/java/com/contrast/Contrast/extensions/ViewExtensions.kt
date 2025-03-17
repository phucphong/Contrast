package com.contrast.Contrast.extensions


import android.graphics.PorterDuff
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import com.contrast.Contrast.managers.GUIManager
import com.contrast.Contrast.managers.ThemeManager


fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun ImageView.setTintColor(color: Int) {
    setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun View.setNormalForeground() {
    background = ThemeManager.foreground?.drawable
    postInvalidate()
}

fun View.setNormalBackground() {
    background = ThemeManager.background?.drawable
    postInvalidate()
}

fun View.setNormalState() {
    background = ThemeManager.normal?.drawable
    postInvalidate()
}

fun View.setSelectedState() {
    background = ThemeManager.selected?.drawable
    postInvalidate()
}

fun View.setHighlightState() {
    background = ThemeManager.highlight?.drawable
    postInvalidate()
}

fun View.setDisabledState() {
    background = ThemeManager.disabled?.drawable
    postInvalidate()
}

fun Button.setStateTheme() {
    val stateColor = if (isFocused) ThemeManager.text?.firstColor else ThemeManager.text?.lastColor
    stateColor?.let { color ->
        setTextColor(color)
    }
    setOnFocusChangeListener { _, isFocused ->
        if (isSelected) setSelectedState() else if (isFocused) setHighlightState() else setNormalState()
        val focusedColor = if (isFocused) ThemeManager.text?.firstColor else ThemeManager.text?.lastColor
        focusedColor?.let { color ->
            setTextColor(color)
        }
    }
    setNormalState()
    GUIManager.applyCornerRadiusForButton(this)
}

fun ImageButton.setStateTheme() {
    setOnFocusChangeListener { _, isFocused ->
        if (isSelected) setSelectedState() else if (isFocused) setHighlightState() else setNormalState()
    }
    setNormalState()
    GUIManager.applyCornerRadiusForButton(this)
}

fun Spinner.setStateTheme() {
    ThemeManager.text?.lastColor?.let { color ->

    }
}