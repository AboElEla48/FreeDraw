package eg.foureg.freedraw.ui.boards.editor

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import eg.foureg.freedraw.R
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.ui.MainActivity
import eg.foureg.freedraw.ui.boards.editor.namedialog.BoardNameInputDialog
import eg.foureg.freedraw.ui.boards.editor.namedialog.BoardNameInputDialogInt
import kotlinx.android.synthetic.main.board_editor_fragment.*

class BoardEditorFragment : Fragment(), BoardDrawingViewHolderInt, BoardNameInputDialogInt {

    companion object {

        const val BUNDLE_BOARD : String = "BUNDLE_BOARD"

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
        boards_editor_drawing_view.initHolderInterface(this)

        // init board
        viewModel.initBoard(activity as Context, board)
        if(board == null) {
            (activity as MainActivity).updateActionBarTitle(getString(R.string.txt_default_new_board_name))
        }
        else {
            (activity as MainActivity).updateActionBarTitle(board!!.name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_editor_board, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_fragment_editor_board -> {
                if(viewModel.boardHasName){
                    viewModel.saveBoard()
                } else {
                    // Show dialog to rename the board
                    BoardNameInputDialog.createDialog(activity as Context, this).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
        viewModel.boardHasName = true

        (activity as MainActivity).updateActionBarTitle(name)

        viewModel.saveBoard()
    }

    override fun boardNameDialogNegativeAction() {

    }


    private lateinit var viewModel: BoardEditorViewModel
    private var board : Board? = null


}
