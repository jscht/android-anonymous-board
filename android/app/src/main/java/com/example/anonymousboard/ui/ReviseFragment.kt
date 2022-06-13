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
import com.example.anonymousboard.databinding.ReviseFragmentBinding
import com.example.anonymousboard.model.ResultMessage
import com.example.anonymousboard.model.ReviseInfoModel
import com.example.anonymousboard.model.ReviseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviseFragment : Fragment() {
    private var _binding: ReviseFragmentBinding? = null
    private val binding get() = _binding!!
    private val model: PostsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= ReviseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rviBack.setOnClickListener {
            findNavController().navigate(R.id.action_reviseFragment_to_postFragment)
        }
        loadPostData()



        binding.reviseCompleteBtn.setOnClickListener {
            val title = binding.rviPostTitle.text.toString()
            Log.i("title", binding.rviPostTitle.text.toString())

            val subj = binding.rviPostSubject.text.toString()
            Log.i("subj", binding.rviPostSubject.text.toString())

            val password = binding.rviPostPw.text.toString()
            Log.i("pw", binding.rviPostPw.text.toString())

            if(title != "" && subj != "" && password != "") {
                val rviModel: ReviseModel = ReviseModel(
                    id = model.postId.value.toString(),
                    title = title,
                    subject = subj,
                    boardPw = password
                )
                revisePost(rviModel)
            } else Toast.makeText(this@ReviseFragment.context, "빈칸이 없도록 채워주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun revisePost(data: ReviseModel) {
        // 서버에 데이터 요청해서 받기
        val request = JsServer.postsApi.revisePost(data)
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
                                Toast.makeText(this@ReviseFragment.context, "게시글이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_reviseFragment_to_postsFragment)
                            } else {
                                Toast.makeText(this@ReviseFragment.context, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Log.d("onRESPONSE", "Response: ${response.code()}")
                    }
                }
            })
        } else Log.d("nullCheck", "request is null")
    }

    private fun loadPostData() {
        // 서버에 데이터 요청해서 받기
        val request = JsServer.postsApi.revisePostInfo(id = model.postId.value.toString())
        if (request != null) {
            request.enqueue(object: Callback<ReviseInfoModel> {
                override fun onFailure(call: Call<ReviseInfoModel>, t: Throwable) {
                    Log.d("http error",t.toString())
                }

                override fun onResponse(call: Call<ReviseInfoModel>, response: Response<ReviseInfoModel>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            Log.d("onRESPONSE", "Response: ${response.body()}")
                            binding.rviPostTitle.text = Editable.Factory.getInstance().newEditable(response.body()!!.title)
                            binding.rviPostSubject.text = Editable.Factory.getInstance().newEditable(response.body()!!.subject)
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