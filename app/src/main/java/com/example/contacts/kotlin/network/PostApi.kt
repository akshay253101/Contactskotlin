package com.example.contacts.kotlin.network
import com.example.contacts.kotlin.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("data.json")
    fun getPosts(): Observable<List<Post>>
}