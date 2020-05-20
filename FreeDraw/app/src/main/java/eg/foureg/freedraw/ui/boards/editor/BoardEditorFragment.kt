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
import eg.foureg.freedraw.ui.dialogs.boardname.BoardNameInputDialogInt
import eg.foureg.freedraw.ui.dialogs.confirmation.ClearBoardConfirmationDialog
import eg.foureg.freedraw.ui.dialogs.confirmation.ClearBoardDialogInt
import eg.foureg.freedraw.ui.dialogs.tools.ToolsDialogActivity
import kotlinx.android.synthetic.main.board_editor_fragment.*

class BoardEditorFragment : BaseActorFragment(),
    BoardDrawingViewHolderInt,
    BoardNameInputDialogInt,
    ClearBoardDialogInt {

    companion object {

        const val BUNDLE_BOARD : String = "BUNDLE_BOARD"
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

        viewModel.invalidateScreen.observe(viewLifecycleOwner, Observer {invalidateView ->
            if(invalidateView) {
                board_editor_drawing_view.invalidate()
                viewModel.invalidateScreen.value = false
            }
        })

        viewModel.motionViewVisibility.observe(viewLifecycleOwner, Observer { visibility ->
            board_editor_motion_view.visibility = visibility
        })

        // init board
        viewModel.initBoard(activity as Context, board)
        updateActionBarTitle()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_editor_board, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lastMenuItemClickedID = item.itemId

        when(item.itemId) {
            R.id.menu_fragment_editor_new_board -> {
                if(viewModel.isBoardSaved) {
                    initEmptyBoard()
                } else {
                    trySaveBoard()
                }
            }

            R.id.menu_fragment_editor_clear_board -> {
                ClearBoardConfirmationDialog.createDialog(activity as Context, this).show()
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

    private fun updateActionBarTitle()
    {
        if(board == null) {
            (activity as MainActivity).updateActionBarTitle(getString(R.string.txt_default_new_board_name))
            boardHasName = false
        }
        else {
            (activity as MainActivity).updateActionBarTitle(board!!.name)
            boardHasName = true
        }
    }

    private fun initEmptyBoard() {
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
        when(requestCode) {
            Request_code_TOOLS_DLG -> {
                if(DrawingToolsModel.drawingShapeType == ShapeType.TextDraw) {
                    viewModel.initTextShape()
                }
            }
        }
    }

    private fun trySaveBoard() {
        if(boardHasName){
            viewModel.saveBoard()
        } else {
            // Show dialog to rename the board
            BoardNameInputDialog.createDialog(activity as Context, this).show()
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

    override fun boardNameDialogPositiveAction(name:String) {
        viewModel.board.name = name
        boardHasName = true

        (activity as MainActivity).updateActionBarTitle(name)

        viewModel.saveBoard()

        if(lastMenuItemClickedID == R.id.menu_fragment_editor_new_board) {
            //init new board after saving
            initEmptyBoard()
        }
    }

    override fun boardNameDialogNegativeAction() {
    }

    override fun clearBoardConfirmed() {
        viewModel.clearBoard()
        board_editor_drawing_view.invalidate()
    }

    override fun handleMessage(message: ActorMessage) {
        super.handleMessage(message)

        when(message.what) {
            messageBackToFragmentID -> {
                ActorMessageDispatcher.sendMessage(MainActivity::class.java, messageNavigateToBoardsListFragment)
            }

            messageEditBoardMoveShapeID -> {
                val point : PointF = message.msg[messageEditBoardMoveShapeParam] as PointF
                viewModel.moveShapeTo(point)
            }

            messageEditBoardFinishMoveShapeID -> {
                viewModel.finishShapeMotion()
            }
        }
    }


    private lateinit var viewModel: BoardEditorViewModel
    var boardHasName : Boolean = false
    private var lastMenuItemClickedID = -1
    private var board : Board? = null

}
