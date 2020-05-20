package eg.foureg.freedraw.ui.boards.editor.motionview

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import eg.foureg.freedraw.R
import kotlinx.android.synthetic.main.view_motion_frame.view.*

class BoardMotionView(context : Context, attrs : AttributeSet) : FrameLayout(context, attrs) {
    companion object {
        const val TAG : String = "BoardDrawingView"
    }

    init {
        setBackgroundColor(Color.TRANSPARENT)
        addView(LayoutInflater.from(context).inflate(R.layout.view_motion_frame, null))

        view_motion_save_btn.setOnClickListener {
            boardHolderInt?.finishShapeMotion()
        }
    }

    fun initHolderInterface(boardHolder: BoardMotionViewHolderInt) {
        boardHolderInt = boardHolder
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_MOVE -> {
                //Add point to Shape
                boardHolderInt?.moveShapeTo(PointF(event.x, event.y))
                return true
            }

        }

        return true
    }

    private var boardHolderInt: BoardMotionViewHolderInt? = null
}