package eg.foureg.freedraw.features.drawing

import android.graphics.Canvas
import android.graphics.Paint
import eg.foureg.freedraw.data.*

fun drawShape(canvas: Canvas, shape: Shape) {

    // create paint to this shape
    val paint = Paint().apply {
        color = shape.color
        strokeWidth = 7f
    }

    when(shape.shapeType) {
        ShapeType.TextDraw -> {
            drawShapeText(canvas, shape, paint)
        }

        ShapeType.FreeDraw -> {
            drawShapeFreeDraw(canvas, shape, paint)
        }

        ShapeType.BitmapDraw -> {
            drawShapeBitmap(canvas, shape, paint)
        }

        ShapeType.RectDraw -> {
            drawRectShape(canvas, shape, paint)
        }

        ShapeType.CircleDraw -> {
            drawCircleShape(canvas, shape, paint)
        }
    }
}

fun drawShapeFreeDraw(canvas: Canvas, shape: Shape, paint: Paint) {
    // draw the shape points
    for( i in 0 until (shape as FreeShape).points.size - 1) {
        canvas.drawLine(shape.points[i].x, shape.points[i].y, shape.points[i+1].x, shape.points[i+1].y, paint)
    }
}

fun drawShapeText(canvas: Canvas, shape: Shape, paint: Paint) {

}

fun drawShapeBitmap(canvas: Canvas, shape: Shape, paint: Paint) {

}

fun drawCircleShape(canvas: Canvas, shape: Shape, paint: Paint) {
    val radius: Float = ((shape as CircleShape).rightBottomPoint.x - shape.topLeftPoint.x) / 2f
    canvas.drawCircle(shape.topLeftPoint.x + radius, shape.topLeftPoint.y + radius, radius, paint)
}

fun drawRectShape(canvas: Canvas, shape: Shape, paint: Paint) {
    canvas.drawRect((shape as RectShape).topLeftPoint.x, shape.topLeftPoint.y, shape.rightBottomPoint.x, shape.rightBottomPoint.y, paint)
}