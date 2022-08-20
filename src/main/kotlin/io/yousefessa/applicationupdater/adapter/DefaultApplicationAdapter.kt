
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import java.io.File
import java.io.InputStream
import java.util.function.Consumer

// test
class DefaultApplicationAdapter(
    private val fileDestination: File,
    private val onComplete: Consumer<File>,
) : ApplicationAdapter {

    init {
        println("DefaultApplicationAdapter init's kotlin invoked")
    }

    override fun onDownload(inputStream: InputStream) {
        println("now on #onDownload' checking if file exists")
        if (fileDestination.exists()) {
            println("file does exist, delete em")
            fileDestination.deleteRecursively()
        }

        println("onDownload")

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