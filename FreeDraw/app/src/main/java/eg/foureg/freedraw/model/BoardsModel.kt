package eg.foureg.freedraw.model

import android.content.Context
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.model.storage.StorePreferences

class BoardsModel {

    companion object {
        const val TAG = "BoardsModel"

    }

    /**
     * Generate new key for new boards
     */
    fun generateNewBoardKey(context: Context) : String {
        val keys = StorePreferences.loadBoardsKeysList(context)

        return "Key_" + (keys.size + 1)
    }

    /**
     * Load all boards keys
     */
    fun loadBoardsKeys(context: Context) : List<String> {
        return StorePreferences.loadBoardsKeysList(context)
    }
    
    fun loadBoardName(context: Context, boardKey: String) : String {
        return StorePreferences.loadBoardName(context, boardKey)
    }

    /**
     * Load specific board
     */
    fun loadBoard(context: Context, boardKey: String) : Board {
        return StorePreferences.loadBoard(context, boardKey)
    }

    /**
     * save given board
     */
    fun saveBoard(context: Context, board: Board) {
        StorePreferences.saveBoard(context, board.boardKey, board )

        // if this is a new key, save it
        val keysList = loadBoardsKeys(context) as ArrayList<String>
        if(!keysList.contains(board.boardKey)) {
            keysList.add(board.boardKey)

            StorePreferences.saveBoardsKeysList(context, keysList)
        }
    }


}