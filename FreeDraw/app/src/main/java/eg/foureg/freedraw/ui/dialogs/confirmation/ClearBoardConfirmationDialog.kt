package eg.foureg.freedraw.ui.dialogs.confirmation

import androidx.appcompat.app.AlertDialog
import android.content.Context
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.messageEditBoardClear
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment

object ClearBoardConfirmationDialog {
    const val TAG = "ClearBoardConfirmationDialog"

    fun createDialog(context: Context) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_clear_board_dialog_title))
        alertDialog.setMessage(context.getString(R.string.txt_clear_board_dialog_msg))

        alertDialog.setPositiveButton(context.getString(R.string.txt_clear_board_dialog_positive_btn)) { dialog, _ ->
            ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageEditBoardClear)
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_clear_board_dialog_negative_btn)) { dialog, _ ->
            dialog.cancel()
        }

        return alertDialog
    }
}