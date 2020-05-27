package eg.foureg.freedraw.model

import android.content.Context
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.model.storage.StorePreferences
import java.util.*
import kotlin.collections.ArrayList

class BoardsModel {

    companion object {
        const val TAG = "BoardsModel"

    }

    /**
     * Generate new key for new boards
     */
    fun generateNewBoardKey(context: Context) : String {
        val keys = StorePreferences.loadBoardsKeysList(context)
        val rand = Random()
        lateinit var newKey : String
        do {
            val keyVal = rand.nextInt(keys.size * 3)
            Logs.debug(TAG, "Random generated key: $keyVal")
            newKey = "Key_$keyVal"

        }while(keys.contains(newKey))

        Logs.debug(TAG, "New Generated board key: $newKey")
        return newKey
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

    fun deleteBoards(context: Context, boardKeys: List<String>) {
        Logs.debug(TAG, "deleteBoard($boardKeys)")
        StorePreferences.deleteBoards(context, boardKeys)
    }



}