package eg.foureg.freedraw.ui.boards.editor.motionview

import android.graphics.PointF

interface BoardMotionViewHolderInt {

    fun moveShapeTo(pointF: PointF)

    fun finishShapeMotion()
}