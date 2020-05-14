package eg.foureg.freedraw.features.drawing

import android.graphics.Canvas
import android.graphics.Paint
import eg.foureg.freedraw.data.*

fun drawShape(canvas: Canvas, shape: Shape) {

    // create paint to this shape
    val drawingPaint = createDrawingPaint(shape.color)

    when(shape.shapeType) {
        ShapeType.TextDraw -> {
            drawShapeText(canvas, shape, drawingPaint)
        }

        ShapeType.FreeDraw -> {
            drawShapeFreeDraw(canvas, shape, drawingPaint)
        }

        ShapeType.BitmapDraw -> {
            drawShapeBitmap(canvas, shape, drawingPaint)
        }

        ShapeType.RectDraw -> {
            drawRectShape(canvas, shape, drawingPaint, createFillingPaint((shape as RectShape).fillColor))
        }

        ShapeType.CircleDraw -> {
            drawCircleShape(canvas, shape, drawingPaint, createFillingPaint((shape as CircleShape).fillColor))
        }
    }
}

fun createDrawingPaint(c: Int) : Paint {
    return Paint().apply {
        style = Paint.Style.STROKE
        color = c
        strokeWidth = 7f
    }
}

fun createFillingPaint(c: Int) : Paint {
    return Paint().apply {
        style = Paint.Style.FILL
        color = c
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

fun drawCircleShape(canvas: Canvas, shape: Shape, paint: Paint, fillingPaint: Paint) {
    val radius: Float = ((shape as CircleShape).rightBottomPoint.x - shape.topLeftPoint.x) / 2f
    canvas.drawCircle(shape.topLeftPoint.x + radius, shape.topLeftPoint.y + radius, radius, paint)
    canvas.drawCircle(shape.topLeftPoint.x + radius, shape.topLeftPoint.y + radius, radius, fillingPaint)
}

fun drawRectShape(canvas: Canvas, shape: Shape, paint: Paint, fillingPaint: Paint) {
    canvas.drawRect((shape as RectShape).topLeftPoint.x, shape.topLeftPoint.y, shape.rightBottomPoint.x, shape.rightBottomPoint.y, paint)
    canvas.drawRect(shape.topLeftPoint.x, shape.topLeftPoint.y, shape.rightBottomPoint.x, shape.rightBottomPoint.y, fillingPaint)
}