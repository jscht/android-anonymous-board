package com.example.anonymousboard.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.anonymousboard.PostsViewModel
import com.example.anonymousboard.R
import com.example.anonymousboard.api.JsServer
import com.example.anonymousboard.databinding.PostFragmentBinding
import com.example.anonymousboard.model.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFragment : Fragment() {
    private var _binding: PostFragmentBinding? = null
    private val binding get() = _binding!!
    private val model: PostsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= PostFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var postId: LiveData<Int> = model.postId
        loadPostData(postId)

        binding.reviseBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postFragment_to_reviseFragment)
        }
        binding.deleteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postFragment_to_deleteFragment)
        }
        binding.returnBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postFragment_to_postsFragment)
        }
    }

    private fun loadPostData(postId: LiveData<Int>) {
        // 서버에 데이터 요청해서 받기
        val request = postId.value?.let { JsServer.postsApi.getPost(it) }
        if (request != null) {
            request.enqueue(object: Callback<Posts> {
                override fun onFailure(call: Call<Posts>, t: Throwable) {
                    Log.d("http error",t.toString())
                }

                override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            Log.d("onRESPONSE", "Response: ${response.body()}")
                            model.setPost(response.body()!!)
                            binding.postTitle.text = response.body()!!.title
                            binding.postCreated.text = response.body()!!.created
                            binding.postViews.text = response.body()!!.views.toString()
                            binding.postSubject.text = response.body()!!.subject
                        }
                    } else {
                        Log.d("onRESPONSE", "Response: ${response.code()}")
                    }
                }
            })
        } else Log.d("nullCheck", "request is null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}