package eg.foureg.freedraw.ui.boards.editor.namedialog

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginStart
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs


object BoardNameInputDialog {
    const val TAG = "BoardNameInputDialog"

    fun createDialog(context: Context, boardNameInputDialogInt: BoardNameInputDialogInt) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_dialog_title_enter_board_name_to_save))
        alertDialog.setMessage(context.getString(R.string.txt_dialog_msg_enter_board_name_to_save))

        val input = EditText(context)
        val lp = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        alertDialog.setView(input)

        alertDialog.setPositiveButton(context.getString(R.string.txt_dialog_positive_enter_board_name_to_save)) { dialog, which ->
            Logs.debug(TAG, "Board Name entered is ${input.text.toString()}")
            boardNameInputDialogInt.boardNameDialogPositiveAction(input.text.toString())
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_dialog_negative_enter_board_name_to_save)) { dialog, which ->
            boardNameInputDialogInt.boardNameDialogNegativeAction()
            dialog.cancel()
        }

        return alertDialog

    }
}