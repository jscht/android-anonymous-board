package com.example.anonymousboard.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.anonymousboard.PostsViewModel
import com.example.anonymousboard.R
import com.example.anonymousboard.api.JsServer
import com.example.anonymousboard.databinding.DeleteFragmentBinding
import com.example.anonymousboard.model.ResultMessage
import com.google.android.material.snackbar.Snackbar
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
        binding.clearText1.setOnClickListener {
            binding.editUpPw.setText("")
        }

        binding.pwBack.setOnClickListener {
            findNavController().navigate(R.id.action_deleteFragment_to_postFragment)
        }

        binding.deleteCompleteBtn.setOnClickListener {
            Log.i("inputPassword", "${binding.editUpPw.text}")
            deleteCheck(binding.editUpPw.text)
        }

    }

    private fun deleteCheck(pw: Editable) {
        // 서버에 데이터 요청해서 받기
        val request = model.postId.value?.let { JsServer.postsApi.deletePost(id = model.postId.value.toString(), password = pw.toString()) }
        if (request != null) {
            request.enqueue(object: Callback<ResultMessage> {
                override fun onFailure(call: Call<ResultMessage>, t: Throwable) {
                    Log.d("http error",t.toString())
                }

                override fun onResponse(call: Call<ResultMessage>, response: Response<ResultMessage>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            Log.d("onRESPONSE", "Response: ${response.body()}")

                            if(response.body()!!.reqState == true) {
                                Toast.makeText(this@DeleteFragment.context, "게시글이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_deleteFragment_to_postsFragment)
                            } else {
                                Toast.makeText(this@DeleteFragment.context, "비밀번호를 재확인 해주세요.", Toast.LENGTH_SHORT).show()
                            }

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