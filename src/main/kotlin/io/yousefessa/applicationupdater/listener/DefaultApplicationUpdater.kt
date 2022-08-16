
import io.yousefessa.applicationupdater.listener.ApplicationAdapter
import io.yousefessa.applicationupdater.util.ApplicationUtil
import java.io.File
import java.util.function.Consumer

class DefaultApplicationAdapter(
    private val fileDestination: File,
    private val onComplete: Consumer<File>,
) : ApplicationAdapter {

    init {
        println("DefaultApplicationAdapter init's kotlin invoked")
    }

    override fun onDownload(fileDestinationLink: String) {
        println("now on #onDownload' checking if file exists")
        if (fileDestination.exists()) {
            println("file does exist, delete em")
            fileDestination.deleteRecursively()
        }

        println("onDownload")
        val inputStream = ApplicationUtil.getInputStreamFrom(fileDestinationLink) ?:
        return

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