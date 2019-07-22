package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

fun Activity.hideKeyboard() {
    val currentFocus = this.currentFocus

    currentFocus?.let { f ->
        val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethod?.let { it.hideSoftInputFromWindow(f.windowToken, 0)}
    }
}

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleRect = Rect()

    this.findViewById<View>(android.R.id.content).getWindowVisibleDisplayFrame(visibleRect)
    val diffHeight = findViewById<View>(android.R.id.content).height - visibleRect.height()

    val errorRound = this.dpToPx(50F).roundToInt()

    return diffHeight > errorRound
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}
