package com.sample.viewbinding.activity

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*
* created by Saksham Pruthi
* bit.ly/sakshampruthi
*/

inline fun <reified T : ViewBinding> Activity.viewBinding() = ActivityViewBinding(T::class.java)

class ActivityViewBinding<T : ViewBinding>(private val bindingClass: Class<T>) :
    ReadOnlyProperty<Activity, T> {

    private var bind: T? = null
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        bind?.let { return it }

        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)

        val invokeLayout = inflateMethod.invoke(null, thisRef.layoutInflater) as T

        thisRef.setContentView(invokeLayout.root)

        return invokeLayout.also { this.bind = it }
    }

}