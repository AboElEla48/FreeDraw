package eg.foureg.freedraw.ui.dialogs.boardname

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.messageEditBoardSetBoardName
import eg.foureg.freedraw.data.messageEditBoardSetBoardNameMap
import eg.foureg.freedraw.data.messageEditBoardSetBoardNameParam
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment

object BoardNameInputDialog {
    const val TAG = "BoardNameInputDialog"

    fun createDialog(context: Context) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_board_name_dialog_title_enter_to_save))
        alertDialog.setMessage(context.getString(R.string.txt_board_name_dialog_msg_enter_to_save))

        val input = EditText(context)
        val lp = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        alertDialog.setView(input)

        alertDialog.setPositiveButton(context.getString(R.string.txt_board_name_dialog_positive_enter_to_save)) { dialog, which ->
            Logs.debug(TAG, "Board Name entered is ${input.text}")
            messageEditBoardSetBoardNameMap[messageEditBoardSetBoardNameParam] = input.text.toString()
            ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageEditBoardSetBoardName)
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_board_name_dialog_negative_enter_to_save)) { dialog, which ->
            dialog.cancel()
        }

        return alertDialog

    }
}