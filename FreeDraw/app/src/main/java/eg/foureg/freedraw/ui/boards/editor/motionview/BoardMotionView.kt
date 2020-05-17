package eg.foureg.freedraw.ui.boards.editor.motionview

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BoardMotionView(context : Context, attrs : AttributeSet) : View(context, attrs) {
    companion object {
        const val TAG : String = "BoardDrawingView"
    }

    init {
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun initHolderInterface(boardHolder: BoardMotionViewHolderInt) {
        boardHolderInt = boardHolder
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_MOVE -> {
                //Add point to Shape
                boardHolderInt?.moveShapeTo(PointF(event.x, event.y))
//                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                boardHolderInt?.finishShapeMotion()
//                invalidate()
                return true
            }

        }

        return true
    }

    private var boardHolderInt: BoardMotionViewHolderInt? = null
}