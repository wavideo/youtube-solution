package com.example.youtubesolution

import android.view.View
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