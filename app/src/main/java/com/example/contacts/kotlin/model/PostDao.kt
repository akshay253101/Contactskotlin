package com.example.contacts.kotlin.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
/**
 * The database object in charge of inserting Post objects and retrieving them from Database.
 * @property all the list of all Posts in database
 */
@Dao
interface PostDao {
    @get:Query("SELECT * FROM post ORDER BY firsName,lastName")
    val all: List<Post>

    /**
     * @query loadData the filtered alphabetically ordered list from database
     */
    @Query("SELECT * FROM post WHERE firsName OR email OR phone OR lastName LIKE :firstName||'%' ORDER BY firsName,lastName")
    fun loadData(firstName:String?):List<Post>
    /**
     * @query loadData the alphabetically ordered list from database
     */
    @Query("SELECT * FROM post ORDER BY firsName,lastName")
    fun loadAll():List<Post>
    /**
     * Inserts specified posts in database.
     * @param posts all the posts to insert in database
     */
    @Insert
    fun insertAll(vararg posts: Post)
}