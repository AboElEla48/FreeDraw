package eg.foureg.freedraw.ui.dialogs.boardsselection

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.model.BoardsModel
import kotlinx.coroutines.launch

class BoardsSelectionDialogViewModel : ViewModel() {

    companion object {
        const val TAG = "BoardsSelectionDialogViewModel"
    }

    fun initViewModel(context: Context) {
        this.context = context
        loadBoards()
    }

    private fun loadBoards() {
        boardsKeysList = boardsModel.loadBoardsKeys(context) as ArrayList<String>
        boardsNamesList.clear()

        viewModelScope.launch {

            for(key in boardsKeysList) {
                val name = boardsModel.loadBoardName(context, key)
                boardsNamesList.add(name)
                Logs.debug(TAG, "initViewModel() | Key=$key, Name=$name")
            }

            notifyLoadingItemsFinished.value = true
        }
    }

    private lateinit var context : Context
    lateinit var boardsKeysList: ArrayList<String>
    val boardsNamesList = ArrayList<String>()

    val notifyLoadingItemsFinished: MutableLiveData<Boolean> = MutableLiveData()

    private val boardsModel = BoardsModel()
}