package com.studiowavi.youtubesolution

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.studiowavi.youtubesolution.dataclass.Idea
import com.studiowavi.youtubesolution.dataclass.IdeaAnalysis
import com.studiowavi.youtubesolution.dataclass.IsRequested
import com.google.firebase.auth.FirebaseAuth
//import com.kiyohara.komoran.Komoran

fun hideViews (vararg views: View){
    views.forEach { it.visibility = View.GONE}
}
fun showViews (vararg views: View){
    views.forEach { it.visibility = View.VISIBLE}
}

fun getUserId(): String? {
    return FirebaseAuth.getInstance().currentUser?.uid
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
        views == 0 -> "데이터 없음"
        views < 1000 -> "${views}뷰"
        views < 10_000 -> "${(views / 1000.0).formatToString()}천뷰"
        views < 100_000 -> "${(views / 10_000.0).formatToString()}만뷰"
        views < 1_000_000 -> "${views / 10_000}만뷰"
        views < 10_000_000 -> "${adjustMillions(views / 100_000)}0만뷰"
        views < 100_000_000 -> "${adjustMillions(views / 1_000_000)}00만뷰"
        else -> "${adjustMillions(views / 100_000_000)}억뷰"
    }
}

inline fun <reified T : Enum<T>> getEnumFromString(value: String?, default: T): T {
    return try {
        if (value != null) enumValueOf<T>(value) else default
    } catch (e: IllegalArgumentException) {
        default
    }
}

fun checkIsRequested(view: TextView, idea: Idea, ideaAnalysis: IdeaAnalysis){
    if (idea.isRequested == IsRequested.COMPLETED) {
        view.text = formatViews(ideaAnalysis!!.refViewCount)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.black))
    } else if (idea.isRequested == IsRequested.REQUESTED){
        view.text = "분석 진행 중"
        view.setTextColor(ContextCompat.getColor(view.context, R.color.gray_a))
    } else if (idea.isRequested == IsRequested.NOT_REQUESTED) {
        view.text = "분석 진행 전"
        view.setTextColor(ContextCompat.getColor(view.context, R.color.gray_b))
    } else {
        view.text = "${idea.isRequested}"
        view.setTextColor(ContextCompat.getColor(view.context, R.color.gray_b))
    }
}

//fun extractKeywords(text: String): List<String> {
//    val komoran = Komoran("models-full") // 모델 로드
//    val result = komoran.analyze(text)
//
//    val keywords = result
//        .filter { it.pos == "NNG" || it.pos == "NNP" } // 명사(NNG, NNP) 필터링
//        .map { it.lexeme } // 단어 추출
//
//    return keywords
//}