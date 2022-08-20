
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import org.tinylog.kotlin.Logger
import java.io.File
import java.io.InputStream
import java.util.function.Consumer

// test
class DefaultApplicationAdapter(
    private val fileDestination: File,
    private val onComplete: Consumer<File>,
) : ApplicationAdapter {

    init {
        Logger.debug("DefaultApplicationAdapter init's kotlin invoked")
    }

    override fun onDownload(inputStream: InputStream) {
        Logger.debug("now on #onDownload' checking if file exists")
        if (fileDestination.exists()) {
            Logger.debug("file does exist, delete em")
            fileDestination.deleteRecursively()
        }

        Logger.debug("onDownload")

        fileDestination.outputStream().use { output ->
            inputStream.use {
                it.runCatching {
                    val bytes = this.readBytes()
                    output.write(bytes)

                    onComplete.accept(fileDestination)
                }
            }
        }
    }
}