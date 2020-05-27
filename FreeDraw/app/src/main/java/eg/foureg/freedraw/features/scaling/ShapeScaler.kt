package eg.foureg.freedraw.features.scaling

import android.graphics.PointF
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.data.LineShape
import eg.foureg.freedraw.data.RectShape
import eg.foureg.freedraw.data.ShapeType

fun scaleBoard(startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float, board: Board) : Board {

    for((index, shape) in board.shapes.withIndex()) {
        when(shape.shapeType) {
            ShapeType.FreeDraw -> {

            }

            ShapeType.EraseDraw -> {

            }

            ShapeType.TextDraw -> {

            }

            ShapeType.RectDraw -> {
                board.shapes[index] = scaleRectShape(shape as RectShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            ShapeType.CircleDraw -> {

            }

            ShapeType.LineDraw -> {
                board.shapes[index] = scaleLineShape(shape as LineShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            else -> {}
        }
    }
    return board
}

private fun scaleLineShape(lineShape: LineShape, startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float) : LineShape{
    lineShape.startPoint.x *= scaleXRatio
    lineShape.startPoint.x += startFramePoint.x

    lineShape.endPoint.x *= scaleXRatio
    lineShape.endPoint.x += startFramePoint.x

    lineShape.startPoint.y *= scaleYRatio
    lineShape.startPoint.y += startFramePoint.y

    lineShape.endPoint.y *= scaleYRatio
    lineShape.endPoint.y += startFramePoint.y

    return lineShape
}

private fun scaleRectShape(rectShape: RectShape, startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float) : RectShape{
    rectShape.topLeftPoint.x *= scaleXRatio
    rectShape.topLeftPoint.x += startFramePoint.x

    rectShape.rightBottomPoint.x *= scaleXRatio
    rectShape.rightBottomPoint.x += startFramePoint.x

    rectShape.topLeftPoint.y *= scaleYRatio
    rectShape.topLeftPoint.y += startFramePoint.y

    rectShape.rightBottomPoint.y *= scaleYRatio
    rectShape.rightBottomPoint.y += startFramePoint.y

    return rectShape
}