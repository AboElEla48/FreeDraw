package eg.foureg.freedraw.model.storage

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import eg.foureg.freedraw.common.Logger
import eg.foureg.freedraw.data.Board

object StorePreferences {
    const val TAG = "StorePreferences"
    const val SEPARATOR_RECORDS = "||&&*"

    const val PREF_KEY_BOARDS_KEYS = "PREF_KEY_BOARDS_KEYS"
    const val PREF_KEY_BOARD_NAME_KEY_POSTFIX = "_Name"

    fun loadBoardsKeysList(context: Context) : List<String> {
        return loadStringList(context, PREF_KEY_BOARDS_KEYS)
    }

    fun loadBoardName(context: Context, boardKey: String) : String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(boardKey + PREF_KEY_BOARD_NAME_KEY_POSTFIX, "")!!
    }

    fun saveBoard(context: Context, boardKey: String, board: Board) {
        val gson = Gson()
        val str = gson.toJson(board)
        Logger.debug(TAG, "saveBoard() | $str")
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(boardKey, str).apply()

        // save board name separately
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(boardKey + PREF_KEY_BOARD_NAME_KEY_POSTFIX, board.name).apply()
    }

    fun loadBoard(context: Context, boardKey: String) : Board {
        val gson = Gson()
        val str = PreferenceManager.getDefaultSharedPreferences(context).getString(boardKey, "")!!
        Logger.debug(TAG, "loadBoard() | $str")
        return gson.fromJson(str, Board::class.java)
    }

    /**
     * Load String list saved as record in preferences
     */
    private fun loadStringList(context: Context, key:String) : List<String>{
        val strs = ArrayList<String>()

        var prefRecord = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "")!!
        while(prefRecord.length > 0) {
            val index = prefRecord.indexOf(SEPARATOR_RECORDS)

            strs.add(prefRecord.substring(0, index))

            prefRecord = prefRecord.substring(index + SEPARATOR_RECORDS.length)
        }

        return strs
    }
}