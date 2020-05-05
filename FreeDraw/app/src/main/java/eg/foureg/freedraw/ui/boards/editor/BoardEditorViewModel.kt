package eg.foureg.freedraw.ui.boards.editor

import android.graphics.Canvas
import android.graphics.PointF
import androidx.lifecycle.ViewModel
import eg.foureg.freedraw.common.Logger
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.data.Shape

class BoardEditorViewModel : ViewModel() {

    companion object {
        const val TAG = "BoardEditorViewModel"
    }

    fun initBoard(board: Board?) {
        if (board == null) {
            this.board = Board(ArrayList())
        } else {
            this.board = board
        }
    }

    fun initNewShape(pointF: PointF) {
        Logger.debug(TAG, "initNewShape(${pointF.x}, ${pointF.y})")
        currentShape = Shape(0x0)

        board.shapes.add(currentShape)
    }

    fun addPointToCurrentShape(pointF: PointF) {
        Logger.debug(TAG, "addPointToCurrentShape(${pointF.x}, ${pointF.y})")
    }

    fun drawBoard(canvas: Canvas) {
        Logger.debug(TAG, "drawBoard()")
    }

    private lateinit var board: Board
    private lateinit var currentShape : Shape
}
