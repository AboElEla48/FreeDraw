package eg.foureg.freedraw.features.drawing

import android.graphics.Canvas
import android.graphics.Paint
import eg.foureg.freedraw.data.FreeShape
import eg.foureg.freedraw.data.Shape
import eg.foureg.freedraw.data.ShapeType

fun drawShape(canvas: Canvas, shape: Shape) {
    when(shape.shapeType) {
        ShapeType.TextDraw -> {
            drawShapeText(canvas, shape)
        }

        ShapeType.FreeDraw -> {
            drawShapeFreeDraw(canvas, shape)
        }

        ShapeType.BitmapDraw -> {
            drawShapeBitmap(canvas, shape)
        }
    }
}

fun drawShapeFreeDraw(canvas: Canvas, shape: Shape) {
    // create paint to this shape
    val paint = Paint().apply {
        color = shape.color
        strokeWidth = 7f
    }

    // draw the shape points
    for( i in 0 until (shape as FreeShape).points.size - 1) {
        canvas.drawLine(shape.points[i].x, shape.points[i].y, shape.points[i+1].x, shape.points[i+1].y, paint)
    }
}

fun drawShapeText(canvas: Canvas, shape: Shape) {

}

fun drawShapeBitmap(canvas: Canvas, shape: Shape) {

}