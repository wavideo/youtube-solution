package com.example.youtubesolution.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class IsRequested {
    NOT_REQUESTED,
    REQUESTED,
    COMPLETED,
    ERROR
}
@Parcelize
data class Idea(
    val ideaId: String = "",
    val userId : String = "",
    val description: String = "",
    val keyword: String = "",
    val isShorts : Boolean = false,
    val isRequested: IsRequested = IsRequested.COMPLETED
): Parcelable

@Parcelize
data class IdeaAnalysis(
    val ideaId: String = "",
    val userId: String = "",
    val refViewCount: Int = -1,
    val refTitle: String = "",
    val howtoClick: String = "",
    val howtoWatching: String = ""
): Parcelable