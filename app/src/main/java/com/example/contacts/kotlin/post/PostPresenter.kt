package com.example.contacts.kotlin.post
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.contacts.kotlin.R
import com.example.contacts.kotlin.base.BasePresenter
import com.example.contacts.kotlin.model.Post
import com.example.contacts.kotlin.model.PostDao
import com.example.contacts.kotlin.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * The Presenter that will present the Post view.
 * @param postView the Post view to be presented by the presenter
 * @property postApi the API interface implementation
 * @property subscription the subscription to the API call
 */
class PostPresenter(postView: PostView) : BasePresenter<PostView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    @Inject
    lateinit var postDao: PostDao

    private var subscription: Disposable? = null

    private val mutablePostList: MutableLiveData<List<Post>> = MutableLiveData()

    override fun onViewCreated() {
        val postListObserver = Observer<List<Post>> { postList ->
            if (postList != null) {
                view.updatePosts(postList)
            }
        }

        mutablePostList.observe(view, postListObserver)
        loadPosts()
    }
    /**
     * Loads the posts from the API and presents them in the view when retrieved, or shows error if
     * any.
     */
    private fun loadPosts() {
        view.showLoading()
        subscription = Observable.fromCallable { postDao.all }
                .flatMap { postList -> if (postList.isNotEmpty()) Observable.just(postList) else saveApiPostsInDatabase() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate {loadNull() }
                .subscribe(
                        { view.hideLoading() },
                        { view.showError(R.string.unknown_error) }
                )

    }
    private fun loadNull(){
        subscription = Observable.fromCallable{postDao.loadAll()}
                .flatMap { postList -> Observable.just(postList) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { postList -> mutablePostList.value = postList }
                ) { view.showError(R.string.unknown_error) }
    }
    fun filteredPost(search :String?){
        when (search) {
            null -> loadNull()
            "" -> loadNull()
            else -> subscription = Observable.fromCallable{postDao.loadData(search)}
                    .flatMap { postList -> Observable.just(postList) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            { postList -> mutablePostList.value = postList }
                    ) { view.showError(R.string.unknown_error) }
        }
    }

    /**
     * Load the posts from the API and store them in the database.
     * @return an Observable for the list of Post retrieved from API
     */
    private fun saveApiPostsInDatabase(): Observable<List<Post>> {
        return postApi.getPosts()
                .flatMap { postList -> Observable.fromCallable { postDao.insertAll(*postList.toTypedArray());postList } }

    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}