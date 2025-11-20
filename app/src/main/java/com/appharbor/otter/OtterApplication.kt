package com.appharbor.otter

import android.app.Application
import com.appharbor.otter.data.python.PythonManager

class OtterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PythonManager.initialize(this)
    }
}
