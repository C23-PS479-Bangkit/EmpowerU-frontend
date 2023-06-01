package com.capstone.empoweru.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.capstone.empoweru.R

class PasswordEditText: AppCompatEditText {
    private lateinit var editTextBackground: Drawable
    private lateinit var editTextErrorBackground: Drawable
    private var isError = false
    private var isPasswordVisible = false

    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr) { init() }

    @SuppressLint("ClickableViewAccessibility")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        background = if (isError) editTextErrorBackground else editTextBackground
        addTextChangedListener(onTextChanged = { text, _, _, _ ->
            if (!TextUtils.isEmpty(text) && text.toString().length < 6 && compoundDrawables[DRAWABLE_RIGHT] != null) {
                error = "Password setidaknya lebih dari 6 karakter"
                isError = true
            } else {
                error = null
                isError = false
            }
        })
    }

    fun isValid(): Boolean {
        return !isError
    }

    private fun init() {

        val customTypeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
        setTypeface(customTypeface)

        setTypeface(customTypeface)
        editTextBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_selected_edit_text) as Drawable
        editTextErrorBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error) as Drawable

        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                clearFocus()
                background = null
                return@setOnEditorActionListener true
            }
            false
        }

        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP &&
                event.x >= width - paddingRight - compoundDrawables[DRAWABLE_RIGHT].bounds.width()
            ) {
                isPasswordVisible = !isPasswordVisible
                setInputType(if (isPasswordVisible) {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                })
                setSelection(length())

                val drawable = if (isPasswordVisible) {
                    ContextCompat.getDrawable(context, R.drawable.ic_eye)
                } else {
                    ContextCompat.getDrawable(context, R.drawable.ic_password)
                }
                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    compoundDrawablesRelative[DRAWABLE_LEFT],
                    compoundDrawablesRelative[DRAWABLE_TOP],
                    drawable,
                    compoundDrawablesRelative[DRAWABLE_BOTTOM]
                )

                setTypeface(customTypeface)

                performClick() // Add this line to satisfy the lint warning
                return@setOnTouchListener true
            }
            false
        }

        setCompoundDrawablesRelativeWithIntrinsicBounds(
            compoundDrawablesRelative[DRAWABLE_LEFT],
            compoundDrawablesRelative[DRAWABLE_TOP],
            ContextCompat.getDrawable(context, R.drawable.ic_password),
            compoundDrawablesRelative[DRAWABLE_BOTTOM]
        )
    }

    companion object {
        const val DRAWABLE_LEFT = 0
        const val DRAWABLE_TOP = 1
        const val DRAWABLE_RIGHT = 2
        const val DRAWABLE_BOTTOM = 3
    }
}