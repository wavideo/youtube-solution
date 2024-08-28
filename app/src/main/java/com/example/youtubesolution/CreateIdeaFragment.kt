package com.example.youtubesolution

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.youtubesolution.databinding.FragmentCreateIdeaBinding
import com.example.youtubesolution.dataclass.Idea
import com.example.youtubesolution.dataclass.IdeaViewModel

class CreateIdeaFragment : Fragment() {
    private val viewModel by activityViewModels<IdeaViewModel>()
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

    lateinit var description: String
    lateinit var keyword: String
    private var isShorts: Boolean = false

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
            handleDescription()
        }
        setupTextWatcher(binding.etAnswerDescription, binding.tvLengthDescription, 100)

        binding.tvButtonKeyword.setOnClickListener {
            handleKeyword()
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

        val newIdea = Idea(description, keyword, isShorts, true)
        viewModel.addIdea(newIdea)
        parentFragmentManager.popBackStack()
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