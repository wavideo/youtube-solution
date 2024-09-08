package com.example.youtubesolution.dataclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubesolution.getEnumFromString
import com.example.youtubesolution.getUserId
import com.google.firebase.firestore.FirebaseFirestore

private val ideaCollection = "testIdeas"
private val analysisCollection = "testAnalyses"
class SharedViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val idealist = mutableListOf<Idea>()
    private val _ideas = MutableLiveData<List<Idea>>(mutableListOf())
    val ideas: LiveData<List<Idea>> = _ideas

    private val ideaAnalysislist = mutableListOf<IdeaAnalysis>()
    private val _ideaAnalyses = MutableLiveData<List<IdeaAnalysis>>(mutableListOf())
    val ideaAnalyses: LiveData<List<IdeaAnalysis>> = _ideaAnalyses

    init {
        fetchIdeasFromFirestore()
    }

    fun addIdea(idea: Idea) {
        idealist.add(0, idea)
        _ideas.value = idealist

        db.collection(ideaCollection)
            .document(idea.ideaId)
            .set(idea)
    }

    fun addIdeaAnalysis(ideaAnalysis: IdeaAnalysis) {
        ideaAnalysislist.add(0, ideaAnalysis)
        _ideaAnalyses.value = ideaAnalysislist

        db.collection(analysisCollection)
            .document(ideaAnalysis.ideaId)
            .set(ideaAnalysis)
    }

    fun updateIdea(updatedIdea: Idea?) {
        updatedIdea?.let { updatedIdea ->
            val index = idealist.indexOfFirst { it.ideaId == updatedIdea.ideaId }
            if (index != -1) {
                idealist[index] = updatedIdea
                _ideas.value = idealist

                db.collection(ideaCollection)
                    .document(updatedIdea.ideaId)
                    .set(updatedIdea)
            }
        }
    }

    fun updateIdeaAnalysis(updatedIdeaAnalysis: IdeaAnalysis?) {
        updatedIdeaAnalysis?.let { updatedIdeaAnalysis ->
            val index = ideaAnalysislist.indexOfFirst { it.ideaId == updatedIdeaAnalysis.ideaId }
            if (index != -1) {
                ideaAnalysislist[index] = updatedIdeaAnalysis
                _ideaAnalyses.value = ideaAnalysislist

                db.collection(analysisCollection)
                    .document(updatedIdeaAnalysis.ideaId)
                    .set(updatedIdeaAnalysis)
            }
        }
    }

    fun getIdeaById(id: String?): Idea {
        val index = idealist.indexOfFirst { it.ideaId == id }
        return idealist[index]
    }


    fun getIdeaAnalysisById(id: String?): IdeaAnalysis {
        val index = ideaAnalysislist.indexOfFirst { it.ideaId == id }
        return ideaAnalysislist[index]
    }


    fun removeIdeaAndAnalysis(id: String?) {
        idealist.removeAll { it.ideaId == id }
        _ideas.value = idealist

        ideaAnalysislist.removeAll { it.ideaId == id }
        _ideaAnalyses.value = ideaAnalysislist

        db.collection(ideaCollection)
            .document(id.toString())
            .delete()

        db.collection(analysisCollection)
            .document(id.toString())
            .delete()
    }

    private fun fetchIdeasFromFirestore() {
        val userId = getUserId()

        db.collection(ideaCollection)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                idealist.clear()
                for (document in result) {
                    val idea = document.toObject(Idea::class.java)
                    val updatedIdea = idea.copy(isRequested = getEnumFromString(document.getString("requested").toString(), IsRequested.ERROR))
                    idealist.add(updatedIdea)
                }
                _ideas.value = idealist
            }
        db.collection(analysisCollection)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                ideaAnalysislist.clear()
                for (document in result) {
                    val ideaAnalysis = document.toObject(IdeaAnalysis::class.java)
                    ideaAnalysislist.add(ideaAnalysis)
                }
                _ideaAnalyses.value = ideaAnalysislist
            }
    }

}