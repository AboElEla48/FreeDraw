package eg.foureg.freedraw.ui.boards.editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import eg.foureg.freedraw.R
import eg.foureg.freedraw.data.Board

class BoardEditorFragment : Fragment() {

    companion object {

        const val BUNDLE_BOARD : String = "BUNDLE_BOARD"

        fun newInstance(board: Board) = BoardEditorFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_BOARD, board)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            board = bundle.getParcelable(BUNDLE_BOARD)!!
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

        viewModel.initBoard(board)
    }


    private lateinit var viewModel: BoardEditorViewModel
    private lateinit var board : Board

}
