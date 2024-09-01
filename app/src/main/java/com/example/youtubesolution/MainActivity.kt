package com.example.youtubesolution

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.youtubesolution.databinding.ActivityMainBinding
import com.example.youtubesolution.dataclass.IdeaViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: IdeaViewModel by viewModels<IdeaViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setFragment(IdeaHomeFragment())

    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_main_activity, frag)
            setReorderingAllowed(true)
        }
    }
}