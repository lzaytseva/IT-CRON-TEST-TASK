package com.github.lzaytseva.it_cron_test_task.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.lzaytseva.it_cron_test_task.databinding.FragmentUserDetailsBinding
import com.github.lzaytseva.it_cron_test_task.presentation.ui.util.BindingFragment

class UserDetailsFragment: BindingFragment<FragmentUserDetailsBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserDetailsBinding {
        return FragmentUserDetailsBinding.inflate(inflater, container, false)
    }
}