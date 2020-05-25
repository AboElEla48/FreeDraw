package eg.foureg.freedraw.model.storage

import android.content.Context
import android.util.JsonReader
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.data.*
import org.json.JSONObject

object StorePreferences {
    const val TAG = "StorePreferences"
    const val SEPARATOR_RECORDS = "||&&*"

    const val PREF_KEY_BOARDS_KEYS = "PREF_KEY_BOARDS_KEYS"
    const val PREF_KEY_BOARD_NAME_KEY_POSTFIX = "_Name"

    fun loadBoardsKeysList(context: Context) : List<String> {
        return loadStringList(context, PREF_KEY_BOARDS_KEYS)
    }

    fun saveBoardsKeysList(context : Context, keys: List<String>) {
        saveStringsList(context, PREF_KEY_BOARDS_KEYS, keys)
    }

    fun loadBoardName(context: Context, boardKey: String) : String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(boardKey + PREF_KEY_BOARD_NAME_KEY_POSTFIX, "")!!
    }

    fun deleteBoards(context: Context, boardKeysToBeDeleted: List<String>) {
        val keys = loadBoardsKeysList(context) as ArrayList<String>
        for(keyToBeDeleted in boardKeysToBeDeleted) {

            // remove key from keys list
            keys.remove(keyToBeDeleted)

            // delete corresponding board
            PreferenceManager.getDefaultSharedPreferences(context).edit().remove(keyToBeDeleted).apply()
        }

        // save keys
        saveBoardsKeysList(context, keys)

    }

    fun saveBoard(context: Context, boardKey: String, board: Board) {
        val gson = Gson()
        val str = gson.toJson(board)
        Logs.debug(TAG, "saveBoard() | $str")

        // Save board JSON
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(boardKey, str).apply()

        // save board name separately
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(boardKey + PREF_KEY_BOARD_NAME_KEY_POSTFIX, board.name).apply()
    }

    fun loadBoard(context: Context, boardKey: String) : Board {
        val gson = Gson()
        val str = PreferenceManager.getDefaultSharedPreferences(context).getString(boardKey, "")!!
        Logs.debug(TAG, "loadBoard() | $str")
        val board = gson.fromJson(str, Board::class.java)

        val jsonObj = JSONObject(str)

        val shapesJSONArr = jsonObj.getJSONArray("shapes")
        for(i in 0 until board.shapes.size) {
            val shapeType = (shapesJSONArr.get(i) as JSONObject).get("shapeType") as String
            Logs.debug(TAG, "loadBoard() | First Shaoe Type: $shapeType")

            when(shapeType) {
                "FreeDraw" -> {
                    board.shapes[i] = gson.fromJson(shapesJSONArr.get(i).toString(), FreeShape::class.java)
                }

                "TextDraw" -> {
                    board.shapes[i] = gson.fromJson(shapesJSONArr.get(i).toString(), TextShape::class.java)
                }

                "CircleDraw" -> {
                    board.shapes[i] = gson.fromJson(shapesJSONArr.get(i).toString(), CircleShape::class.java)
                }

                "RectDraw" -> {
                    board.shapes[i] = gson.fromJson(shapesJSONArr.get(i).toString(), RectShape::class.java)
                }

                "LineDraw" -> {
                    board.shapes[i] = gson.fromJson(shapesJSONArr.get(i).toString(), LineShape::class.java)
                }

                "EraseDraw" -> {
                    board.shapes[i] = gson.fromJson(shapesJSONArr.get(i).toString(), EraseShape::class.java)
                }
            }
        }

        return board
    }

    /**
     * Load String list saved as record in preferences
     */
    private fun loadStringList(context: Context, key: String) : List<String>{
        val strs = ArrayList<String>()

        var prefRecord = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "")!!
        while(prefRecord.length > 0) {
            val index = prefRecord.indexOf(SEPARATOR_RECORDS)

            strs.add(prefRecord.substring(0, index))

            prefRecord = prefRecord.substring(index + SEPARATOR_RECORDS.length)
        }

        return strs
    }

    private fun saveStringsList(context: Context, key : String, strs: List<String>) {
        var prefRecord = ""
        for(itemStr in strs) {
            prefRecord = prefRecord + itemStr + SEPARATOR_RECORDS
        }

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, prefRecord).apply()
    }
}