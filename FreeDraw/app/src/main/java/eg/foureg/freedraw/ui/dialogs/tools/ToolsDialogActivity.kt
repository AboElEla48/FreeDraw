package eg.foureg.freedraw.ui.dialogs.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eg.foureg.freedraw.R
import eg.foureg.freedraw.data.ShapeType
import eg.foureg.freedraw.model.DrawingToolsModel
import kotlinx.android.synthetic.main.activity_tools_dialog.*

class ToolsDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_dialog)

        val viewModel = ViewModelProvider(this).get(ToolsDialogViewModel::class.java)

        viewModel.freeDrawShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_free_draw_radio.isChecked = checked
        })

        viewModel.circleShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_circle_radio.isChecked = checked
        })

        viewModel.rectShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_rect_radio.isChecked = checked
        })

        viewModel.textShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_text_radio.isChecked = checked
        })

        viewModel.bitmapShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_bitmap_radio.isChecked = checked
        })

        tools_dialog_save_tools_values_btn.setOnClickListener {
            viewModel.resetAllValues()

            when(tools_dialog_shape_type_radio_group.checkedRadioButtonId) {
                R.id.tools_dialog_shape_type_free_draw_radio -> {
                    viewModel.freeDrawShapeRadioChecked.value = true
                }

                R.id.tools_dialog_shape_type_rect_radio -> {
                    viewModel.rectShapeRadioChecked.value = true
                }

                R.id.tools_dialog_shape_type_circle_radio -> {
                    viewModel.circleShapeRadioChecked.value = true
                }

                R.id.tools_dialog_shape_type_text_radio -> {
                    viewModel.textShapeRadioChecked.value = true
                }

                R.id.tools_dialog_shape_type_bitmap_radio -> {
                    viewModel.bitmapShapeRadioChecked.value = true
                }
            }
            viewModel.saveTools()
            finish()
        }

        viewModel.initViewModel()
    }
}
