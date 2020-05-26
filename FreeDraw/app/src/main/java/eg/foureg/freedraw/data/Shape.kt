package eg.foureg.freedraw.data

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class ShapeType {
    FreeDraw,
    EraseDraw,
    TextDraw,
    RectDraw,
    CircleDraw,
    LineDraw,
    TempInsertBoardFrame
}

/**
 * Parent Shape class
 */
@Parcelize
open class Shape(val color: Int, val shapeType: ShapeType) : Parcelable

/**
 * Shape class for drawing free shapes
 */
@Parcelize
data class FreeShape(val points : ArrayList<PointF>, val shapeColor : Int) : Shape(shapeColor, ShapeType.FreeDraw)

/**
 * Text Shape class
 */
@Parcelize
data class TextShape(val text: String, var topLeftPoint: PointF, var textSize: Float, val textColor: Int) :Shape(textColor, ShapeType.TextDraw)

/**
 * Circle Shape class
 */
@Parcelize
data class CircleShape(var topLeftPoint : PointF, var rightBottomPoint: PointF, val outerColor: Int, val fillColor: Int) :Shape(outerColor, ShapeType.CircleDraw)

/**
 * Rect Shape class
 */
@Parcelize
data class RectShape(var topLeftPoint : PointF, var rightBottomPoint: PointF, val outerColor: Int,  val fillColor: Int) :Shape(outerColor, ShapeType.RectDraw)

/**
 * Rect Shape class
 */
@Parcelize
data class TempInsertBoardFrame(var topLeftPoint : PointF, var rightBottomPoint: PointF, val outerColor: Int) :Shape(outerColor, ShapeType.TempInsertBoardFrame)


/**
 * Line Shape class
 */
@Parcelize
data class LineShape(var startPoint : PointF, var endPoint: PointF, val drawColor: Int) :Shape(drawColor, ShapeType.LineDraw)

/**
 * Shape class for Eraser. It is like Free Draw but with background color
 */
@Parcelize
data class EraseShape(val erasePoints : ArrayList<PointF>) : Shape(Color.WHITE, ShapeType.EraseDraw)
