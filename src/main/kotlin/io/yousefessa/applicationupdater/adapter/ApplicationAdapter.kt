package io.yousefessa.applicationupdater.adapter

import DefaultApplicationAdapter
import java.io.File
import java.io.InputStream
import java.util.function.Consumer

interface ApplicationAdapter {
    fun onDownload(inputStream: InputStream)

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