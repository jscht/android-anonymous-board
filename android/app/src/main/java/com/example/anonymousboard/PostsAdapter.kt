package com.example.anonymousboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anonymousboard.PostsAdapter.PostsViewHolder
import com.example.anonymousboard.model.Posts

class PostsAdapter(private var listener: OnItemClickListener?): RecyclerView.Adapter<PostsViewHolder>(){
    // PostsAdapter 클래스를 생성할 때 listener가 있는것과 없는것으로 생성 하도록 만든 것
    constructor(): this(null)
    private var data: List<Posts> = listOf()

    fun interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?){
        Log.i("setOnItemClickListener", "$listener")
        this.listener = listener
    }

    fun getData(position: Int): Posts{
        Log.i("getData", "$position")
        return data[position]
    }

    fun setData(data: List<Posts>) {
        this.data = data
        notifyDataSetChanged()
    }

    // view 뿐만아니라 클릭에 대한 감지를 해야하기 때문에 추가적으로 파라미터 listener를 받는다.
    class PostsViewHolder(view: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(view){
        val boardId: TextView = view.findViewById(R.id.boardId_text)
        val title: TextView = view.findViewById(R.id.title_text)
        val time: TextView = view.findViewById(R.id.time_text)
        val views: TextView = view.findViewById(R.id.views_text)
        init {
            // view 자체 클릭 리스너, 위의 선언한 함수랑 별개
            view.setOnClickListener {
                Log.i("view.setOnClickListener", "listener?.onItemClick(${this.layoutPosition})")
                listener?.onItemClick(this.layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.posts_frame, parent, false)
        return PostsViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = data[position]
        holder.boardId.text = item.id.toString()
        holder.title.text = item.title
        holder.time.text = item.created
        holder.views.text = item.views.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}