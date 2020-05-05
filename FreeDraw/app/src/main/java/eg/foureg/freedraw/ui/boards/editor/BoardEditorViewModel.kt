package eg.foureg.freedraw.ui.boards.editor

import android.graphics.Canvas
import android.graphics.PointF
import androidx.lifecycle.ViewModel
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.data.Shape

class BoardEditorViewModel : ViewModel() {

    fun initBoard(board: Board?) {
        if (board == null) {
            this.board = Board(ArrayList())
        } else {
            this.board = board
        }
    }

    fun initNewShape(pointF: PointF) {
        currentShape = Shape(0x0)

        board.shapes.add(currentShape)
    }

    fun addPointToCurrentShape(pointF: PointF) {

    }

    fun drawBoard(canvas: Canvas) {

    }

    private lateinit var board: Board
    private lateinit var currentShape : Shape
}
