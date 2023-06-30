package com.example.radius.helpers

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd

fun View?.makeViewAppearFromBottom(parent: View, topView: View){
    this.makeVisible()
    val animator = ObjectAnimator.ofFloat(this,"y",(parent.y+parent.bottom),(topView.y+topView.bottom))
    animator.duration = 200
    animator.interpolator = AccelerateDecelerateInterpolator()
    animator.start()
}

fun View?.hideViewToBottom(parent: View, topView: View, doOnEnd:(()->Unit)? = null){
    val animator = ObjectAnimator.ofFloat(this,"y",(topView.y+topView.bottom),(parent.y+parent.bottom))
    animator.duration = 200
    animator.interpolator = AccelerateInterpolator()
    animator.doOnEnd {
        doOnEnd?.invoke()
        this.makeGone()
    }
    animator.start()
}


fun View?.makeVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.makeGone() {
    this?.visibility = View.GONE
}