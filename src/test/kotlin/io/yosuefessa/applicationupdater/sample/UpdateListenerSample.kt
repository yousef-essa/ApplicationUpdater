package io.yosuefessa.applicationupdater.sample

import io.yousefessa.applicationupdater.listener.DownloadListener
import java.util.function.Consumer

class UpdateListenerSample(
    onDownloadSuccess: Consumer<Void>,
    onDownloadFailure: Consumer<Void>,
    onUpdateSuccess: Consumer<Void>,
    onUpdateFailure: Consumer<Void>,
): DownloadListener {
    override fun onDownloadSuccess() {
        TODO("Not yet implemented")
    }

    override fun onDownloadFailure(reason: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateSuccess(version: Number) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(reason: String) {
        TODO("Not yet implemented")
    }
}