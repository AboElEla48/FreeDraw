package eg.foureg.freedraw.ui.dialogs.tools

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eg.foureg.freedraw.data.ShapeType
import eg.foureg.freedraw.model.DrawingToolsModel

class ToolsDialogViewModel : ViewModel() {

    fun initViewModel() {
        resetAllValues()

        when(DrawingToolsModel.drawingShapeType) {
            ShapeType.CircleDraw -> {
                circleShapeRadioChecked.value = true
            }

            ShapeType.FreeDraw -> {
                freeDrawShapeRadioChecked.value = true
            }

            ShapeType.RectDraw -> {
                rectShapeRadioChecked.value = true
            }

            ShapeType.LineDraw -> {
                lineShapeRadioChecked.value = true
            }

            ShapeType.TextDraw -> {
                textShapeRadioChecked.value = true
            }

            ShapeType.BitmapDraw -> {
                bitmapShapeRadioChecked.value = true
            }
        }

        drawingColor.value = DrawingToolsModel.drawingColor
        fillingColor.value = DrawingToolsModel.fillingColor
    }

    fun resetAllValues() {
        freeDrawShapeRadioChecked.value = false
        circleShapeRadioChecked.value = false
        rectShapeRadioChecked.value = false
        lineShapeRadioChecked.value = false
        textShapeRadioChecked.value = false
        bitmapShapeRadioChecked.value = false
    }

    fun saveTools() {
        if(freeDrawShapeRadioChecked.value!!) {
            DrawingToolsModel.drawingShapeType = ShapeType.FreeDraw
        } else if(circleShapeRadioChecked.value!!) {
            DrawingToolsModel.drawingShapeType = ShapeType.CircleDraw
        } else if(rectShapeRadioChecked.value!!) {
            DrawingToolsModel.drawingShapeType = ShapeType.RectDraw
        }else if(lineShapeRadioChecked.value!!) {
            DrawingToolsModel.drawingShapeType = ShapeType.LineDraw
        }else if(textShapeRadioChecked.value!!) {
            DrawingToolsModel.drawingShapeType = ShapeType.TextDraw
        }else if(bitmapShapeRadioChecked.value!!) {
            DrawingToolsModel.drawingShapeType = ShapeType.BitmapDraw
        }
    }

    fun setDrawingColor(color: Int) {
        DrawingToolsModel.drawingColor = color
        drawingColor.value = color
    }

    fun setFillingColor(color: Int) {
        DrawingToolsModel.fillingColor = color
        fillingColor.value = color
    }

    val freeDrawShapeRadioChecked : MutableLiveData<Boolean> = MutableLiveData()
    val circleShapeRadioChecked : MutableLiveData<Boolean> = MutableLiveData()
    val rectShapeRadioChecked : MutableLiveData<Boolean> = MutableLiveData()
    val lineShapeRadioChecked : MutableLiveData<Boolean> = MutableLiveData()
    val textShapeRadioChecked : MutableLiveData<Boolean> = MutableLiveData()
    val bitmapShapeRadioChecked : MutableLiveData<Boolean> = MutableLiveData()

    val drawingColor: MutableLiveData<Int> = MutableLiveData()
    val fillingColor: MutableLiveData<Int> = MutableLiveData()
}