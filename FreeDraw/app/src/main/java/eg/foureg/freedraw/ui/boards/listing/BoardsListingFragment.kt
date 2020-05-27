package eg.foureg.freedraw.ui.boards.listing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessage
import eg.foureg.freedraw.data.messageDeleteBoardsFromListingID
import eg.foureg.freedraw.ui.BaseActorFragment
import eg.foureg.freedraw.ui.MainActivity
import eg.foureg.freedraw.ui.dialogs.confirmation.DeleteBoardsConfirmationDialog
import kotlinx.android.synthetic.main.boards_listing_fragment.*

class BoardsListingFragment : BaseActorFragment() {

    companion object {
        fun newInstance() = BoardsListingFragment()
    }

    private lateinit var viewModel: BoardsListingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.boards_listing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).updateActionBarTitle(getString(R.string.app_name))

        viewModel = ViewModelProvider(this).get(BoardsListingViewModel::class.java)

        viewModel.notifyLoadingItemsFinished.observe(viewLifecycleOwner, Observer {
            setListAdapter()
        })

        viewModel.emptyItemsTextVisibility.observe(viewLifecycleOwner, Observer {visibility->
            boards_listing_empty_items_text_view.visibility = visibility
        })

        boards_listing_list_view.layoutManager = LinearLayoutManager(activity)

        boards_listing_open_editor_btn.setOnClickListener {
            (activity as MainActivity).navigator.navigateToBoardEditorFragment(activity as MainActivity, null)
        }

        boards_listing_selection_delete_items_btn.setOnClickListener {
            DeleteBoardsConfirmationDialog.createDialog(activity as Context).show()
        }

        boards_listing_selection_cancel_selection_btn.setOnClickListener {
            cancelSelectionMode()
        }

        viewModel.initViewModel(activity as Context)

    }

    private fun setListAdapter() {
        val adapter = BoardsRecyclerAdapter(activity as Context, viewModel.boardsNamesList, this, isSelectionMode)
        boards_listing_list_view.adapter = adapter
    }

    fun itemClicked(pos : Int) {
        viewModel.openBoard(pos)
    }

    fun showSelectionMode() {
        isSelectionMode = true
        setListAdapter()

        boards_listing_selection_layout.visibility = View.VISIBLE
        boards_listing_open_editor_btn.visibility = View.GONE
    }

    private fun cancelSelectionMode() {
        boards_listing_selection_layout.visibility = View.GONE
        boards_listing_open_editor_btn.visibility = View.VISIBLE

        isSelectionMode = false
        setListAdapter()
    }

    override fun handleMessage(message: ActorMessage) {
        super.handleMessage(message)

        when(message.what) {
            messageDeleteBoardsFromListingID -> {
                viewModel.deleteBoards((boards_listing_list_view.adapter as BoardsRecyclerAdapter).selectionIndicesList)
                cancelSelectionMode()
            }
        }
    }

    private var isSelectionMode = false

}
