package com.example.youtubesolution

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.youtubesolution.IdeaHomeFragment.IdeaHomeFragment
import com.example.youtubesolution.RequestState.*
import com.example.youtubesolution.databinding.FragmentIdeaDetailBinding
import com.example.youtubesolution.databinding.FragmentIdeaDetailStubRequestBinding
import com.example.youtubesolution.databinding.FragmentIdeaDetailStubResultBinding
import com.example.youtubesolution.dataclass.Idea
import com.example.youtubesolution.dataclass.IdeaAnalysis
import com.example.youtubesolution.dataclass.SharedViewModel
import com.example.youtubesolution.dataclass.IsRequested

enum class RequestState {
    NOT_REQUESTED,
    REQUESTED,
    RESULT
}

private const val ARG_ID = "id"

class IdeaDetailFragment : Fragment() {
    private val viewModel by activityViewModels<SharedViewModel>()
    private val binding by lazy { FragmentIdeaDetailBinding.inflate(layoutInflater) }
    private val bindingRequest by lazy { FragmentIdeaDetailStubRequestBinding.bind(binding.viewStub.inflate()) }
    private val bindingResult by lazy { FragmentIdeaDetailStubResultBinding.bind(binding.viewStub.inflate()) }

    private val requestState by lazy { checkRequestState() }
    private val refViewCountFormat by lazy { formatViews(ideaAnalysis?.refViewCount.toString().toInt()) }

    private var id: String? = null
    private var idea: Idea? = null
    private var ideaAnalysis: IdeaAnalysis? = null

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

        initView()
        setupClickListeners()
        setViewStubByRequestState(requestState)
        setupClickListnersByRequestState(requestState)


    }

    private fun setupClickListeners() {
        binding.ivIconArrowBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        binding.tvButtonDelete.setOnClickListener{
            viewModel.removeIdeaAndAnalysis(id)
            parentFragmentManager.commit {
                replace(R.id.fragment_container_main_activity, IdeaHomeFragment())
                setReorderingAllowed(true)
                parentFragmentManager.popBackStack()
            }
        }

    }

    private fun initView() {
        idea = viewModel.getIdeaById(id)
        ideaAnalysis = viewModel.getIdeaAnalysisById(id)

        binding.apply {
            tvItemIdeaDescription.text = idea?.description
            tvItemIdeaKeyword.text = idea?.keyword
            tvItemIdeaView.text = refViewCountFormat
        }
    }

    private fun checkRequestState(): RequestState {
        if (idea?.isRequested == IsRequested.NOT_REQUESTED) {
            return NOT_REQUESTED
        } else if (idea?.isRequested == IsRequested.REQUESTED) {
            return REQUESTED
        } else {
            return RESULT
        }
    }

    private fun setViewStubByRequestState(requestState : RequestState) {

        when (requestState) {
            NOT_REQUESTED -> {
                setViewStubAsNotRequested()
            }

            REQUESTED -> {
                setViewStubAsRequested()
            }

            RESULT -> {
                setViewStubAsResult()
            }
        }
    }

    private fun setViewStubAsNotRequested() {
        binding.viewStub.layoutResource = R.layout.fragment_idea_detail_stub_request

        bindingRequest.apply {
            tvStringRequest.text = "아이디어 분석을 의뢰할까요?"
            tvStringRequestSubtext.text =
                "전문가가 ${idea?.keyword} 키워드의\n조회수 이력과 소재사용법을 분석해드립니다"
            tvButtonRequest.text = "전문가 분석신청"
        }
    }

    private fun setViewStubAsRequested() {
        binding.viewStub.layoutResource = R.layout.fragment_idea_detail_stub_request

        bindingRequest.apply {
            tvStringRequest.text = "분석 진행 중 . . ."
            tvStringRequestSubtext.text =
                "전문가가 ${idea?.keyword} 키워드\n분석하고 있습니다\n\n분석이 완료되면 알림을 보내드릴게요\n(평균 1일 소요)"
            clButtonRequest.visibility = View.GONE
        }
    }

    private fun setViewStubAsResult() {
        binding.viewStub.layoutResource = R.layout.fragment_idea_detail_stub_result

        val stringWhatKeyword = "${idea?.keyword} 키워드 분석 결과"
        val stringWhatViewcount = "규모가 작은 채널에서\n${refViewCountFormat} 이력이 검증되었습니다"
        val StringHowtoUseKeyword = "전문가가 분석한 ${idea?.keyword} 소재 사용법입니다"

        bindingResult.apply {
            tvStringWhatKeyword.text = stringWhatKeyword
            tvStringWhatViewcount.text = stringWhatViewcount

            tvVideoTitle.text = ideaAnalysis?.refTitle

            tvStringHowtoUseKeyword.text = StringHowtoUseKeyword

            tvResultHowtoClick.text = ideaAnalysis?.howtoClick
            tvResultHowtoWatching.text = ideaAnalysis?.howtoWatching
        }
    }

    private fun setupClickListnersByRequestState(requestState : RequestState) {
        setupClickListenerEditIdeaAnalysis()

        when (requestState) {
            NOT_REQUESTED -> {
                bindingRequest.clButtonRequest.setOnClickListener {
                    updateIsRequired(IsRequested.REQUESTED)
                }
            }

            REQUESTED -> {
            }

            RESULT -> {
            }
        }
    }

    private fun setupClickListenerEditIdeaAnalysis() {
        binding.tvButtonEditIdeaAnalysis.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragment_container_main_activity, EditIdeaAnalysisFragment.newInstance(id.toString()))
                setReorderingAllowed(true)
                addToBackStack("")
            }
        }
    }

    private fun updateIsRequired(isRequested: IsRequested) {
        val updatedIdea = idea?.copy(
            isRequested = isRequested
        )
        viewModel.updateIdea(updatedIdea)

        parentFragmentManager.commit {
            replace(R.id.fragment_container_main_activity, IdeaDetailFragment.newInstance(id.toString()))
            setReorderingAllowed(true)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            IdeaDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}