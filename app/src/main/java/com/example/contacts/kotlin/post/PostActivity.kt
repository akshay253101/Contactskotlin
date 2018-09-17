package com.example.contacts.kotlin.post
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.multidex.MultiDexApplication
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.contacts.kotlin.R
import com.example.contacts.kotlin.base.BaseActivity
import com.example.contacts.kotlin.databinding.ActivityMainBinding
import com.example.contacts.kotlin.model.Post
import com.example.contacts.kotlin.utils.listArray
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity displaying the list of posts
 */
class PostActivity : BaseActivity<PostPresenter>(), PostView, android.widget.SearchView.OnQueryTextListener {


    /**
     * DataBinding instance
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * The adapter for the list of posts
     */
    private val postsAdapter = PostAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.adapter = postsAdapter
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        contactDetail()
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(this)
        presenter.onViewCreated()

    }

    private fun contactDetail(){
        postsAdapter.setOnItemClickListener(object :PostAdapter.OnItemClickListener{
            override fun onClick(view: View,post: Post){
                val gson = Gson()
                val json: String? = gson.toJson(post)
                val intent = Intent(applicationContext,ContactDetail::class.java)
                intent.putExtra("post",json)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun updatePosts(posts: List<Post>) {
        postsAdapter.updatePosts(posts)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun instantiatePresenter(): PostPresenter {
        return PostPresenter(this)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.filteredPost(newText)
        return false
    }
}

