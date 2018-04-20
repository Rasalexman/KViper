package com.mincor.kviper.utils

/**
 * Created by a.minkin on 23.10.2017.
 */
import com.bluelinelabs.conductor.Controller
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A property that clears the reference after [Controller.onDestroyView]
 */
class ClearAfterDestroyView<T : Any>(controller: Controller) : ReadWriteProperty<Controller, T> {

    private var value: T? = null

    init {
        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun postDestroyView(@Suppress("NAME_SHADOWING") controller: Controller) {
                value = null
            }
        })
    }

    override fun getValue(thisRef: Controller, property: KProperty<*>) = value
            ?: throw IllegalStateException("Property ${property.name} should be initialized before get and not called after postDestroyView")

    override fun setValue(thisRef: Controller, property: KProperty<*>, value: T) {
        this.value = value
    }
}

fun <T : Any> Controller.clearAfterDestroyView(): ReadWriteProperty<Controller, T> = ClearAfterDestroyView(this)