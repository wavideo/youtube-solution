package com.studiowavi.youtubesolution

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.studiowavi.youtubesolution.databinding.FragmentEditIdeaAnalysisBinding
import com.studiowavi.youtubesolution.dataclass.SharedViewModel
import com.studiowavi.youtubesolution.dataclass.IsRequested

private const val ARG_ID = "id"

class EditIdeaAnalysisFragment : Fragment() {
    private val viewModel by activityViewModels<SharedViewModel>()
    private val binding by lazy { FragmentEditIdeaAnalysisBinding.inflate(layoutInflater) }

    private var id: String? = null
    private val idea by lazy { viewModel.getIdeaById(id) }
    private val ideaAnalysis by lazy { viewModel.getIdeaAnalysisById(id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = arguments?.getString(ARG_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIdeaView()
        initAnalysisView()
        setupClickListners()

    }

    private fun initIdeaView() {
        binding.apply {
            tvItemIdeaDescription.text = idea?.description
            tvItemIdeaKeyword.text = idea?.keyword
            checkIsRequested(tvItemIdeaView, idea!!, ideaAnalysis!!)
            tvQeustionFindVideo.text = "${idea?.keyword} 소재의 조회수 이력 찾기"
        }
    }

    private fun initAnalysisView() {
        val refViewCount =
            if (ideaAnalysis.refViewCount == 0){
                ""
            } else {
                ideaAnalysis.refViewCount.toString()
            }

        binding.apply {
            etYoutubeTitle.setText(ideaAnalysis.refTitle)
            etYoutubeViewcount.setText(refViewCount)
            etHowtoClick.setText(ideaAnalysis.howtoClick)
            etHowtoWatching.setText(ideaAnalysis.howtoWatching)
        }
    }

    private fun setupClickListners() {
        fun edit() {
            val updatedIdeaAnalysis = ideaAnalysis.copy(
                refTitle = binding.etYoutubeTitle.text.toString(),
                refViewCount = binding.etYoutubeViewcount.text.toString().toInt(),
                howtoClick = binding.etHowtoClick.text.toString(),
                howtoWatching = binding.etHowtoWatching.text.toString()
            )
            viewModel.updateIdeaAnalysis(updatedIdeaAnalysis)
            viewModel.updateIdea(idea.copy(isRequested = IsRequested.COMPLETED))

            parentFragmentManager.commit {
                replace(
                    R.id.fragment_container_main_activity,
                    IdeaDetailFragment.newInstance(id.toString())
                )
                setReorderingAllowed(true)
                parentFragmentManager.popBackStack()
            }
        }

        binding.clButtonComplete.setOnClickListener {
                if (binding.etYoutubeTitle.text.length < 0) {
                    Toast.makeText(requireActivity(), "영상 제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if (binding.etYoutubeViewcount.text.length < 0) {
                    Toast.makeText(requireActivity(), "조회수를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if (binding.etYoutubeViewcount.text.toString().toInt() < 1) {
                    Toast.makeText(requireActivity(), "조회수를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    edit()
                }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            EditIdeaAnalysisFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}