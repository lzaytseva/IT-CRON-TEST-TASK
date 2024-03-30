package com.github.lzaytseva.it_cron_test_task.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.lzaytseva.it_cron_test_task.databinding.FragmentUsersBinding
import com.github.lzaytseva.it_cron_test_task.presentation.ui.util.BindingFragment

class UsersFragment: BindingFragment<FragmentUsersBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersBinding {
        return FragmentUsersBinding.inflate(inflater, container, false)
    }
}