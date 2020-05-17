package eg.foureg.freedraw.ui.dialogs.confirmation

import androidx.appcompat.app.AlertDialog
import android.content.Context
import eg.foureg.freedraw.R

object ClearBoardConfirmationDialog {
    const val TAG = "ClearBoardConfirmationDialog"

    fun createDialog(context: Context, clearBoardDialogInt: ClearBoardDialogInt) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_clear_board_dialog_title))
        alertDialog.setMessage(context.getString(R.string.txt_clear_board_dialog_msg))

        alertDialog.setPositiveButton(context.getString(R.string.txt_clear_board_dialog_positive_btn)) { dialog, which ->
            clearBoardDialogInt.clearBoardConfirmed()
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_clear_board_dialog_negative_btn)) { dialog, which ->
            dialog.cancel()
        }

        return alertDialog
    }
}