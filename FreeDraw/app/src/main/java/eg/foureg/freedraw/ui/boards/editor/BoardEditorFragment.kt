package eg.foureg.freedraw.ui.boards.editor

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.PointF
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessage
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.*
import eg.foureg.freedraw.features.export.ImageExport
import eg.foureg.freedraw.model.DrawingToolsModel
import eg.foureg.freedraw.ui.BaseActorFragment
import eg.foureg.freedraw.ui.MainActivity
import eg.foureg.freedraw.ui.boards.editor.drawerview.BoardDrawingViewHolderInt
import eg.foureg.freedraw.ui.dialogs.boardname.BoardNameInputDialog
import eg.foureg.freedraw.ui.dialogs.confirmation.ClearBoardConfirmationDialog
import eg.foureg.freedraw.ui.dialogs.tools.ToolsDialogActivity
import kotlinx.android.synthetic.main.board_editor_fragment.*

class BoardEditorFragment : BaseActorFragment(),
    BoardDrawingViewHolderInt {

    companion object {

        const val BUNDLE_BOARD: String = "BUNDLE_BOARD"
        const val Request_code_TOOLS_DLG = 1

        fun newInstance(board: Board?) = BoardEditorFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_BOARD, board)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let { bundle ->
            board = bundle.getParcelable(BUNDLE_BOARD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.board_editor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoardEditorViewModel::class.java)

        // init holder interface
        board_editor_drawing_view.initHolderInterface(this)

        viewModel.invalidateScreen.observe(viewLifecycleOwner, Observer { invalidateView ->
            if (invalidateView) {
                board_editor_drawing_view.invalidate()
                viewModel.invalidateScreen.value = false
            }
        })

        viewModel.motionViewVisibility.observe(viewLifecycleOwner, Observer { visibility ->
            board_editor_motion_view.visibility = visibility
            board_editor_motion_view.initShape(viewModel.currentShape as TextShape)
        })

        // init board
        viewModel.initBoard(activity as Context, board)
        updateActionBarTitle()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_editor_board, menu)
    }

//    override fun onPrepareOptionsMenu(menu: Menu) {
//        super.onPrepareOptionsMenu(menu)
//        if(!viewModel.isUndoPossible()) {
//            menu.removeItem(R.id.menu_fragment_editor_undo_board)
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fragment_editor_new_board -> {
                if (viewModel.isBoardSaved) {
                    initEmptyBoard()
                } else {
                    shouldOpenNewBoard = true
                    trySaveBoard()
                }
            }

            R.id.menu_fragment_editor_clear_board -> {
                ClearBoardConfirmationDialog.createDialog(activity as Context).show()
            }

            R.id.menu_fragment_editor_erase_board -> {
                viewModel.startEraser()
            }

            R.id.menu_fragment_editor_undo_board -> {
                viewModel.undo()
            }

            R.id.menu_fragment_editor_tools_board -> {
                showToolsDialog()
            }

            R.id.menu_fragment_editor_save_board -> {
                trySaveBoard()
            }

            R.id.menu_fragment_editor_export_board -> {
                ImageExport.exportViewAsImage(activity as Context, board_editor_drawing_view)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateActionBarTitle() {
        if (board == null) {
            (activity as MainActivity).updateActionBarTitle(getString(R.string.txt_default_new_board_name))
            boardHasName = false
        } else {
            (activity as MainActivity).updateActionBarTitle(board!!.name)
            boardHasName = true
        }
    }

    private fun initEmptyBoard() {
        shouldOpenNewBoard = false
        board = null
        viewModel.initBoard(activity as Context, board)
        updateActionBarTitle()

        board_editor_drawing_view.invalidate()
    }

    private fun showToolsDialog() {
        val intent = Intent(activity, ToolsDialogActivity::class.java)
        startActivityForResult(intent, Request_code_TOOLS_DLG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Request_code_TOOLS_DLG -> {
                if (DrawingToolsModel.drawingShapeType == ShapeType.TextDraw) {
                    viewModel.initTextShape()
                }
            }
        }
    }

    private fun trySaveBoard() {
        if (boardHasName) {
            viewModel.saveBoard()
        } else {
            // Show dialog to rename the board
            BoardNameInputDialog.createDialog(activity as Context).show()
        }
    }

    override fun initNewShape(pointF: PointF) {
        viewModel.initNewShape(pointF)
    }

    override fun addPointToCurrentShape(pointF: PointF) {
        viewModel.addPointToCurrentShape(pointF)
    }

    override fun drawBoard(canvas: Canvas) {
        viewModel.drawBoard(canvas)
    }

    private fun setBoardName(name: String) {
        viewModel.board.name = name
        boardHasName = true

        (activity as MainActivity).updateActionBarTitle(name)

        viewModel.saveBoard()

        if (shouldOpenNewBoard) {
            //init new board after saving
            initEmptyBoard()
        }

    }

    fun clearBoard() {
        viewModel.clearBoard()
        board_editor_drawing_view.invalidate()
    }

    override fun handleMessage(message: ActorMessage) {
        super.handleMessage(message)

        when (message.what) {
            // Navigate back to list fragment
            messageBackToFragmentID -> {
                ActorMessageDispatcher.sendMessage(
                    MainActivity::class.java,
                    messageNavigateToBoardsListFragment
                )
            }

            // Move shape
            messageEditBoardMoveShapeID -> {
                val point: PointF = message.msg[messageEditBoardMoveShapeParam] as PointF
                viewModel.moveShapeTo(point)
            }

            // Finish Move shape
            messageEditBoardFinishMoveShapeID -> {
                viewModel.finishShapeMotion()
            }

            // Invalidate draw
            messageEditBoardInvalidateDrawID -> {
                board_editor_drawing_view.invalidate()
            }

            // set board name
            messageEditBoardSetBoardNameID -> {
                val boardName = message.msg[messageEditBoardSetBoardNameParam] as String
                setBoardName(boardName)
            }

            messageEditBoardClearID -> {
                clearBoard()
            }
        }
    }


    private lateinit var viewModel: BoardEditorViewModel
    var boardHasName: Boolean = false
    private var shouldOpenNewBoard = false
    private var board: Board? = null

}
