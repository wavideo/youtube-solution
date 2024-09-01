package com.example.youtubesolution.dataclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IdeaViewModel : ViewModel() {
    private val idealist = mutableListOf<Idea>()
    private val _ideas = MutableLiveData<List<Idea>>(mutableListOf())
    val ideas: LiveData<List<Idea>> = _ideas

    private val ideaAnalysislist = mutableListOf<IdeaAnalysis>()
    private val _ideaAnalysises = MutableLiveData<List<IdeaAnalysis>>(mutableListOf())
    val ideaAnalysises: LiveData<List<IdeaAnalysis>> = _ideaAnalysises

    fun addIdea(idea: Idea) {
        idealist.add(0, idea)
        _ideas.value = idealist
    }

    fun addIdeaAnalysis(ideaAnalysis: IdeaAnalysis) {
        ideaAnalysislist.add(0, ideaAnalysis)
        _ideaAnalysises.value = ideaAnalysislist
    }

    fun updateIdea(updatedIdea: Idea?) {
        updatedIdea?.let { idea ->
            val index = idealist.indexOfFirst { it.id == updatedIdea.id }
            if (index != -1) {
                idealist[index] = updatedIdea
                _ideas.value = idealist
            }
        }
    }

    fun updateIdeaAnalysis(updatedIdeaAnalysis: IdeaAnalysis?) {
        updatedIdeaAnalysis?.let { idea ->
            val index = ideaAnalysislist.indexOfFirst { it.ideaId == updatedIdeaAnalysis.ideaId }
            if (index != -1) {
                ideaAnalysislist[index] = updatedIdeaAnalysis
                _ideaAnalysises.value = ideaAnalysislist
            }
        }
    }

    fun removeIdeaAndAnalysis(id: String?) {
        idealist.removeAll { it.id == id }
        _ideas.value = idealist

        ideaAnalysislist.removeAll { it.ideaId == id }
        _ideaAnalysises.value = ideaAnalysislist
    }

    fun getIdeaById(id: String?): Idea {
        val index = idealist.indexOfFirst { it.id == id }
        return idealist[index]
    }

    fun getIdeaAnalysisById(id: String?): IdeaAnalysis {
        val index = ideaAnalysislist.indexOfFirst { it.ideaId == id }
        return ideaAnalysislist[index]
    }


}