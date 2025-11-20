package com.appharbor.otter.data.python

import android.content.Context
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

object PythonManager {
    private var isInitialized = false

    fun initialize(context: Context) {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }
        isInitialized = true
    }

    fun getModule(name: String): PyObject {
        if (!isInitialized) {
            throw IllegalStateException("PythonManager must be initialized before use")
        }
        return Python.getInstance().getModule(name)
    }
}
