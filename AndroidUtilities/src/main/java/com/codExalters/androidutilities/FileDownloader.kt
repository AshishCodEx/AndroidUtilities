package com.codExalters.androidutilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InterruptedIOException
import java.net.URL
import java.util.concurrent.Future

/**
 * Created by Ashish on 30/10/19.
 */
object FileDownloader {

    fun downloadFile(
        fileUrl: String,
        storagePath: String,
        fileName: String,
        onFileDownloaded: (downloadedFilePath: String) -> Unit,
        onErrorOccurred: (error: String) -> Unit,
        progressUpdate: (Int) -> Unit,
        isCanceledByUser: () -> Unit
    ): Future<Unit> {

        Log.w("FileDownloader", "downloading File : $fileUrl")

        var isSuccess: Boolean

        return doAsync {

            // var downloadedFileUri = Uri.EMPTY

            val directory = File(storagePath)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, fileName)

            var count: Int
            var total = 0;
            try {
                val url = URL(fileUrl)
                val urlConnection = url.openConnection()

                urlConnection.connect()

                // getting file length
                val lengthOfFile = urlConnection.contentLength

                // inputStream stream to read file - with 8k buffer
                val input = BufferedInputStream(url.openStream(), 8192)

                // Output stream to write file
                val output = FileOutputStream(file)

                val data = ByteArray(1024)

                count = input.read(data)
                while (count != -1) {
                    total += count;
                    // publishing the onProgressUpdate....
                    // After this onProgressUpdate will be called

                    if (lengthOfFile > 0) {
                        val progress = ((total * 100) / lengthOfFile)

                        uiThread {
                            progressUpdate(progress)
                        }
                        // writing data to file
                        output.write(data, 0, count)
                        count = input.read(data)
                    }
                }

                // flushing output
                output.flush()

                // closing streams
                output.close()
                input.close()

                isSuccess = true

            } catch (e: Exception) {
                e.printStackTrace()
                isSuccess = false

                uiThread {

                    if (e is InterruptedIOException) {
                        isCanceledByUser()
                    } else {
                        onErrorOccurred("Could not download file!")
                    }
                }

                return@doAsync
            }

            uiThread {

                if (isSuccess) {
                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    onFileDownloaded(file.absolutePath)
                } else {
                    onErrorOccurred("Could not download file!")
                }

            }
        }

    }


    fun downloadFileInBitmap(
        fileUrl: String,
        onFileDownloaded: (downloadedFileBitmap: Bitmap?, isTaskSuccess: Boolean) -> Unit
    ) {

        Log.w("FileDownloader", "downloadFileInBitmap : $fileUrl")

        if (fileUrl.isEmpty()) {
            return
        }
        doAsync {

            try {
                val bitmap =
                    BitmapFactory.decodeStream(URL(fileUrl).openConnection().getInputStream())

                uiThread {

                    Log.d("FileDownloader", "downloadFileInBitmap : success ")
                    onFileDownloaded(bitmap, true)
                }
            } catch (e: Exception) {
                Log.e("FileDownloader", "downloadFileInBitmap :Error :: ${e.toString()} ")
                uiThread {
                    onFileDownloaded(null, false)
                }
            }
        }
    }


}