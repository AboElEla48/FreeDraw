package eg.foureg.freedraw.ui.boards.editor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.data.FreeShape
import eg.foureg.freedraw.data.Shape
import eg.foureg.freedraw.data.ShapeType
import eg.foureg.freedraw.drawing.drawShape
import eg.foureg.freedraw.model.BoardsModel
import kotlinx.coroutines.launch

class BoardEditorViewModel : ViewModel() {

    companion object {
        const val TAG = "BoardEditorViewModel"
    }

    /**
     * init board for editing whether it is a new board or old one to edit
     */
    fun initBoard(context: Context, board: Board?) {
        Logs.debug(TAG, "initBoard($board)")

        this.context = context

        if (board == null) {
            Logs.debug(TAG, "This is a new board")

            val newBoardKey = boardModel.generateNewBoardKey(context)
            Logs.debug(TAG, "New Board Key: $newBoardKey")

            this.board = Board(newBoardKey, context.getString(R.string.txt_default_new_board_name), ArrayList())

        } else {

            Logs.debug(TAG, "This is existing board")
            this.board = board

        }

        // initial drawing type
        shapeType.value = ShapeType.FreeDraw
    }

    /**
     * User is drawing. Create new shape in this board
     */
    fun initNewShape(pointF: PointF) {
        Logs.debug(TAG, "initNewShape(${pointF.x}, ${pointF.y})")
        currentShape = FreeShape(points = ArrayList(), shapeColor = Color. BLACK)

        board.shapes.add(currentShape)
    }

    /**
     * Add points to current shape
     */
    fun addPointToCurrentShape(pointF: PointF) {
        Logs.debug(TAG, "addPointToCurrentShape(${pointF.x}, ${pointF.y})")

        when(shapeType.value) {
            ShapeType.FreeDraw -> {
                (currentShape as FreeShape).points.add(pointF)
            }
            else -> {}
        }
    }

    /**
     * draw board shapes on given canvas
     */
    fun drawBoard(canvas: Canvas) {
        Logs.debug(TAG, "drawBoard()")

        viewModelScope.launch {
            for( i in 0 until board.shapes.size) {
                drawShape(canvas, board.shapes[i])
            }
        }
    }

    fun saveBoard() {
        Logs.debug(TAG, "saveBoard()")
        viewModelScope.launch {
            boardModel.saveBoard(context, board)
        }

    }

    lateinit var board: Board
    private lateinit var context: Context
    private lateinit var currentShape : Shape
    private val boardModel = BoardsModel()

    var shapeType : MutableLiveData<ShapeType> = MutableLiveData()
}
