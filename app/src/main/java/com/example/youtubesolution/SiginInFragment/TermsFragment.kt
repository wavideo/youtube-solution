package com.example.youtubesolution.SiginInFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.youtubesolution.R
import com.example.youtubesolution.databinding.FragmentTermsBinding

class TermsFragment : Fragment() {
    private val binding by lazy { FragmentTermsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivIconArrowBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        binding.tvButtonAgree.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.fragment_container_main_activity, SignInFragment.newInstance(true))
            }
        }

    }
}