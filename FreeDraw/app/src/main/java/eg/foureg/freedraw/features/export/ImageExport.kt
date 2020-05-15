package eg.foureg.freedraw.features.export

import android.content.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object ImageExport {

    const val TAG = "ImageExport"

    fun exportViewAsImage(context: Context, view: View) {

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)

        saveFile(context, bitmap)
    }


    private fun saveFile(context: Context, bitmap: Bitmap) : String{
        val currentDate = "${SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())}_${Calendar.getInstance().get(Calendar.HOUR)}_${Calendar.getInstance().get(Calendar.MINUTE)}_${Calendar.getInstance().get(Calendar.SECOND)}"


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

            Toast.makeText(context, context.getString(R.string.txt_toast_export_image_success) + " ${file.absolutePath}", Toast.LENGTH_LONG).show()

            return file.absolutePath

        } catch (e: Exception) {
            e.printStackTrace()
            Logs.error(TAG, "Exception in exporting image")

            Toast.makeText(context, context.getString(R.string.txt_toast_export_image_failure), Toast.LENGTH_LONG).show()
        }

        return ""
    }



}