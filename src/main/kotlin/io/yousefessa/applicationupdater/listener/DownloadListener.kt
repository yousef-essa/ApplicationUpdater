package io.yousefessa.applicationupdater.listener

interface DownloadListener: UpdaterListener {
    fun onDownloadSuccess()
    fun onDownloadFailure(reason: String)
}