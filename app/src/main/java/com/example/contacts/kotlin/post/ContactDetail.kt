package com.example.contacts.kotlin.post

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.example.contacts.kotlin.R
import com.example.contacts.kotlin.databinding.ContactDetailBinding
import com.example.contacts.kotlin.model.Post
import com.google.gson.Gson
import kotlinx.android.synthetic.main.contact_detail.*
/**
 * Activity displaying contact details
 */
class ContactDetail: AppCompatActivity() {

    /**
     * DataBinding instance
     */
    private lateinit var binding: ContactDetailBinding
    /**
     * Material color array for contact letter icon
     */
    private lateinit var mMaterialColors:IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.contact_detail)
        updateView(binding)

    }
    /**
     * Bind activity view
     */
    private fun updateView(binding:ContactDetailBinding)
    {
        mMaterialColors = this.resources.getIntArray(R.array.colors)
        val bundle =intent.extras
        var json:String? = null
        if (bundle!=null){
            json= bundle.getString("post")
        }
        val gson = Gson()
        val post : Post = gson.fromJson(json, Post::class.java)
        val iconLetter: String = post.firsName[0].toString() + post.lastName[0].toString()
        title = post.firsName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.edit.setOnClickListener { view ->
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        binding.contactIcon.letter = iconLetter
        binding.contactIcon.shapeColor = mMaterialColors[post.contactId%11]
        binding.contact = post
        binding.executePendingBindings()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
