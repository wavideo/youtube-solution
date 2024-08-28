package com.example.youtubesolution

import android.view.View
fun hideViews (vararg views: View){
    views.forEach { it.visibility = View.GONE}
}
fun showViews (vararg views: View){
    views.forEach { it.visibility = View.VISIBLE}
}