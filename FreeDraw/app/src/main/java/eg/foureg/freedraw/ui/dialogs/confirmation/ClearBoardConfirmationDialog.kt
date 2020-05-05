package eg.foureg.freedraw.ui.dialogs.confirmation

import androidx.appcompat.app.AlertDialog
import android.content.Context
import eg.foureg.freedraw.R

object ClearBoardConfirmationDialog {
    const val TAG = "ClearBoardConfirmationDialog"

    fun createDialog(context: Context, clearBoardDialogInt: ClearBoardDialogInt) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_dialog_title_clear_board))
        alertDialog.setMessage(context.getString(R.string.txt_dialog_msg_clear_board_name))

        alertDialog.setPositiveButton(context.getString(R.string.txt_dialog_positive_clear_board)) { dialog, which ->
            clearBoardDialogInt.clearBoardConfirmed()
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_dialog_negative_clear_board)) { dialog, which ->
            dialog.cancel()
        }

        return alertDialog
    }
}