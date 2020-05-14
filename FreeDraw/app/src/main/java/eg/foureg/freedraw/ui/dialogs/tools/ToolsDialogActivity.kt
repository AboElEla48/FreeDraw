package eg.foureg.freedraw.ui.dialogs.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eg.foureg.freedraw.R
import eg.foureg.freedraw.data.ShapeType
import eg.foureg.freedraw.model.DrawingToolsModel
import kotlinx.android.synthetic.main.activity_tools_dialog.*

class ToolsDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_dialog)

        tools_dialog_select_shape_btn.setOnClickListener {
            when(tools_dialog_shape_type_radio_group.checkedRadioButtonId) {
                R.id.tools_dialog_shape_type_free_draw_radio -> {
                    DrawingToolsModel.drawingShape = ShapeType.FreeDraw
                }

                R.id.tools_dialog_shape_type_rect_radio -> {
                    DrawingToolsModel.drawingShape = ShapeType.RectDraw
                }

                R.id.tools_dialog_shape_type_circle_radio -> {
                    DrawingToolsModel.drawingShape = ShapeType.CircleDraw
                }
            }
        }
    }
}
