package com.ramand.noteapp.utils.base
import android.annotation.SuppressLint
import androidx.annotation.NonNull
import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenterImpl: BasePresenter {

    @SuppressLint("KotlinNullnessAnnotation")
    @NonNull
    var disposable: Disposable? = null
    override fun onStop() {
        disposable?.let {
            it.dispose()
        }

    }
}