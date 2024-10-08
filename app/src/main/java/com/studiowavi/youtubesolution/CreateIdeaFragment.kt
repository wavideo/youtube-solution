package com.studiowavi.youtubesolution

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.studiowavi.youtubesolution.databinding.FragmentCreateIdeaBinding
import com.studiowavi.youtubesolution.dataclass.Idea
import com.studiowavi.youtubesolution.dataclass.IdeaAnalysis
import com.studiowavi.youtubesolution.dataclass.SharedViewModel
import com.studiowavi.youtubesolution.dataclass.IsRequested
import java.util.UUID

class CreateIdeaFragment : Fragment() {
    private val viewModel by activityViewModels<SharedViewModel>()
    private val binding by lazy { FragmentCreateIdeaBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupClickListners()

    }

    private lateinit var id: String
    private lateinit var description: String
    private lateinit var keyword: String
    private var isShorts: Boolean = false
    private lateinit var userId : String

    private fun initViews() {
        hideViews(
            binding.clCheckDescription,
            binding.clQuestionKeyword,
            binding.clAnswerKeyword,
            binding.tvButtonKeyword,
            binding.tvCheckKeyword,
            binding.tvTitleDescription
        )
    }

    private fun setupClickListners() {
        binding.tvButtonDescription.setOnClickListener {
            if (binding.etAnswerDescription.text.length > 0) {
            handleDescription()
            } else {
                Toast.makeText(requireActivity(), "내용을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }
        setupTextWatcher(binding.etAnswerDescription, binding.tvLengthDescription, 100)

        binding.tvButtonKeyword.setOnClickListener {
            if (binding.etAnswerKeyword.text.length > 0) {
                handleKeyword()
            } else {
                Toast.makeText(requireActivity(), "내용을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }
        setupTextWatcher(binding.etAnswerKeyword, binding.tvLengthKeyword, 15)

        binding.ivIconClose.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

    private fun handleDescription() {
        description = binding.etAnswerDescription.text.toString()
        binding.tvCheckDescription.setText(description)

        hideViews(
            binding.tvStringCreateidea,
            binding.clQuestionDescription,
            binding.clAnswerDescription
        )
        showViews(
            binding.tvTitleDescription,
            binding.clCheckDescription,
            binding.clQuestionKeyword,
            binding.clAnswerKeyword,
            binding.tvButtonKeyword
        )
    }

    private fun handleKeyword() {
        keyword = binding.etAnswerKeyword.text.toString()
        isShorts = binding.cbCheckboxIsshorts.isChecked
        id = generateRandomId()
        userId = getUserId().toString()

        val newIdea = Idea(id, userId, description, keyword, isShorts, IsRequested.NOT_REQUESTED)
        viewModel.addIdea(newIdea)

        val emptyIdeaAnalysis = IdeaAnalysis(id, userId,0, "", "","")
        viewModel.addIdeaAnalysis(emptyIdeaAnalysis)

        parentFragmentManager.popBackStack()
    }

    fun generateRandomId(): String {
        return UUID.randomUUID().toString()
    }

    private fun setupTextWatcher(editText: TextView, tvLength: TextView, maxLength: Int) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateCharacterCount(editText, tvLength, maxLength)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateCharacterCount(editText: TextView, tvLength: TextView, maxLength: Int) {
        val currentLength = editText.text.length
        val text = "$currentLength/$maxLength 자"
        val spannableString = SpannableString(text)

        val startIndex = 0
        val endIndex = currentLength.toString().length

        val color = ContextCompat.getColor(requireContext(), R.color.gray_a)
        spannableString.setSpan(
            ForegroundColorSpan(color), // Orange color
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvLength.text = spannableString
    }
}