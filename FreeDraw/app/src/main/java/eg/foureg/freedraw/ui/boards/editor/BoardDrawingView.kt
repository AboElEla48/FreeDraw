package eg.foureg.freedraw.ui.boards.editor

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import eg.foureg.freedraw.common.Logger

class BoardDrawingView(context : Context, attrs : AttributeSet) : View(context, attrs) {

    /**
     * Handle touch event to collect shape points
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {

        Logger.debug(TAG, "onTouchEvent --> ${event.actionMasked}, ${event.action}")

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                //Create new Shape
                //(parent as BoardEditorFragment).viewModel.initNewShape(PointF(event.x, event.y))
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                //Add point to Shape
                //(parent as BoardEditorFragment).viewModel.addPointToCurrentShape(PointF(event.x, event.y))
                invalidate()
            }

        }


        return super.onTouchEvent(event)
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDrawForeground(canvas)

        Logger.debug(TAG, "onDraw()")

        //(parent as BoardEditorFragment).viewModel.drawBoard(canvas!!)
    }

    companion object {
        const val TAG : String = "BoardDrawingView"
    }

}