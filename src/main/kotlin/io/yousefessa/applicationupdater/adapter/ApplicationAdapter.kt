package io.yousefessa.applicationupdater.adapter

import io.yousefessa.applicationupdater.adapter.DefaultApplicationAdapter
import java.io.File
import java.util.function.Consumer

interface ApplicationAdapter {
    fun onDownload(context: ApplicationAdapterContext)

    companion object {
        @JvmStatic
        fun defaultAdapter(
            fileOutputDestination: File,
            onComplete: Consumer<File>,
        ): ApplicationAdapter {
            return DefaultApplicationAdapter(fileOutputDestination, onComplete)
        }
    }
}