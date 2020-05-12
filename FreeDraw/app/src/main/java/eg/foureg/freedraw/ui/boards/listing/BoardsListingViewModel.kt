package eg.foureg.freedraw.ui.boards.listing

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.messageNavigateToEditBoardFragment
import eg.foureg.freedraw.data.messageNavigateToEditBoardFragmentMap
import eg.foureg.freedraw.data.messageNavigateToEditBoardParam
import eg.foureg.freedraw.model.BoardsModel
import eg.foureg.freedraw.ui.MainActivity
import kotlinx.coroutines.launch

class BoardsListingViewModel : ViewModel() {

    companion object {
        const val TAG = "BoardsListingViewModel"
    }

    fun initViewModel(context: Context) {
        this.context = context
        boardsKeysList = boardsModel.loadBoardsKeys(context) as ArrayList<String>

        viewModelScope.launch {

            for(key in boardsKeysList) {
                val name = boardsModel.loadBoardName(context, key)
                boardsNamesList.add(name)
                Logs.debug(TAG, "initViewModel() | Key=$key, Name=$name")
            }

            notifyLoadingItemsFinished.value = true
        }
    }

    fun selectItem(position: Int) {
        // get selected board
        val selectedBoard = boardsModel.loadBoard(context, boardsKeysList.get(position))
        messageNavigateToEditBoardFragmentMap.clear()
        messageNavigateToEditBoardFragmentMap.put(messageNavigateToEditBoardParam, selectedBoard)

        ActorMessageDispatcher.sendMessage(MainActivity::class.java, messageNavigateToEditBoardFragment)
    }

    private lateinit var context : Context
    lateinit var boardsKeysList: ArrayList<String>
    val boardsNamesList = ArrayList<String>()
    val notifyLoadingItemsFinished: MutableLiveData<Boolean> = MutableLiveData()
    private val boardsModel = BoardsModel()
}
