package eg.foureg.freedraw.features.drawing

import android.graphics.Canvas
import android.graphics.Paint
import eg.foureg.freedraw.data.*

fun drawShape(canvas: Canvas, shape: Shape) {

    // create paint to this shape
    val drawingPaint = createDrawingPaint(shape.color)

    when(shape.shapeType) {
        ShapeType.TextDraw -> {
            drawShapeText(canvas, shape as TextShape, drawingPaint)
        }

        ShapeType.FreeDraw -> {
            drawShapeFreeDraw(canvas, shape as FreeShape, drawingPaint)
        }

        ShapeType.BitmapDraw -> {
            drawShapeBitmap(canvas, shape as ImageShape, drawingPaint)
        }

        ShapeType.RectDraw -> {
            drawRectShape(canvas, shape as RectShape, drawingPaint, createFillingPaint(shape.fillColor))
        }

        ShapeType.CircleDraw -> {
            drawCircleShape(canvas, shape as CircleShape, drawingPaint, createFillingPaint(shape.fillColor))
        }
    }
}

fun createDrawingPaint(c: Int) : Paint {
    return Paint().apply {
        style = Paint.Style.STROKE
        color = c
        strokeWidth = 7f
        textSize = 70f
    }
}

fun createFillingPaint(c: Int) : Paint {
    return Paint().apply {
        style = Paint.Style.FILL
        color = c
    }
}

fun drawShapeFreeDraw(canvas: Canvas, shape: FreeShape, paint: Paint) {
    // draw the shape points
    for( i in 0 until shape.points.size - 1) {
        canvas.drawLine(shape.points[i].x, shape.points[i].y, shape.points[i+1].x, shape.points[i+1].y, paint)
    }
}

fun drawShapeText(canvas: Canvas, shape: TextShape, paint: Paint) {
    canvas.drawText(shape.text, shape.topLeftPoint.x, shape.topLeftPoint.y, paint)
}

fun drawShapeBitmap(canvas: Canvas, shape: ImageShape, paint: Paint) {
    TODO("Unsupported yet")
}

fun drawCircleShape(canvas: Canvas, shape: CircleShape, paint: Paint, fillingPaint: Paint) {
    val radius: Float = (shape.rightBottomPoint.x - shape.topLeftPoint.x) / 2f
    canvas.drawCircle(shape.topLeftPoint.x + radius, shape.topLeftPoint.y + radius, radius, paint)
    canvas.drawCircle(shape.topLeftPoint.x + radius, shape.topLeftPoint.y + radius, radius, fillingPaint)
}

fun drawRectShape(canvas: Canvas, shape: RectShape, paint: Paint, fillingPaint: Paint) {
    canvas.drawRect(shape.topLeftPoint.x, shape.topLeftPoint.y, shape.rightBottomPoint.x, shape.rightBottomPoint.y, paint)
    canvas.drawRect(shape.topLeftPoint.x, shape.topLeftPoint.y, shape.rightBottomPoint.x, shape.rightBottomPoint.y, fillingPaint)
}