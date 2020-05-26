package eg.foureg.freedraw.ui.dialogs.confirmation

import android.content.Context
import androidx.appcompat.app.AlertDialog
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.messageDeleteBoardsFromListing
import eg.foureg.freedraw.ui.boards.listing.BoardsListingFragment

object DeleteBoardsConfirmationDialog {

    fun createDialog(context: Context) : AlertDialog.Builder {
        val alertDialog  = AlertDialog.Builder(context)
        alertDialog.setTitle(context.getString(R.string.txt_delete_boards_dialog_title))
        alertDialog.setMessage(context.getString(R.string.txt_delete_boards_dialog_msg))

        alertDialog.setPositiveButton(context.getString(R.string.txt_delete_boards_dialog_positive_btn)) { dialog, _ ->
            ActorMessageDispatcher.sendMessage(BoardsListingFragment::class.java, messageDeleteBoardsFromListing)
            dialog.cancel()
        }

        alertDialog.setNegativeButton(context.getString(R.string.txt_delete_boards_dialog_negative_btn)) { dialog, _ ->
            dialog.cancel()
        }

        return alertDialog
    }
}