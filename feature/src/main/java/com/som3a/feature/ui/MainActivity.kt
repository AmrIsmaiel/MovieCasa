package com.som3a.feature.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.som3a.base.BaseActivity
import com.som3a.feature.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    override val createLayout: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun createView(savedInstanceState: Bundle?) = Unit
}