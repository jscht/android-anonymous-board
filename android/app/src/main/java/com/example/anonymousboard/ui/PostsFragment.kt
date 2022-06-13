package com.example.anonymousboard.ui

import com.example.anonymousboard.R
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anonymousboard.PostsAdapter
import com.example.anonymousboard.api.JsServer
import com.example.anonymousboard.databinding.PostsFragmentBinding
import com.example.anonymousboard.model.Posts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.anonymousboard.PostsViewModel

class PostsFragment : Fragment() {
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!
    private val model: PostsViewModel by activityViewModels()
    private val adapter = PostsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= PostsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSample.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSample.adapter = adapter // 어댑터에서 불러오기
        Log.i("wqewq", model.post.value.toString())
        loadData()

        var spn: Spinner = binding.spinnerList.spnList
        var spn_adapter = this@PostsFragment.context?.let {
            ArrayAdapter.createFromResource(it, R.array.order_item, android.R.layout.simple_spinner_item)
        }
        spn_adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn.setAdapter(spn_adapter)
        var currentOrder: String = ""
        spn.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.i("w", spn.selectedItem.toString())
                if(spn.selectedItem.toString() == "시간순")
                    currentOrder = ""
                else if (spn.selectedItem.toString() == "조회순")
                    currentOrder = "views"
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        adapter.setOnItemClickListener{
            Log.i("onViewCreated adapter", "on")
            val postData = adapter.getData(it)
            Log.i("postData", "$postData")
            model.setPostId(postData.id)
            findNavController().navigate(R.id.action_postsFragment_to_postFragment)
        }

        var editText: EditText = binding.searchBarLayout.findViewById(R.id.search_text_view)
        editText.setOnEditorActionListener { textView, i, keyEvent ->
            val searchText: String = textView.text.toString()
            // 검색어를 통한 조회 searchText
            Log.i("chk", "$searchText, $currentOrder")
            val request = JsServer.postsApi.getPosts(keyword = searchText, order = currentOrder)
            request.enqueue(object: Callback<List<Posts>> {
                override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                    Log.d("http error",t.toString())
                }
                override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            if(it.size == 0) {
                                Toast.makeText(this@PostsFragment.context, "조회된 게시글이 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                            adapter.setData(it)
                        }
                    } else {
                        Log.d("onRESPONSE", "Response: ${response.code()}")
                    }
                }
            })

            textView.clearFocus();
            textView.setText("");
            textView.clearFocus();
            textView.setFocusableInTouchMode(true);
            textView.setFocusable(true);

            false
        }





        binding.registButton.setOnClickListener {
            findNavController().navigate(R.id.action_postsFragment_to_registFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        lifecycleScope.launch {
            showData(isLoading = true) // 로딩 화면

            delay(1000) // 딜레이 1초

            // 서버에 데이터 요청해서 받기
            val request = JsServer.postsApi.getPosts(keyword = "", order = "")
            request.enqueue(object: Callback<List<Posts>> {
                override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                    Log.d("http error",t.toString())
                }
                override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            adapter.setData(it)
                            Log.d("onRESPONSE", "Response: ${response.body()}")
                        }
                    } else {
                        Log.d("onRESPONSE", "Response: ${response.code()}")
                    }
                }
            })

            showData(isLoading = false) // 로딩이 끝나고 리스트 출력
        }
    }

    private fun showData(isLoading: Boolean) {
        if (isLoading) {
            binding.sflSample.startShimmer()
            binding.registButton.visibility = View.GONE
            binding.searchBarLayout.visibility = View.GONE
            binding.sflSample.visibility = View.VISIBLE
            binding.rvSample.visibility = View.GONE
        } else {
            binding.sflSample.stopShimmer()
            binding.registButton.visibility = View.VISIBLE
            binding.searchBarLayout.visibility = View.VISIBLE
            binding.sflSample.visibility = View.GONE
            binding.rvSample.visibility = View.VISIBLE
        }
    }
}