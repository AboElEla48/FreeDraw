package eg.foureg.freedraw.ui.dialogs.information

import android.content.Context
import androidx.appcompat.app.AlertDialog
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.messageEditBoardClear
import eg.foureg.freedraw.data.messageInsertBoard
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment

object RequestToDrawBoardFrameDialog {
    const val TAG = "RequestToDrawBoardFrameDialog"

    fun createDialog(context: Context) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_information_insert_board_dialog_title))
        alertDialog.setMessage(context.getString(R.string.txt_information_insert_board_dialog_msg))

        alertDialog.setPositiveButton(context.getString(R.string.txt_information_insert_board_dialog_positive_btn)) { dialog, _ ->
            ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageInsertBoard)
            dialog.cancel()
        }

        return alertDialog
    }
}