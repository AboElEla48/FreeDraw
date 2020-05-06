package eg.foureg.freedraw.export

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import android.os.Environment
import android.view.View
import eg.foureg.freedraw.common.Logs
import java.io.File
import java.io.FileOutputStream
import java.util.*


object ImageExport {

    const val TAG = "ImageExport"

    fun exportViewAsImage(context: Context, view: View) {

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)


        val root = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/saved_images")
        myDir.mkdirs()

        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val fname = "Image-$n.jpg"

        val file = File(myDir, fname)

        Logs.debug(TAG, file.absolutePath)

        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()

            Logs.debug(TAG, "File written")

            MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null) { path, uri ->
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Logs.debug(TAG, "Exception in exporting image")
        }

    }
}