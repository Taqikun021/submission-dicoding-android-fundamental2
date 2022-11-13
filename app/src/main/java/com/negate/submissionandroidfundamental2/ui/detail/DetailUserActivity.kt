package com.negate.submissionandroidfundamental2.ui.detail

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.negate.submissionandroidfundamental2.databinding.ActivityDetailUserBinding
import com.negate.submissionandroidfundamental2.model.Item
import com.negate.submissionandroidfundamental2.ui.main.MainActivity.Companion.INTENT

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = when {
            SDK_INT >= TIRAMISU -> intent?.getParcelableExtra(INTENT, Item::class.java) as Item
            else -> @Suppress("DEPRECATION") intent?.getParcelableExtra<Item>(INTENT) as Item
        }

        viewModel.getUserDetail(data.login)
    }
}