package eg.foureg.freedraw.ui.dialogs.tools

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import eg.foureg.freedraw.R
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

        viewModel.lineShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_line_radio.isChecked = checked
        })

        viewModel.textShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_text_radio.isChecked = checked
        })

        viewModel.bitmapShapeRadioChecked.observe(this, Observer { checked ->
            tools_dialog_shape_type_bitmap_radio.isChecked = checked
        })

        viewModel.drawingColor.observe(this, Observer { color ->
            tools_dialog_color_text_view.text = "$color"
        })

        viewModel.fillingColor.observe(this, Observer { color ->
            tools_dialog_filling_color_text_view.text = "$color"
        })

        tools_dialog_color_text_view.setOnClickListener {
            val c = Color.valueOf(viewModel.drawingColor.value!!)
            val colorDlg = ColorPicker(this, c.red().toInt(), c.green().toInt(), c.blue().toInt() )
            colorDlg.show()


            val okColor : Button = colorDlg.findViewById(com.pes.androidmaterialcolorpickerdialog.R.id.okColorButton)

            okColor.setOnClickListener{
                /* You can get single channel (value 0-255) */
                val selectedColorR = colorDlg.getRed()
                val selectedColorG = colorDlg.getGreen()
                val selectedColorB = colorDlg.getBlue()

                viewModel.setDrawingColor(Color.argb(255, selectedColorR, selectedColorG, selectedColorB ))
                colorDlg.dismiss()
            }
        }

        tools_dialog_filling_color_text_view.setOnClickListener {
            val c = Color.valueOf(viewModel.fillingColor.value!!)
            val colorDlg = ColorPicker(this, c.red().toInt(), c.green().toInt(), c.blue().toInt() )
            colorDlg.show()


            val okColor : Button = colorDlg.findViewById(com.pes.androidmaterialcolorpickerdialog.R.id.okColorButton)

            okColor.setOnClickListener{
                /* You can get single channel (value 0-255) */
                val selectedColorR = colorDlg.getRed()
                val selectedColorG = colorDlg.getGreen()
                val selectedColorB = colorDlg.getBlue()

                viewModel.setFillingColor(Color.argb(255, selectedColorR, selectedColorG, selectedColorB ))
                colorDlg.dismiss()
            }
        }

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

                R.id.tools_dialog_shape_type_line_radio -> {
                    viewModel.lineShapeRadioChecked.value = true
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
