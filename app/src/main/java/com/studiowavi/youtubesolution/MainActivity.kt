package com.studiowavi.youtubesolution

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.studiowavi.youtubesolution.IdeaHomeFragment.IdeaHomeFragment
import com.studiowavi.youtubesolution.SiginInFragment.SignInFragment
import com.studiowavi.youtubesolution.databinding.ActivityMainBinding
import com.studiowavi.youtubesolution.dataclass.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val viewModel: SharedViewModel by viewModels<SharedViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().currentUser != null) {
            setFragment(IdeaHomeFragment())
        } else {
            setFragment(SignInFragment())
        }

    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_main_activity, frag)
            setReorderingAllowed(true)
        }
    }
}

