package eg.foureg.freedraw.ui.boards.editor.drawerview

import android.graphics.Canvas
import android.graphics.PointF

/**
 * This interface to provide the holder methods needed from custom drawing view from parent fragment
 */
interface BoardDrawingViewHolderInt {
    /**
     * Start drawing new shape
     */
    fun initNewShape(pointF: PointF)

    /**
     * Add this point to shape
     */
    fun addPointToCurrentShape(pointF: PointF)

    /**
     * Call to notify that user motion up event
     */
    fun finishNewShape()

    /**
     * draw all shapes in the board
     */
    fun drawBoard(canvas: Canvas)


}