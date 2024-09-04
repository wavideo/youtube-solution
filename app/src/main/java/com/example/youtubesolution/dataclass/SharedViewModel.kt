package com.example.youtubesolution.dataclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubesolution.getUserId
import com.google.firebase.firestore.FirebaseFirestore

class SharedViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

//    private val _user = MutableLiveData<User>()
//    val user: LiveData<User> = _user

    private val idealist = mutableListOf<Idea>()
    private val _ideas = MutableLiveData<List<Idea>>(mutableListOf())
    val ideas: LiveData<List<Idea>> = _ideas

    private val ideaAnalysislist = mutableListOf<IdeaAnalysis>()
    private val _ideaAnalyses = MutableLiveData<List<IdeaAnalysis>>(mutableListOf())
    val ideaAnalyses: LiveData<List<IdeaAnalysis>> = _ideaAnalyses

    init {
//        loadRecentUser()
        fetchIdeasFromFirestore()
    }

//    fun addUser(newUser: User) {
//        _user.value = newUser
//
//        db.collection("usersCollection")
//            .document(newUser.userId)
//            .set(user)
//    }
//
//    fun updateUser (updatedUser: User) {
//        _user.value = updatedUser
//        db.collection("usersCollection")
//            .document(updatedUser.handle)
//            .set(user)
//    }

    fun addIdea(idea: Idea) {
        idealist.add(0, idea)
        _ideas.value = idealist

        db.collection("ideasCollection")
            .document(idea.ideaId)
            .set(idea)
    }

    fun updateIdea(updatedIdea: Idea?) {
        updatedIdea?.let { updatedIdea ->
            val index = idealist.indexOfFirst { it.ideaId == updatedIdea.ideaId }
            if (index != -1) {
                idealist[index] = updatedIdea
                _ideas.value = idealist

                db.collection("ideasCollection")
                    .document(updatedIdea.ideaId)
                    .set(updatedIdea)
            }
        }
    }


    fun getIdeaById(id: String?): Idea {
        val index = idealist.indexOfFirst { it.ideaId == id }
        return idealist[index]
    }



    fun addIdeaAnalysis(ideaAnalysis: IdeaAnalysis) {
        ideaAnalysislist.add(0, ideaAnalysis)
        _ideaAnalyses.value = ideaAnalysislist

        db.collection("ideaAnalysesCollection")
            .document(ideaAnalysis.ideaId)
            .set(ideaAnalysis)
    }

    fun updateIdeaAnalysis(updatedIdeaAnalysis: IdeaAnalysis?) {
        updatedIdeaAnalysis?.let { updatedIdeaAnalysis ->
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

    fun getIdeaAnalysisById(id: String?): IdeaAnalysis {
        val index = ideaAnalysislist.indexOfFirst { it.ideaId == id }
        return ideaAnalysislist[index]
    }


    fun removeIdeaAndAnalysis(id: String?) {
        idealist.removeAll { it.ideaId == id }
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

//    fun loadRecentUser() {
//        db.collection("usersCollection")
//            .whereEqualTo("userId", getUserId())
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val user = document.toObject(User::class.java)
//                    _user.value = user
//                }
//            }
//    }
    private fun fetchIdeasFromFirestore() {
        val userId = getUserId()

        db.collection("ideasCollection")
            .whereEqualTo("userId", userId)
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