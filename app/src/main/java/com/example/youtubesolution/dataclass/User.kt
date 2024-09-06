package com.example.youtubesolution.dataclass

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

data class User(
    val userId: String = "",
    val handle: String = "",
    val paymentStartDate: Int = 946684800,
    val paymentEndDate: Int = 946684800
)