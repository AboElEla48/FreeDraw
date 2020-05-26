package eg.foureg.freedraw.ui.boards.listing

import android.content.Context
import android.view.View
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

            if(boardsKeysList.isEmpty()) {
                emptyItemsTextVisibility.value = View.VISIBLE
            } else {
                emptyItemsTextVisibility.value = View.GONE
            }

            notifyLoadingItemsFinished.value = true
        }
    }

    fun openBoard(position: Int) {
        // get selected board
        val selectedBoard = boardsModel.loadBoard(context, boardsKeysList.get(position))
        messageNavigateToEditBoardFragmentMap.clear()
        messageNavigateToEditBoardFragmentMap.put(messageNavigateToEditBoardParam, selectedBoard)

        ActorMessageDispatcher.sendMessage(MainActivity::class.java, messageNavigateToEditBoardFragment)
    }

    fun deleteBoards(boardsIndices : List<Int>) {
        val keysToBeDeleted = ArrayList<String>()

        // Collect all keys to be deleted
        for(pos in boardsIndices) {
            keysToBeDeleted.add(boardsKeysList.get(pos))
        }

        val deleteJob = viewModelScope.launch {
            boardsModel.deleteBoards(context, keysToBeDeleted)
        }

        viewModelScope.launch {
            deleteJob.join()
            loadBoards()
        }

    }

    private lateinit var context : Context
    lateinit var boardsKeysList: ArrayList<String>
    val boardsNamesList = ArrayList<String>()

    val emptyItemsTextVisibility: MutableLiveData<Int> = MutableLiveData()
    val notifyLoadingItemsFinished: MutableLiveData<Boolean> = MutableLiveData()

    private val boardsModel = BoardsModel()
}
