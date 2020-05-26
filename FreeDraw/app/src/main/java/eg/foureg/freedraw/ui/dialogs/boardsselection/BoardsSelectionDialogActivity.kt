package eg.foureg.freedraw.ui.dialogs.boardsselection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.ui.BaseActorActivity
import kotlinx.android.synthetic.main.activity_boards_selection_dialog.*

class BoardsSelectionDialogActivity : BaseActorActivity() {

    companion object {
        const val TAG = "BoardsSelectionDialogAct"
        const val SELECTION_RESULT = 101
        const val SELECTION_RESULT_Param = "Boards_Selection_Result_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boards_selection_dialog)

        viewModel = ViewModelProvider(this).get(BoardsSelectionDialogViewModel::class.java)

        viewModel.notifyLoadingItemsFinished.observe(this, Observer {
            setListAdapter()
        })

        boards_selection_list_view.layoutManager = LinearLayoutManager(this)

        viewModel.initViewModel(this)
    }

    private fun setListAdapter() {
        val adapter = BoardsSelectionRecyclerAdapter(this, viewModel.boardsNamesList, this)
        boards_selection_list_view.adapter = adapter
    }

    fun itemSelected(position: Int) {
        Logs.debug(TAG, "Board selected key: ${viewModel.boardsKeysList[position]}")
        val intent = Intent()
        intent.putExtra(SELECTION_RESULT_Param, viewModel.boardsKeysList[position])
        setResult(SELECTION_RESULT, intent)

        finish()
    }

    private lateinit var viewModel : BoardsSelectionDialogViewModel
}
