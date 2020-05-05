package eg.foureg.freedraw.data

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Parent Shape class
 */
@Parcelize
open class Shape(val color: Int) : Parcelable

/**
 * Shape class for drawing free shapes
 */
@Parcelize
data class FreeShape(val points : ArrayList<PointF>, val shapeColor : Int) : Shape(shapeColor)

/**
 * Text Shape class
 */
@Parcelize
data class TextShape(val text: String, val textColor: Int) :Shape(textColor)

/**
 * Image Shape class
 */
@Parcelize
data class ImageShape(val bitmap: Bitmap, val backgroundColor: Int) :Shape(backgroundColor)