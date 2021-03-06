package eg.foureg.freedraw.ui.boards.editor.drawerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import eg.foureg.freedraw.common.Logs

class BoardDrawingView(context : Context, attrs : AttributeSet) : View(context, attrs) {

    companion object {
        const val TAG : String = "BoardDrawingView"
    }

    fun initHolderInterface(boardHolder: BoardDrawingViewHolderInt) {
        boardHolderInt = boardHolder
    }

    /**
     * Handle touch event to collect shape points
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {

        Logs.debug(TAG, "onTouchEvent --> ${event.actionMasked}, ${event.action}")

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                //Create new Shape
                boardHolderInt?.initNewShape(PointF(event.x, event.y))
                invalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                //Add point to Shape
                boardHolderInt?.addPointToCurrentShape(PointF(event.x, event.y))
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                boardHolderInt?.finishNewShape()
            }

        }

        return super.onTouchEvent(event)
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDrawForeground(canvas)

        Logs.debug(TAG, "onDraw()")

        boardHolderInt?.drawBoard(canvas!!)
    }

    private var boardHolderInt: BoardDrawingViewHolderInt? = null

}