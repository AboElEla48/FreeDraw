package eg.foureg.freedraw.ui.dialogs.textdrawmsg

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.model.DrawingToolsModel

object TextDrawMessageInputDialog {
    const val TAG = "TextDrawMessageInputDialog"

    fun createDialog(context: Context) : AlertDialog.Builder{
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_txt_shape_dialog_title))
        alertDialog.setMessage(context.getString(R.string.txt_txt_shape_dialog_msg))

        val input = EditText(context)
        val lp = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        alertDialog.setView(input)

        alertDialog.setPositiveButton(context.getString(R.string.txt_txt_shape_dialog_positive_btn)) { dialog, which ->
            Logs.debug(TAG, "Text Shape String ${input.text}")
            DrawingToolsModel.drawingText = input.text.toString()
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_txt_shape_dialog_negative_btn)) { dialog, which ->
            DrawingToolsModel.drawingText = ""
            dialog.cancel()
        }

        return alertDialog
    }
}