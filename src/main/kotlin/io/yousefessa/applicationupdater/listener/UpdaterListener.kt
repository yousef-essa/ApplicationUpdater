package io.yousefessa.applicationupdater.listener

interface UpdaterListener {
    fun onUpdateSuccess(version: Number)
    fun onUpdateFailure(reason: String)
}