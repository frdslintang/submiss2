package com.dicoding.submiss2.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailAdapter(
    frgment: FragmentActivity,
    private val fragment:MutableList<Fragment>
): FragmentStateAdapter(frgment) {
    override fun getItemCount(): Int =fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]
}