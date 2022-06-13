package com.example.anonymousboard.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.anonymousboard.R
import com.example.anonymousboard.api.JsServer
import com.example.anonymousboard.databinding.RegistFragmentBinding
import com.example.anonymousboard.model.RegistModel
import com.example.anonymousboard.model.ResultMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistFragment : Fragment() {
    private var _binding: RegistFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= RegistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rgBack.setOnClickListener {
            findNavController().navigate(R.id.action_registFragment_to_postsFragment)
        }
        binding.registCompleteBtn.setOnClickListener {
            Log.i("title", binding.rgPostTitle.text.toString())
            val title = binding.rgPostTitle.text.toString()
            Log.i("subj", binding.rgPostSubject.text.toString())
            val subj = binding.rgPostSubject.text.toString()
            Log.i("pw", binding.rgPostPw.text.toString())
            val password = binding.rgPostPw.text.toString()

            if(title != "" && subj != "" && password != "") {
                val rgModel: RegistModel = RegistModel(
                    title = title,
                    subject = subj,
                    boardPw = password
                )
                registPost(rgModel)
            } else Toast.makeText(this@RegistFragment.context, "빈칸이 없도록 채워주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registPost(data: RegistModel) {
        // 서버에 데이터 요청해서 받기
        val request = JsServer.postsApi.registPost(data)
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
                                Toast.makeText(this@RegistFragment.context, "게시글이 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_registFragment_to_postsFragment)
                            } else {
                                Toast.makeText(this@RegistFragment.context, "오류가 발생하였습니다 재요청해주세요.", Toast.LENGTH_SHORT).show()
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