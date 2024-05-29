package corp.hell.kernel.parent.imp

/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since March 04, 2022
 */
import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Decided on this approach after checking few other similar options, This approach is fine tuned
 * and works properly for SingleEvent Use Case, as in once observed by the UI, only new values shall be
 * triggered again in the LiveData. If there is a new observer connected, then the last set value
 * in the LiveData shall be triggered/emitted.
 */
class LiveEvent<T> : MediatorLiveData<T>() {

    private val observers = ArraySet<ObserverWrapper<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observers.remove(observer as ObserverWrapper)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T) {
            if (pending && t != null) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}