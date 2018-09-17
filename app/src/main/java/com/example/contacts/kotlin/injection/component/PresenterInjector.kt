package com.example.contacts.kotlin.injection.component

import com.example.contacts.kotlin.base.BaseView
import com.example.contacts.kotlin.injection.module.ContextModule
import com.example.contacts.kotlin.injection.module.DaoModule
import com.example.contacts.kotlin.injection.module.NetworkModule
import com.example.contacts.kotlin.post.PostPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class), (DaoModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param postPresenter PostPresenter in which to inject the dependencies
     */
    fun inject(postPresenter: PostPresenter)

    @Component.Builder
    interface Builder {

        fun build(): PresenterInjector
        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun daoModule(daoModule: DaoModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}