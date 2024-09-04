package com.example.youtubesolution.dataclass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubesolution.getUserId
import com.google.firebase.firestore.FirebaseFirestore

class IdeaViewModel : ViewModel() {
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

        db.collection("ideasCollection")
            .document(idea.id)
            .set(idea)
    }

    fun addIdeaAnalysis(ideaAnalysis: IdeaAnalysis) {
        ideaAnalysislist.add(0, ideaAnalysis)
        _ideaAnalyses.value = ideaAnalysislist

        db.collection("ideaAnalysesCollection")
            .document(ideaAnalysis.ideaId)
            .set(ideaAnalysis)
    }

    fun updateIdea(updatedIdea: Idea?) {
        updatedIdea?.let { idea ->
            val index = idealist.indexOfFirst { it.id == updatedIdea.id }
            if (index != -1) {
                idealist[index] = updatedIdea
                _ideas.value = idealist

                db.collection("ideasCollection")
                    .document(updatedIdea.id)
                    .set(updatedIdea)
            }
        }
    }

    fun updateIdeaAnalysis(updatedIdeaAnalysis: IdeaAnalysis?) {
        updatedIdeaAnalysis?.let { idea ->
            val index = ideaAnalysislist.indexOfFirst { it.ideaId == updatedIdeaAnalysis.ideaId }
            if (index != -1) {
                ideaAnalysislist[index] = updatedIdeaAnalysis
                _ideaAnalyses.value = ideaAnalysislist

                db.collection("ideaAnalysesCollection")
                    .document(updatedIdeaAnalysis.ideaId)
                    .set(updatedIdeaAnalysis)
            }
        }
    }

    fun removeIdeaAndAnalysis(id: String?) {
        idealist.removeAll { it.id == id }
        _ideas.value = idealist

        ideaAnalysislist.removeAll { it.ideaId == id }
        _ideaAnalyses.value = ideaAnalysislist

        db.collection("ideasCollection")
            .document(id.toString())
            .delete()

        db.collection("ideaAnalysesCollection")
            .document(id.toString())
            .delete()
    }

    fun getIdeaById(id: String?): Idea {
        val index = idealist.indexOfFirst { it.id == id }
        return idealist[index]
    }

    fun getIdeaAnalysisById(id: String?): IdeaAnalysis {
        val index = ideaAnalysislist.indexOfFirst { it.ideaId == id }
        return ideaAnalysislist[index]
    }

//    .whereEqualTo("userId", getUserId())
    private fun fetchIdeasFromFirestore() {
        db.collection("ideasCollection")
            .get()
            .addOnSuccessListener { result ->
                idealist.clear()
                for (document in result) {
                    val idea = document.toObject(Idea::class.java)
                    idealist.add(idea)
                }
                _ideas.value = idealist
            }
        db.collection("ideaAnalysesCollection")
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

    fun loadIdeaAnalysisFromFirestore(id : String){

    }


}