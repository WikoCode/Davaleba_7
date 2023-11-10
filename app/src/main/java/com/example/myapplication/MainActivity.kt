package com.example.myapplication

import android.os.Bundle
import android.text.InputType
import android.util.Log.d
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAddField = binding.btnAddField
        val etEnterFieldName = binding.etEnterFieldName
        val cbFieldIsNumeric = binding.cbFieldIsNumeric

        btnAddField.setOnClickListener{
            addNewField(etEnterFieldName.text.toString(), cbFieldIsNumeric.isChecked)
            d("isPressed", "It was pressed and is working")
        }

    }

    private var lastAddedViewId = View.NO_ID

    private fun addNewField(fieldName: String, toggleNumeric: Boolean) {
        val newEditText = EditText(this)
        newEditText.id = View.generateViewId()
        newEditText.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        if (toggleNumeric) {
            newEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        } else {
            newEditText.inputType = InputType.TYPE_CLASS_TEXT
        }

        newEditText.hint = fieldName

        binding.constraintLayout.addView(newEditText)

        val set = ConstraintSet()
        set.clone(binding.constraintLayout)

        if (lastAddedViewId != View.NO_ID) {
            set.connect(
                newEditText.id, ConstraintSet.TOP,
                lastAddedViewId, ConstraintSet.BOTTOM,
                resources.getDimensionPixelSize(R.dimen.margin_between_fields)
            )
        } else {
            set.connect(
                newEditText.id, ConstraintSet.TOP,
                binding.btnAddField.id, ConstraintSet.BOTTOM,
                resources.getDimensionPixelSize(R.dimen.margin_between_fields)
            )
        }

        set.connect(
            newEditText.id, ConstraintSet.START,
            binding.constraintLayout.id, ConstraintSet.START,
            resources.getDimensionPixelSize(R.dimen.start_margin)
        )
        set.connect(
            newEditText.id, ConstraintSet.END,
            binding.constraintLayout.id, ConstraintSet.END,
            resources.getDimensionPixelSize(R.dimen.end_margin)
        )

        set.applyTo(binding.constraintLayout)

        lastAddedViewId = newEditText.id
    }

}