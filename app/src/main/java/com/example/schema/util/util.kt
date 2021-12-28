package com.example.schema.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun NavController.navigateSafely(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun formatAfterDot(string: String, places: Int): String {
    var position = 0
    var positionOfDot = 0
    var isDot = false
    var eException = false
    for (char in string) {
        if (char == '.') {
            isDot = true
            positionOfDot = position
        }
        if (char == 'e') {
            eException = true
        }
        position += 1
    }
    var resultString = ""
    if (isDot && positionOfDot + places < string.length && !eException) {
        for (i in 0..positionOfDot + places) {
            resultString += string[i]
        }
        return resultString
    } else {
        return string
    }
}