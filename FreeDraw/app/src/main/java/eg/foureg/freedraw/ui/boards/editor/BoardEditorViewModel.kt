package eg.foureg.freedraw.ui.boards.editor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.freedraw.common.Logger
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.data.FreeShape
import eg.foureg.freedraw.data.Shape
import eg.foureg.freedraw.data.ShapeType
import eg.foureg.freedraw.drawing.drawShape
import kotlinx.coroutines.launch

class BoardEditorViewModel : ViewModel() {

    companion object {
        const val TAG = "BoardEditorViewModel"
    }

    /**
     * init board for editing whether it is a new board or old one to edit
     */
    fun initBoard(board: Board?) {
        if (board == null) {
            this.board = Board(ArrayList())
        } else {
            this.board = board
        }

        // initial drawing type
        shapeType.value = ShapeType.FreeDraw
    }

    /**
     * User is drawing. Create new shape in this board
     */
    fun initNewShape(pointF: PointF) {
        Logger.debug(TAG, "initNewShape(${pointF.x}, ${pointF.y})")
        currentShape = FreeShape(points = ArrayList(), shapeColor = Color. BLACK)

        board.shapes.add(currentShape)
    }

    /**
     * Add points to current shape
     */
    fun addPointToCurrentShape(pointF: PointF) {
        Logger.debug(TAG, "addPointToCurrentShape(${pointF.x}, ${pointF.y})")

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
        Logger.debug(TAG, "drawBoard()")

        viewModelScope.launch {
            for( i in 0 until board.shapes.size) {
                drawShape(canvas, board.shapes[i])
            }
        }
    }

    private lateinit var board: Board
    private lateinit var currentShape : Shape
    var shapeType : MutableLiveData<ShapeType> = MutableLiveData()
}
