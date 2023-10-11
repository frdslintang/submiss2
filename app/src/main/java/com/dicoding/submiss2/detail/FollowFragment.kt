package com.dicoding.submiss2.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submiss2.data.response.ResponseUser
import com.dicoding.submiss2.databinding.FragmentFollowsBinding
import com.dicoding.submiss2.main.UserAdapter
import com.dicoding.submiss2.utils.Result



class FollowFragment : Fragment() {

    private var binding: FragmentFollowsBinding?=null
    private val adapter = UserAdapter{}

    private val viewModel by activityViewModels<DetailViewModel>()

    var type = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollows?.apply {
            layoutManager= LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }


        when(type){
            Followers->{
                viewModel.resultFollowersUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            Following->{
                viewModel.resultFollowingUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(state: Result){
        when(state){
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<ResponseUser.Item>)
            }
            is Result.Error ->  {
                Toast.makeText(requireActivity(), state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Result.Loading->{
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }
    companion object {
        const val Followers = 1
        const val Following = 12
        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }
    }
}