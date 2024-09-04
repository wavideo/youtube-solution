package com.example.youtubesolution.IdeaHomeFragment

import IdeaAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubesolution.CreateIdeaFragment
import com.example.youtubesolution.IdeaDetailFragment
import com.example.youtubesolution.R
import com.example.youtubesolution.databinding.FragmentIdeaHomeBinding
import com.example.youtubesolution.dataclass.Idea
import com.example.youtubesolution.dataclass.SharedViewModel

class IdeaHomeFragment : Fragment() {
    private val viewModel by activityViewModels<SharedViewModel>()
    private val binding by lazy { FragmentIdeaHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListners()
        Log.d ("체크","${viewModel.ideas.value?.size}")
        setupRecyclerView()
    }


    private fun setupClickListners(){
        binding.clButtonCreate.setOnClickListener {
            openCreateIdeaFragment()
        }
    }

    private fun openCreateIdeaFragment() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container_main_activity, CreateIdeaFragment())
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    private fun setupRecyclerView() {
        val adapter = IdeaAdapter(mutableListOf())
        adapter.setViewModel(viewModel)
        binding.rvIdeaList.adapter = adapter
        binding.rvIdeaList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.ideas.observe(viewLifecycleOwner) { ideas ->
            adapter.submitList(ideas)
        }

        adapter.itemClick = object : IdeaAdapter.OnItemClickListener {
            override fun onItemClick(idea: Idea) {
                val id = idea.ideaId
                parentFragmentManager.commit {
                    replace(
                        R.id.fragment_container_main_activity,
                        IdeaDetailFragment.newInstance(id)
                    )
                    setReorderingAllowed(true)
                    addToBackStack("")
                }
            }
        }
    }

}