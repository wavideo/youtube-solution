package com.example.youtubesolution.dataclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IdeaViewModel : ViewModel() {
    private val idealist = mutableListOf<Idea>()
    private val _ideas = MutableLiveData<List<Idea>>(mutableListOf())
    val ideas: LiveData<List<Idea>> = _ideas

    private fun initItems() {
        idealist.addAll(
            listOf(
                Idea("최신 스마트폰 신제품 리뷰 | 최신 기술 비교 분석", "신제품 리뷰", true, true),
                Idea("효과적인 근력 강화 운동 루틴 | 홈트레이닝 초보자를 위한 팁", "운동 루틴", true, true),
                Idea("효과적인 시간 관리 전략 | 자기계발로 목표 달성하기", "쉬운 요리", true, true)
            )
        )
        _ideas.value = idealist
    }

    fun addIdea(idea: Idea) {
        idealist.add(0, idea)
        _ideas.value = idealist
    }

}