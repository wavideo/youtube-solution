package com.example.youtubesolution.dataclass

import android.os.Parcelable
import com.example.youtubesolution.formatViews
import kotlinx.parcelize.Parcelize

enum class IsRequested {
    NOT_REQUESTED,
    REQUESTED,
    COMPLETED
}
@Parcelize
data class Idea(
    val id: String = "",
    val description: String = "",
    val keyword: String = "",
    val isShorts : Boolean = false,
    val isRequested: IsRequested = IsRequested.NOT_REQUESTED
): Parcelable

@Parcelize
data class IdeaAnalysis(
    val ideaId: String = "",
    val refViewCount: Int = -1,
    val refTitle: String = "",
    val howtoClick: String = "",
    val howtoWatching: String = ""
): Parcelable