package com.studiowavi.youtubesolution.dataclass

import android.os.Parcelable
import com.studiowavi.youtubesolution.formatViews
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class User(
    val userId : String = "",
    val handle: String = "",
    val paymentStartDate : Long = Date().time,
    val paymentEndDate : Long = Date().time
): Parcelable