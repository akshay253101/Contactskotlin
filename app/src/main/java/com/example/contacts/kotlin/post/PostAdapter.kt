package com.example.contacts.kotlin.post
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.contacts.kotlin.R
import com.example.contacts.kotlin.databinding.ItemListBinding
import com.example.contacts.kotlin.model.Post
import kotlinx.android.synthetic.main.item_list.view.*
import android.view.View
import com.example.contacts.kotlin.utils.needSeparator
import com.example.contacts.kotlin.utils.listArray
import java.lang.IndexOutOfBoundsException


/**
 * Adapter for the list of the posts
 * @property context Context in which the application is running
 */
class PostAdapter(val context: Context) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    /**
     * The list of posts of the adapter
     */
    private var posts: List<Post> = listOf()
    private lateinit var mMaterialColors:IntArray
    private lateinit var listener :OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        setOnItemClickListener(listener)
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_list, parent, false)
        mMaterialColors = context.resources.getIntArray(R.array.colors)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val iconLetter: String = posts[position].firsName[0].toString() + posts[position].lastName[0].toString()
        holder.itemView.icon.letter = iconLetter
        try {

            when {
                needSeparator -> {
                    listArray.add(posts[position].firsName + " " + posts[position].lastName)
                    holder.itemView.separator.text = posts[position].firsName[0].toString()
                    holder.itemView.separator.visibility = View.VISIBLE
                    needSeparator = false
                }

                posts[position].firsName[0] != posts[position + 1].firsName[0] && listArray[listArray.size - 1][0] < posts[position + 1].firsName[0] -> {
                    needSeparator = true
                }
                else -> {
                    holder.itemView.separator.visibility = View.GONE
                }
            }
        }
        catch (e:IndexOutOfBoundsException){
            Log.d("error",e.message)
        }
        for(list in listArray){
            if (list==posts[position].firsName+" "+posts[position].lastName)
            {
                holder.itemView.separator.text = list[0].toString()
                holder.itemView.separator.visibility=View.VISIBLE
            }
        }
        holder.itemView.icon.shapeColor = mMaterialColors[posts[position].contactId%11]
        holder.bind(posts[position])
        holder.itemView.setOnClickListener { listener.onClick(it,posts[position]) }

    }
    interface OnItemClickListener {
        fun onClick(view: View, post: Post)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    /**
     * Updates the list of posts of the adapter
     * @param posts the new list of posts of the adapter
     */
    fun updatePosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
    /**
     * The ViewHolder of the adapter
     * @property binding the DataBinging object for Post item
     */
    class PostViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds a post into the view
         */
        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
        }
    }
}




