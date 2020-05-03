package eg.foureg.freedraw.data

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF

abstract class Shape(val color: Color)

data class FreeShape(val points : ArrayList<PointF>, val shapeColor : Color) : Shape(shapeColor)
data class TextShape(val text: String, val textColor: Color) :Shape(textColor)
data class ImageShape(val bitmap: Bitmap, val backgroundColor: Color) :Shape(backgroundColor)