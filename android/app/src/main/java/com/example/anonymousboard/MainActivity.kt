package com.example.anonymousboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.anonymousboard.databinding.ActivityMainBinding
import com.example.anonymousboard.model.Posts

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val model: PostsViewModel by viewModels() // 이 한줄로 바꿀 수 있다. (데이터 공유할 것이 아니라면)
    // private lateinit var postsViewModel: PostsViewModel // 이거랑

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // postsViewModel = ViewModelProvider(this).get(PostsViewModel::class.java) // 요고 두 줄을

        model.postId.observe(this, Observer {
            Log.i("vmObserver", "${model.postId}")
        })
    }
}

class PostsViewModel: ViewModel() {
    val error = MutableLiveData<String>()
    private var _postId = MutableLiveData<Int>()

    private val list = mutableListOf<Posts>()
    private var _post = MutableLiveData<List<Posts>>()

    val postId: LiveData<Int>
        get() = _postId

    val post: LiveData<List<Posts>>
        get() = _post

    init {
        _postId.value = 0
    }

    fun setPostId(id: Int) {
        _postId.value = id
    }

    fun setPost(item: Posts) {
        list.add(item)
        _post.value = list
        Log.i("asd","${_post.value}")
    }

    override fun onCleared() {
        super.onCleared()
    }
}