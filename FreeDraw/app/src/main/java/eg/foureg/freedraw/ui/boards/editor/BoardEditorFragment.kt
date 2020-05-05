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
import kotlinx.android.synthetic.main.board_editor_fragment.*

class BoardEditorFragment : Fragment(), BoardDrawingViewHolderInt {

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_editor_board, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_fragment_editor_board -> {
                viewModel.saveBoard()
            }
        }
        return super.onOptionsItemSelected(item)
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


    private lateinit var viewModel: BoardEditorViewModel
    private var board : Board? = null


}
