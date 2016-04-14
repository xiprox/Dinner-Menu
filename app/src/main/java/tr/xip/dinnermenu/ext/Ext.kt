package tr.xip.dinnermenu.ext

import android.content.Context
import android.widget.ViewFlipper

fun String.isNotEmpty(): Boolean {
    return trim().length != 0
}

fun ViewFlipper.safeSetDisplayedChild(child: Int) {
    if (displayedChild != child) displayedChild = child
}

fun Number.toDp(context: Context): Int {
    return (this.toFloat() / context.resources.displayMetrics.density).toInt()
}

fun Number.toPx(context: Context): Int {
    return (this.toFloat() * context.resources.displayMetrics.density).toInt()
}