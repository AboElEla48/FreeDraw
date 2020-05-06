package eg.foureg.freedraw.export

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Environment
import android.view.View
import android.widget.Toast
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


object ImageExport {

    const val TAG = "ImageExport"

    fun exportViewAsImage(context: Context, view: View) {

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)


//        val root = "/Internal storage/FreeDraw"
//        val myDir = File(root)
//        myDir.mkdirs()

//        Logs.debug(TAG, "Root Path: $root")
//        Logs.debug(TAG, "externalMediaDirs: ${context.externalMediaDirs}")
//        Logs.debug(TAG, "Environment.getExternalStorageState: ${Environment.getExternalStorageState()}")
//        Logs.debug(TAG, "Environment.getRootDirectory(): ${Environment.getRootDirectory()}")
//        Logs.debug(TAG, "context.getExternalFilesDir(Environment.DIRECTORY_DCIM): ${context.getExternalFilesDir(Environment.DIRECTORY_DCIM)}")



        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val fname = "Image-$currentDate.jpg"
        Logs.debug(TAG, "Image Name: $fname")

        val file = File(context.getExternalFilesDir("FreeDraw"), fname)
        Logs.debug(TAG, file.absolutePath)

        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()

            Logs.debug(TAG, "Exported Image File written")

            MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null) { path, uri ->
            }

            Toast.makeText(context, context.getString(R.string.txt_toast_export_image_success) + " $fname", Toast.LENGTH_LONG).show()


        } catch (e: Exception) {
            e.printStackTrace()
            Logs.error(TAG, "Exception in exporting image")

            Toast.makeText(context, context.getString(R.string.txt_toast_export_image_failure), Toast.LENGTH_LONG).show()
        }

    }


}