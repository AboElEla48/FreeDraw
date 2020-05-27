package eg.foureg.freedraw.features.scaling

import android.graphics.PointF
import eg.foureg.freedraw.data.*

fun scaleBoard(startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float, board: Board) : Board {

    for((index, shape) in board.shapes.withIndex()) {
        when(shape.shapeType) {
            ShapeType.FreeDraw -> {
                board.shapes[index] = scaleFreeShape(shape as FreeShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            ShapeType.EraseDraw -> {
                board.shapes[index] = scaleEraserShape(shape as EraseShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            ShapeType.TextDraw -> {

            }

            ShapeType.RectDraw -> {
                board.shapes[index] = scaleRectShape(shape as RectShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            ShapeType.CircleDraw -> {
                board.shapes[index] = scaleCircleShape(shape as CircleShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            ShapeType.LineDraw -> {
                board.shapes[index] = scaleLineShape(shape as LineShape, startFramePoint, scaleXRatio, scaleYRatio)
            }

            else -> {}
        }
    }
    return board
}

private fun scaleFreeShape(freeShape: FreeShape, startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float) : FreeShape {
    for(pt in freeShape.points) {
        pt.x *= scaleXRatio
        pt.x += startFramePoint.x

        pt.y *= scaleYRatio
        pt.y += startFramePoint.y
    }

    return freeShape
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

private fun scaleCircleShape(circleShape: CircleShape, startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float) : CircleShape{
    circleShape.topLeftPoint.x *= scaleXRatio
    circleShape.topLeftPoint.x += startFramePoint.x

    circleShape.rightBottomPoint.x *= scaleXRatio
    circleShape.rightBottomPoint.x += startFramePoint.x

    circleShape.topLeftPoint.y *= scaleYRatio
    circleShape.topLeftPoint.y += startFramePoint.y

    circleShape.rightBottomPoint.y *= scaleYRatio
    circleShape.rightBottomPoint.y += startFramePoint.y

    return circleShape
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

private fun scaleEraserShape(eraseShape: EraseShape, startFramePoint: PointF, scaleXRatio: Float, scaleYRatio: Float) : EraseShape{
    for(pt in eraseShape.erasePoints) {
        pt.x *= scaleXRatio
        pt.x += startFramePoint.x

        pt.y *= scaleYRatio
        pt.y += startFramePoint.y
    }

    return eraseShape
}


