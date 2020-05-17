package eg.foureg.freedraw.data

import android.graphics.Bitmap
import android.graphics.PointF
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class ShapeType {
    FreeDraw,
    TextDraw,
    BitmapDraw,
    RectDraw,
    CircleDraw
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
data class TextShape(val text: String, var topLeftPoint: PointF, val textColor: Int) :Shape(textColor, ShapeType.TextDraw)

/**
 * Image Shape class
 */
@Parcelize
data class ImageShape(val bitmap: Bitmap, val backgroundColor: Int) :Shape(backgroundColor, ShapeType.BitmapDraw)

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