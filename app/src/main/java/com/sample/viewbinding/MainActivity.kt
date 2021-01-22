package com.sample.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.viewbinding.activity.viewBinding
import com.sample.viewbinding.databinding.ActivityMainBinding
import com.sample.viewbinding.utils.toast

/*
* created by Saksham Pruthi
* bit.ly/sakshampruthi
*/

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding  by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.textView.text = getString(R.string.view_bind)

        binding.clickMe.setOnClickListener {
            toast("View Binding Works")
        }
    }
}