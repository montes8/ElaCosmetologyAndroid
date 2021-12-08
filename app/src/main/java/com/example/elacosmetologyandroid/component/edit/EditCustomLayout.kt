package com.example.elacosmetologyandroid.component.edit

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.*
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.extensions.hideKeyboardFrom
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

typealias UIEditCustomListener = (String) -> Unit
typealias UIEditClickListener = (View) -> Unit
typealias UIEditCustomKeyListener = (Int) -> Unit
typealias UIEditIconClickListener = () -> Unit
typealias UIEditBeforeTextChangedListener = (String, Int) -> Unit
typealias UIEditOnFocusListener = () -> Unit

@BindingMethods(
    BindingMethod(
        type = EditCustomLayout::class, attribute = "app:text", method = "setText"
    ),
    BindingMethod(
        type = EditCustomLayout::class, attribute = "app:uiEnabled", method = "setUiEnable"
    ),
    BindingMethod(
        type = EditCustomLayout::class, attribute = "app:uiHint", method = "setUiHint"
    ),
    BindingMethod(
        type = EditCustomLayout::class, attribute = "app:maxLength", method = "setUiEditMaxLength"
    )
)
@InverseBindingMethods(
    InverseBindingMethod(
        type = EditCustomLayout::class,
        attribute = "app:text",
        event = "app:textAttrChanged",
        method = "getText"
    )
)
class EditCustomLayout @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet, defaultStyle: Int = 0
) : TextInputLayout(context, attrs, defaultStyle) {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["app:textAttrChanged"])
        fun setListener(input: EditCustomLayout, inverseBindingListener: InverseBindingListener) {
            input.addTextChangedListener {
                inverseBindingListener.onChange()
            }
        }

    }

    var uiEditCustomListener: UIEditCustomListener = {}
    var uiEditClickListener: UIEditClickListener = {}
    var uiEditCustomKeyListener: UIEditCustomKeyListener = {}
    var uiEditIconClickListener: UIEditIconClickListener = {}
    var uIEditOnFocusListener: UIEditOnFocusListener = {}
    var uiEditBeforeTextChangedListener: UIEditBeforeTextChangedListener =
        { _, _ -> }

    private var clearErrorsOnChangeEnabled = true
    private var isTwoWayDataBindingEnabled = false

    private var editText: TextInputEditText = TextInputEditText(
        context,
        null,
        R.style.EditStylePink
    )

    private fun pixelsToSp(px: Float) = px / context.resources.displayMetrics.scaledDensity
    private var uiIconDrawableEnd: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_bottom)

    private var uiIconDrawableEndDisable: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_bottom)

    var uiIconTint: Int = ContextCompat.getColor(context, R.color.pink_200)

    var uiHint: String = ""
        set(value) {
            field = value
            this.isHintEnabled = true
            this.hint = value
        }


    var uiEditInputType: Int = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            editText.inputType = value
        }

    var uiEditMaxLength = 30
        set(value) {
            field = value
            editText.filters = arrayOf(InputFilter.LengthFilter(value))
        }

    var uiPasswordEnabled: Boolean = false
        set(value) {
            field = value
            if (value) {
                this.endIconMode = END_ICON_PASSWORD_TOGGLE
            } else {
                this.endIconMode = END_ICON_NONE
            }

        }

    var uiIconPassword: Drawable? = null
        set(value) {
            field = value
            setUIIconDrawable(
                value ?: ContextCompat.getDrawable(
                    context,
                    R.drawable.selector_password
                )
            )
        }


    var uiErrorMessage: String = ""
        set(value) {
            field = value
            uiErrorEnable = value.isNotEmpty()
            this.error = if (value.isEmpty()) null else value
        }

    var uiErrorEnable: Boolean = false
        set(value) {
            field = value
            this.isErrorEnabled = value
        }

    var uiEnable: Boolean = true
        set(value) {
            field = value
            this.isEnabled = value
            editText.isEnabled = value
            setUIBackgroundColor(value)
            configUiIcon()
        }

    var uiVisibility: Boolean = true
        set(value) {
            field = value
            this.visibility = if (value) View.VISIBLE else View.GONE
        }

    var uiBackgroundColor: Int = ContextCompat.getColor(context, R.color.transparent)
        set(value) {
            field = value
            this.boxBackgroundColor = value
        }

    var uiFocusable: Boolean = true
        set(value) {
            field = value
            editText.isFocusableInTouchMode = value
            editText.isCursorVisible = value
            editText.isFocusable = value
        }

    var uiDrawableEndEnable: Boolean = false
        set(value) {
            field = value
            if (isClearable) return
            uiIconDrawableEnd?.setBounds(
                0,
                0,
                context.resources.getDimensionPixelOffset(R.dimen.size_18),
                context.resources.getDimensionPixelOffset(R.dimen.size_18)
            )
            editText.setCompoundDrawablesWithIntrinsicBounds(
                null, null, if (value) uiIconDrawableEnd else null, null
            )
            editText.compoundDrawablePadding =
                context.resources.getDimensionPixelOffset(R.dimen.size_16)
        }


    var uiText: String = ""
        set(value) {
            if (field != value) {
                val cursorPosition = editText.selectionEnd
                editText.setText(value)
                setCursorPosition(cursorPosition)
                field = value
            }
        }
        get() = editText.text?.toString() ?: ""

    var uiIdEdit: Int = 0
        set(value) {
            field = value
            editText.id = value
        }

    var uiContentDescription: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty())
                editText.contentDescription = value
        }

    var uiImeiOptions: Int = EditorInfo.IME_ACTION_DONE
        set(value) {
            field = value
            editText.imeOptions = value
        }

    var uiCursorVisibility : Boolean = true
    set(value) {
        field = value
        editText.isCursorVisible = value
    }

    var isClearable: Boolean = false

    private fun setUIIconDrawable(icon: Drawable?) {
        icon?.let {
            this.endIconDrawable = it
        }
    }

    private fun setUIBackgroundColor(value: Boolean) {
        val color = if (!value) ContextCompat
            .getColor(context, R.color.pink_50)
        else ContextCompat.getColor(context, R.color.transparent)
        uiBackgroundColor = color
    }

    init {
        init()
        loadAttributes()
        setUpView()
        configIcons()
    }

    private fun configIcons() {
        uiIconDrawableEndDisable?.setTint(ContextCompat.getColor(context, R.color.pink_600))
        uiIconDrawableEnd?.setTint(uiIconTint)
    }

    private fun init() {
        setWillNotDraw(false)
        this.isErrorEnabled = false
        this.hintTextColor = ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.selector_hint_text)
        )
        this.setEndIconTintList(
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.pink_200))
        )

        this.errorIconDrawable = null
        createEditLayout(editText)
    }

    fun setConfigRangeType(type: Int, range: Int) {
        uiEditMaxLength = range
        uiEditInputType = type
    }

    private fun loadAttributes() {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.EditCustomLayout)
        attributeSet.let {
            uiEditInputType = it.getInt(
                R.styleable.EditCustomLayout_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )
            uiImeiOptions = it.getInt(
                R.styleable.EditCustomLayout_android_imeOptions,
                EditorInfo.IME_ACTION_DONE
            )
            uiIconTint = it.getColor(R.styleable.EditCustomLayout_uiIconTint, uiIconTint)

            val mDrawable = it.getDrawable(R.styleable.EditCustomLayout_uiIconDrawableEnd)
                ?: uiIconDrawableEnd

            uiIconDrawableEnd = mDrawable?.constantState?.newDrawable()?.mutate()
            uiIconDrawableEndDisable = mDrawable?.constantState?.newDrawable()?.mutate()
            isClearable = it.getBoolean(R.styleable.EditCustomLayout_isClearable, false)
            uiDrawableEndEnable =
                it.getBoolean(R.styleable.EditCustomLayout_uiDrawableEndEnabled, false)
            uiContentDescription =
                it.getString(R.styleable.EditCustomLayout_uiContentDescription) ?: ""
            uiPasswordEnabled =
                it.getBoolean(R.styleable.EditCustomLayout_uiPasswordEnabled, false)
            uiErrorEnable = it.getBoolean(R.styleable.EditCustomLayout_uiErrorEnabled, false)
            uiEditMaxLength = it.getInteger(R.styleable.EditCustomLayout_uiEditMaxLength, 40)
            uiIconPassword = it.getDrawable(R.styleable.EditCustomLayout_uiIconPassword)
            uiFocusable = it.getBoolean(R.styleable.EditCustomLayout_uiFocusable, true)
            uiText = it.getString(R.styleable.EditCustomLayout_uiText) ?: ""
            uiHint = it.getString(R.styleable.EditCustomLayout_uiHint) ?: ""
            uiEnable = it.getBoolean(R.styleable.EditCustomLayout_uiEnabled, true)
            uiErrorMessage = it.getString(R.styleable.EditCustomLayout_uiErrorMessage) ?: ""
            uiCursorVisibility = it.getBoolean(R.styleable.EditCustomLayout_uiCursorVisibility, true)


        }
        attributeSet.recycle()
    }

    private fun configUiIcon() {
        if (isClearable) return
        val endIcon = if (isEnabled) uiIconDrawableEnd else uiIconDrawableEndDisable
        if (uiDrawableEndEnable) {
            endIcon?.setBounds(
                0,
                0,
                context.resources.getDimensionPixelOffset(R.dimen.size_18),
                context.resources.getDimensionPixelOffset(R.dimen.size_18)
            )
            editText.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                endIcon,
                null
            )
            editText.compoundDrawablePadding =
                context.resources.getDimensionPixelOffset(R.dimen.size_16)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpView() {
        editText.addTextChangedListener(afterTextChanged = {
            uiEditCustomListener(it.toString())
            if (clearErrorsOnChangeEnabled) uiErrorMessage = ""
            validateIcon(it.toString(), editText.isFocused)
        })
        editText.doBeforeTextChanged { text, _, _, _ ->
            uiEditBeforeTextChangedListener(text.toString(), editText.selectionStart)
        }
        editText.setOnFocusChangeListener { _, isFocused ->
            if (!isFocused && isClearable)
                editText.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, null, null
                )
            else
                validateIcon(editText.text.toString(), editText.isFocused)
            uIEditOnFocusListener()
        }
        editText.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                context.hideKeyboardFrom(view)
            }
            false
        }
        editText.setOnClickListener { uiEditClickListener(it) }
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editText.compoundDrawables[2] != null && event.rawX >= (right - editText.compoundDrawables[2].bounds.width())) {
                    uiEditIconClickListener.invoke()
                    clearContent()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun clearContent() {
        if (isClearable)
            uiText = ""
    }

    private fun validateIcon(text: String?, isFocussed: Boolean = false) {
        if (isClearable && isFocussed) {
            uiIconDrawableEnd?.setBounds(
                0,
                0,
                context.resources.getDimensionPixelOffset(R.dimen.size_18),
                context.resources.getDimensionPixelOffset(R.dimen.size_18)
            )
            editText.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                if (text.isNullOrEmpty()) null else uiIconDrawableEnd,
                null
            )
            editText.compoundDrawablePadding =
                context.resources.getDimensionPixelOffset(R.dimen.size_16)
        }
    }

    private fun createEditLayout(editText: TextInputEditText) {
        val padding16 = context.resources.getDimensionPixelSize(R.dimen.size_16)
        with(editText) {
            setPadding(padding16, 0, padding16, 0)
            gravity = Gravity.CENTER_VERTICAL
            setTextColor(ContextCompat.getColor(context, R.color.pink_200))
            textSize = pixelsToSp(
                context.resources.getDimensionPixelSize(R.dimen.txt_size_16).toFloat()
            )
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                context.resources.getDimensionPixelSize(R.dimen.size_56)
            )
            maxLines = 1
            setLines(1)
            isSingleLine = true
        }
        addView(editText)
    }

    fun clearErrorsOnChange(value: Boolean) {
        clearErrorsOnChangeEnabled = value
    }

    fun setText(text: String?) {
        if (text != uiText) {
            uiText = text ?: ""
        }
    }

    fun getText(): String? {
        return uiText
    }

    private fun setCursorPosition(selectionEnd: Int) {
        if (isTwoWayDataBindingEnabled) {
            editText.text?.let {
                if (editText.isFocused && it.isNotBlank()) {
                    if (selectionEnd >= 0 && selectionEnd < it.length) {
                        editText.setSelection(selectionEnd)
                    }
                }
            }
        }
    }

    fun addTextChangedListener(block: () -> Unit) {
        isTwoWayDataBindingEnabled = true
        editText.doAfterTextChanged {
            block()
        }
    }

    fun setFilters(inputFilterList: Array<InputFilter>) {
        editText.filters = inputFilterList
    }

    fun setSelection(position: Int) {
        editText.setSelection(position)
    }

    fun getSelectionStart(): Int {
        return editText.selectionStart
    }
}