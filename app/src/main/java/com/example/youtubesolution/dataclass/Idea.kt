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
    val id: String,
    val description: String,
    val keyword: String,
    val isShorts : Boolean,
    val isRequested: IsRequested
): Parcelable

@Parcelize
data class IdeaAnalysis(
    val ideaId: String,
    val refViewCount: Int,
    val refTitle: String,
    val howtoClick: String,
    val howtoWatching: String
): Parcelable