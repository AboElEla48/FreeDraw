package eg.foureg.freedraw.features.drawing

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import eg.foureg.freedraw.data.*
import kotlinx.coroutines.processNextEventInCurrentThread

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

        ShapeType.LineDraw -> {
            drawLineShape(canvas, shape as LineShape, drawingPaint)
        }

        ShapeType.EraseDraw -> {
            drawEraser(canvas, shape as EraseShape, drawingPaint)
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

fun drawShapeFreeDraw(canvas: Canvas, shape: FreeShape, paint: Paint) {
    // draw the shape points
    drawFreeShapePoints(canvas, shape.points, paint)
//    for( i in 0 until shape.points.size - 1) {
//        canvas.drawLine(shape.points[i].x, shape.points[i].y, shape.points[i+1].x, shape.points[i+1].y, paint)
//    }
}

private fun drawFreeShapePoints(canvas: Canvas, points: List<PointF>, paint: Paint) {
    for( i in 0 until points.size - 1) {
        canvas.drawLine(points[i].x, points[i].y, points[i+1].x, points[i+1].y, paint)
    }
}

fun drawEraser(canvas: Canvas, shape: EraseShape, paint: Paint) {
    paint.color = Color.WHITE
    paint.strokeWidth = 15f

    drawFreeShapePoints(canvas, shape.erasePoints, paint)
}

fun drawShapeText(canvas: Canvas, shape: TextShape, paint: Paint) {
    paint.textSize = shape.textSize
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

fun drawLineShape(canvas: Canvas, shape: LineShape, paint: Paint) {
    canvas.drawLine(shape.startPoint.x, shape.startPoint.y, shape.endPoint.x, shape.endPoint.y,  paint)
}