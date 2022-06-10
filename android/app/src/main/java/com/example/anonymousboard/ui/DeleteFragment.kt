package com.example.anonymousboard.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.anonymousboard.PostsViewModel
import com.example.anonymousboard.api.JsServer
import com.example.anonymousboard.databinding.DeleteFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteFragment : Fragment() {
    private var _binding: DeleteFragmentBinding? = null
    private val binding get() = _binding!!
    private val model: PostsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DeleteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteCompleteBtn.setOnClickListener {
            Log.i("asdas", "${binding.editUpPw.text}")
            deleteCheck(binding.editUpPw.text)
        }

    }

    private fun deleteCheck(pw: Editable) {
        // 서버에 데이터 요청해서 받기
        val request = model.postId.value?.let { JsServer.postsApi.deletePost(id = model.postId, password = pw.toString()) }
        if (request != null) {
            request.enqueue(object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("http error",t.toString())
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            Log.d("onRESPONSE", "Response: ${response.body()}")
                            // model.setPost(response.body()!!)

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