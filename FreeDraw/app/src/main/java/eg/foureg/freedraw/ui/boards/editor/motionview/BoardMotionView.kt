package eg.foureg.freedraw.ui.boards.editor.motionview

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.*
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment
import kotlinx.android.synthetic.main.view_motion_frame.view.*

class BoardMotionView(context : Context, attrs : AttributeSet) : FrameLayout(context, attrs) {
    companion object {
        const val TAG : String = "BoardDrawingView"
    }

    init {
        setBackgroundColor(Color.TRANSPARENT)
        addView(LayoutInflater.from(context).inflate(R.layout.view_motion_frame, null))

        view_motion_apply_text_size_btn.setOnClickListener {
            currentShape?.textSize = view_motion_text_size_edit_text.text.toString().toFloat()
            ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageEditBoardInvalidateDraw)
        }

        view_motion_finish_btn.setOnClickListener {
            finishMotion()
        }
    }

    fun initShape(shape: TextShape) {
        currentShape = shape
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_MOVE -> {
                //Add point to Shape
                moveShapeTo(PointF(event.x, event.y))
                return true
            }

        }

        return true
    }

    private fun moveShapeTo(pointF: PointF) {
        messageEditBoardMoveShapeMap[messageEditBoardMoveShapeParam] = pointF
        ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageEditBoardMoveShape)
    }

    private fun finishMotion() {
        ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageEditBoardFinishMoveShape)
    }

    var currentShape : TextShape? = null
}