package eg.foureg.freedraw.ui.boards.editor

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.Logs
import eg.foureg.freedraw.data.*
import eg.foureg.freedraw.features.drawing.drawShape
import eg.foureg.freedraw.model.BoardsModel
import eg.foureg.freedraw.model.DrawingToolsModel
import eg.foureg.freedraw.model.UndoActionModel
import eg.foureg.freedraw.ui.dialogs.textdrawmsg.TextDrawMessageInputDialog
import kotlinx.coroutines.delay
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

        isBoardSaved = true
        invalidateScreen.value = false
        DrawingToolsModel.drawingShapeType = ShapeType.FreeDraw

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
    }


    /**
     * User is drawing. Create new shape in this board
     */
    fun initNewShape(startPoint: PointF) {
        Logs.debug(TAG, "initNewShape(${startPoint.x}, ${startPoint.y})")
        isBoardSaved = false

        when(DrawingToolsModel.drawingShapeType) {
            ShapeType.FreeDraw -> {
                currentShape = FreeShape(ArrayList(), DrawingToolsModel.drawingColor)
                board.shapes.add(currentShape)
                undoActionModel.actionsStack.push(currentShape)
                addPointToCurrentShape(startPoint)
            }

            ShapeType.CircleDraw -> {
                currentShape = CircleShape(startPoint, startPoint, DrawingToolsModel.drawingColor, DrawingToolsModel.fillingColor)
                board.shapes.add(currentShape)
                undoActionModel.actionsStack.push(currentShape)
                addPointToCurrentShape(startPoint)
            }

            ShapeType.RectDraw -> {
                currentShape = RectShape(startPoint, startPoint, DrawingToolsModel.drawingColor, DrawingToolsModel.fillingColor)
                board.shapes.add(currentShape)
                undoActionModel.actionsStack.push(currentShape)
                addPointToCurrentShape(startPoint)
            }

            ShapeType.LineDraw -> {
                currentShape = LineShape(startPoint, startPoint, DrawingToolsModel.drawingColor)
                board.shapes.add(currentShape)
                undoActionModel.actionsStack.push(currentShape)
                addPointToCurrentShape(startPoint)
            }

            ShapeType.EraseDraw -> {
                currentShape = EraseShape(ArrayList())
                board.shapes.add(currentShape)
                undoActionModel.actionsStack.push(currentShape)
                addPointToCurrentShape(startPoint)
            }

            else -> {}
        }
    }

    /**
     * Add points to current shape
     */
    fun addPointToCurrentShape(pointF: PointF) {
        Logs.debug(TAG, "addPointToCurrentShape(${pointF.x}, ${pointF.y})")

        when(DrawingToolsModel.drawingShapeType) {
            ShapeType.FreeDraw -> {
                (currentShape as FreeShape).points.add(pointF)
            }
            ShapeType.CircleDraw -> {
                (currentShape as CircleShape).rightBottomPoint = pointF
            }

            ShapeType.RectDraw -> {
                (currentShape as RectShape).rightBottomPoint = pointF
            }

            ShapeType.LineDraw -> {
                (currentShape as LineShape).endPoint = pointF
            }

            ShapeType.EraseDraw -> {
                (currentShape as EraseShape).erasePoints.add(pointF)
            }

            else -> {}
        }
    }

    fun initTextShape() {
        // check if this is text drawing, show the input text to get the string message
        Logs.debug(TAG, " initTextShape() | get drawing string from user")

        DrawingToolsModel.drawingText = null

        // Show dialog to let user enter the string he would like to draw
        val inputJob = viewModelScope.launch {
            TextDrawMessageInputDialog.createDialog(context).show()
            while (DrawingToolsModel.drawingText == null) {
                delay(100)
            }
        }

        // Draw string if user entered value to draw
        viewModelScope.launch {
            Logs.debug(TAG, " initTextShape() | Wait till user enter the name")
            inputJob.join()
            Logs.debug(TAG, " initTextShape() | Text Shape String: ( ${DrawingToolsModel.drawingText} )")

            //Assure user entered value to draw
            if(DrawingToolsModel.drawingText?.length!! > 0) {
                currentShape = TextShape(DrawingToolsModel.drawingText!!, PointF(120F, 120F), 70F, DrawingToolsModel.drawingColor)
                board.shapes.add(currentShape)
                undoActionModel.actionsStack.push(currentShape)
                invalidateScreen.value = true

                motionViewVisibility.value = View.VISIBLE
            }

        }
    }

    fun moveShapeTo(pointF: PointF) {
        Logs.debug(TAG, "moveShapeTo() | Point = (${pointF.x}, ${pointF.y})")

        if(currentShape.shapeType == ShapeType.TextDraw) {
            Logs.debug(TAG, "moveShapeTo() | Move text shape to point")
            (currentShape as TextShape).topLeftPoint = pointF
            invalidateScreen.value = true
        }
    }

    fun finishShapeMotion() {
        motionViewVisibility.value = View.GONE
        DrawingToolsModel.drawingShapeType = ShapeType.FreeDraw
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

    fun clearBoard() {
        Logs.debug(TAG, "clearBoard()")
        board.shapes.clear()
        isBoardSaved = false
    }

    fun startEraser() {
        DrawingToolsModel.drawingShapeType = ShapeType.EraseDraw
    }

    fun isUndoPossible() : Boolean{
        return undoActionModel.actionsStack.size > 0
    }

    fun undo() {
        // Undo last action
        if(!undoActionModel.actionsStack.empty()) {
            val action = undoActionModel.actionsStack.pop()
            board.shapes.remove(action)

            invalidateScreen.value = true
            if(motionViewVisibility.value == View.VISIBLE) {
                finishShapeMotion()
            }
        }
    }

    fun insertBoard(boardKey: String) {
        Logs.debug(TAG, "insertBoard($boardKey)")

        viewModelScope.launch {
            val boardToInsert = boardModel.loadBoard(context, boardKey)

            // Add all shapes to current shape
            board.shapes.addAll(boardToInsert.shapes)

            invalidateScreen.value = true
        }
    }

    fun saveBoard() {
        Logs.debug(TAG, "saveBoard()")
        viewModelScope.launch {
            boardModel.saveBoard(context, board)
            isBoardSaved = true
        }

    }

    lateinit var board: Board
    private lateinit var context: Context
    var isBoardSaved = true
    lateinit var currentShape : Shape

    private val boardModel = BoardsModel()
    private val undoActionModel = UndoActionModel()

    val invalidateScreen = MutableLiveData<Boolean>()
    val motionViewVisibility = MutableLiveData<Int>()

}
