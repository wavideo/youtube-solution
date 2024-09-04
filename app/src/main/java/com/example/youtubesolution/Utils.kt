package com.example.youtubesolution

import android.view.View
import com.google.firebase.auth.FirebaseAuth

fun hideViews (vararg views: View){
    views.forEach { it.visibility = View.GONE}
}
fun showViews (vararg views: View){
    views.forEach { it.visibility = View.VISIBLE}
}

fun getUserId() {
    FirebaseAuth.getInstance().currentUser?.uid.toString()
}

fun formatViews(views: Int): String {
    fun Double.formatToString(): String {
        this
        return if (this % 1.0 == 0.0) {
            this.toInt().toString()
        } else {
            String.format("%.1f", this)
        }
    }

    fun adjustMillions(value: Int): Int {
        return if (value % 10 == 9) value - 1 else value
    }

    return when {
        views == 0 -> "분석 이전"
        views < 1000 -> "${views}뷰"
        views < 10_000 -> "${(views / 1000.0).formatToString()}천뷰"
        views < 100_000 -> "${(views / 10_000.0).formatToString()}만뷰"
        views < 1_000_000 -> "${views / 10_000}만뷰"
        views < 10_000_000 -> "${adjustMillions(views / 100_000)}0만뷰"
        views < 100_000_000 -> "${adjustMillions(views / 1_000_000)}00만뷰"
        else -> "${adjustMillions(views / 100_000_000)}억뷰"
    }
}