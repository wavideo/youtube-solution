package com.example.youtubesolution.SiginInFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.youtubesolution.IdeaHomeFragment.IdeaHomeFragment
import com.example.youtubesolution.R
import com.example.youtubesolution.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private const val ARG_AGREEMENT = "agreement"

class SignInFragment : Fragment() {
    private var agreement: Boolean? = null
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            agreement = it.getBoolean(ARG_AGREEMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //초기화
        setupGoogleSignIn()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTermsButton()
        setupSignInButton()
    }

    private fun setupGoogleSignIn() {
        firebaseAuth = FirebaseAuth.getInstance()

        // 구글로그인
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    private fun setupTermsButton() {
        binding.clButtonTerms.setOnClickListener {
            navigateToTermsFragment()
        }

        if (agreement == null) {
            binding.checkBox.isClickable = false
        } else if (agreement == true){
            binding.checkBox.isChecked = true
        }
    }

    private fun setupSignInButton() {
        // Set up sign-in button click listener
        binding.btnSignIn.setOnClickListener {
            if (binding.checkBox.isChecked) {
                signIn()
            } else {
                Toast.makeText(requireContext(), "서비스 이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    navigateToIdeaHomeFragment()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun navigateToIdeaHomeFragment() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container_main_activity, IdeaHomeFragment())
        }
    }

    private fun navigateToTermsFragment() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_main_activity, TermsFragment())
            addToBackStack(null)
        }
    }

    companion object {
        private const val TAG = "SignInFragment"

        @JvmStatic
        fun newInstance(agreement: Boolean) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_AGREEMENT, agreement)
                }
            }
    }
}