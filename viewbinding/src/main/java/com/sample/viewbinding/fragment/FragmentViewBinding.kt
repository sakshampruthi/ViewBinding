package com.sample.viewbinding.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*
* created by Saksham Pruthi
* bit.ly/sakshampruthi
*/

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBinding(T::class.java, this)

class FragmentViewBinding<T : ViewBinding>(bindingClass: Class<T>, private val fragment: Fragment) :
    ReadOnlyProperty<Fragment, T> {

    private var bind: T? = null

    private val bindingMethod = bindingClass.getMethod("bind", View::class.java)

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            bind = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        bind?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Unable to access bindings. State of view lifecycle: ${lifecycle.currentState}!")
        }

        val invoke = bindingMethod.invoke(null, thisRef.requireView()) as T

        return invoke.also { this.bind = it }
    }
}