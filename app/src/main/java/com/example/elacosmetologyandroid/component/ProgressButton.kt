package com.example.elacosmetologyandroid.component

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R


@BindingMethods(
    BindingMethod(
        type = ProgressButton::class, attribute = "app:isButtonEnabled", method = "setButtonEnabled"
    ),
    BindingMethod(
        type = ProgressButton::class, attribute = "app:isButtonLoading", method = "setButtonLoading"
    ),
    BindingMethod(
        type = ProgressButton::class, attribute = "app:isButtonVisible", method = "setButtonVisible"
    ),
    BindingMethod(
        type = ProgressButton::class, attribute = "app:text", method = "setButtonText"
    )
)
class ProgressButton constructor(
    context: Context,
    private val attrs: AttributeSet,
) : ConstraintLayout(context, attrs) {

    private val binding: com.example.elacosmetologyandroid.databinding.ProgressButtonBinding = DataBindingUtil.inflate(
        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
        R.layout.ui_kit_progress_button,
        this,
        true
    )

    init {
        loadAttributes()
        setUpView()
    }

    private lateinit var currentStyle: ProgressButtonStyle

    private var buttonIconLeft: Int = 0
    private var paddingButton: Int =
        context.resources.getDimensionPixelSize(R.dimen.progress_button_padding_large)
    private var buttonTextSize: Float =
        context.resources.getDimension(R.dimen.progress_button_size_large)

    var buttonDrawablePadding: Int = 0

    var buttonUISize: ProgressButtonSize = ProgressButtonSize.LARGE
        set(value) {
            field = value
            setUpSizesButton(value)
        }

    var buttonUIStyle: ProgressButtonStyle = ProgressButtonStyle.PRIMARY
        set(value) {
            field = value
            currentStyle = value
            setUpStyleButton()
        }

    var isButtonLoading: Boolean? = false
        set(value) {
            field = value
            if (value == true) setUpStyleLoadingButton()
            else if (isButtonEnabled != false) setUpStyleButton()
        }

    var isButtonEnabled: Boolean? = true
        set(value) {
            field = value
            binding.button.isEnabled = value ?: true
            if (value == true) setUpStyleButton()
            else setUpStyleEnabledButton()
        }

    var isButtonVisible: Boolean = true
        set(value) {
            field = value
            binding.button.isVisible = value
        }

    var buttonText: String = ""
        set(value) {
            field = value
            binding.button.text = value
        }
    var buttonIconRight: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                binding.button.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    AppCompatResources.getDrawable(context, value),
                    null
                )
            }

        }

    var buttonIconStart: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                binding.button.setCompoundDrawablesWithIntrinsicBounds(
                    AppCompatResources.getDrawable(context, value),
                    null,
                    null,
                    null
                )
            }

        }

    private fun setUpView() {
        setPaddingIconButton()
        setUpIcon()
    }

    fun setOnClickButtonListener(listener: ProgressButtonListener) {
        binding.button.setOnClickListener { listener.onClick(this) }
    }

    fun setOnClickButtonDelayListener(time: Long = 700, listener: ProgressButtonListener) {
        binding.button.setOnClickListener {
            binding.button.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                binding.button.isEnabled = (isButtonEnabled == true)
            }, time)
            listener.onClick(this)
        }
    }

    private fun loadAttributes() {
        val attribute = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton)
        buttonUIStyle =
            ProgressButtonStyle.values()[attribute.getInt(
                R.styleable.ProgressButton_buttonUIStyle,
                0
            )]
        buttonUISize =
            ProgressButtonSize.values()[attribute.getInt(
                R.styleable.ProgressButton_buttonUISize,
                0
            )]
        buttonIconLeft = attribute.getResourceId(R.styleable.ProgressButton_buttonIconLeft, 0)
        buttonIconRight = attribute.getResourceId(R.styleable.ProgressButton_buttonIconRight, 0)
        buttonText = attribute.getString(R.styleable.ProgressButton_buttonText) ?: ""
        buttonDrawablePadding =
            attribute.getResourceId(R.styleable.ProgressButton_buttonDrawablePadding, 0)
        isButtonEnabled = attribute.getBoolean(R.styleable.ProgressButton_isButtonEnabled, true)
        isButtonVisible = attribute.getBoolean(R.styleable.ProgressButton_isButtonVisible, true)
        isButtonLoading = attribute.getBoolean(R.styleable.ProgressButton_isButtonLoading, false)
        currentStyle = buttonUIStyle
        attribute.recycle()
    }

    private fun setUpStyleButton() {
        when (currentStyle) {
            ProgressButtonStyle.PRIMARY -> setUpStylePrimaryButton()
            ProgressButtonStyle.SECONDARY -> setUpButtonSecondary()
            ProgressButtonStyle.SUBTLE -> setUpButtonSubtle()
            ProgressButtonStyle.TEXT -> setUpButtonText()
        }
    }

    private fun setUpStyleLoadingButton() {
        when (currentStyle) {
            ProgressButtonStyle.PRIMARY -> {
                backgroundButtonLoading(
                    background = R.drawable.ui_kit_shape_primary_loading_button,
                    backgroundTextColor = context.getColor(R.color.R700)
                )
            }
            ProgressButtonStyle.SECONDARY -> {
                backgroundButtonLoading(
                    background = R.drawable.ui_kit_shape_secondary_default_button,
                    backgroundTextColor = context.getColor(android.R.color.transparent)
                )
            }
            ProgressButtonStyle.SUBTLE -> {
                backgroundButtonLoading(
                    background = R.drawable.ui_kit_shape_subtle_default_button,
                    backgroundTextColor = context.getColor(android.R.color.transparent)
                )
            }
            ProgressButtonStyle.TEXT -> {
                binding.button.text = context.getString(R.string.progress_button_loading)
                binding.button.isEnabled = false
            }
        }
    }

    private fun setUpStyleEnabledButton() {
        when (currentStyle) {
            ProgressButtonStyle.PRIMARY -> {
                backgroundButtonDisabled(
                    background = R.drawable.ui_kit_shape_primary_disabled_button,
                    textColor = context.getColor(R.color.N500)
                )
            }
            ProgressButtonStyle.SECONDARY -> {
                backgroundButtonDisabled(
                    background = R.drawable.ui_kit_shape_secondary_disabled_button,
                    textColor = context.getColor(R.color.B200)
                )
            }
            ProgressButtonStyle.SUBTLE -> {
                backgroundButtonDisabled(
                    background = R.drawable.ui_kit_shape_subtle_disabled_button,
                    textColor = context.getColor(R.color.N500)
                )
            }
            ProgressButtonStyle.TEXT -> {
                binding.button.setTextColor(context.getColor(R.color.B400))
            }
        }
    }


    private fun setUpSizesButton(sizeButton: ProgressButtonSize) {
        val params = binding.button.layoutParams
        when (sizeButton) {
            ProgressButtonSize.LARGE -> {
                paddingButton =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_padding_large)
                buttonTextSize = context.resources.getDimension(R.dimen.progress_button_size_large)
                params.height =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_height_large)
            }
            ProgressButtonSize.MEDIUM -> {
                paddingButton =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_padding_medium)
                buttonTextSize = context.resources.getDimension(R.dimen.progress_button_size_medium)
                params.height =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_height_medium)
            }
            ProgressButtonSize.SMALL -> {
                paddingButton =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_padding_small)
                buttonTextSize = context.resources.getDimension(R.dimen.progress_button_size_small)
                params.height =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_height_small)
            }
            ProgressButtonSize.NORMAL -> {
                paddingButton =
                    context.resources.getDimensionPixelSize(R.dimen.progress_button_size_normal)
                buttonTextSize = context.resources.getDimension(R.dimen.progress_button_size_small)
            }
        }
        binding.button.layoutParams = params
        binding.button.setPadding(paddingButton, 0, paddingButton, 0)
        binding.button.textSize = pixelsToSp(buttonTextSize)
    }

    private fun removeButtonPadding() {
        binding.button.setPadding(0, 0, 0, 0)
    }

    private fun setPaddingIconButton() {
        if (buttonDrawablePadding != 0) {
            binding.button.compoundDrawablePadding =
                resources.getDimensionPixelOffset(buttonDrawablePadding)
        }
    }

    private fun setUpIcon() {
        try {
            if (buttonIconRight != 0) {
                binding.button.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    AppCompatResources.getDrawable(context, buttonIconRight),
                    null
                )
            }
            if (buttonIconLeft != 0) {
                binding.button.setCompoundDrawablesWithIntrinsicBounds(
                    AppCompatResources.getDrawable(context, buttonIconLeft),
                    null,
                    null,
                    null
                )
            }
        } catch (ex: Exception) {
            //Do Nothing
        }
    }

    private fun setUpStylePrimaryButton() {
        setColorTextAndProgress(
            textColor = context.getColor(R.color.N0),
            progressColor = context.getColor(R.color.N0)
        )
        backgroundButtonDefault(R.drawable.ui_kit_selector_primary_button)
    }

    private fun setUpButtonSecondary() {
        setColorTextAndProgress(
            textColor = context.getColor(R.color.B600),
            progressColor = context.getColor(R.color.B600)
        )
        backgroundButtonDefault(background = R.drawable.ui_kit_selector_secondary_button)
    }

    private fun setUpButtonSubtle() {
        setColorTextAndProgress(
            textColor = context.getColor(R.color.N700),
            progressColor = context.getColor(R.color.N700)
        )
        backgroundButtonDefault(background = R.drawable.ui_kit_selector_subtle_button)
    }

    private fun setUpButtonText() {
        binding.button.setTextColor(context.getColor(R.color.B600))
        backgroundButtonDefault(background = R.drawable.ui_kit_shape_text_default_button)
        removeButtonPadding()
    }

    private fun backgroundButtonDefault(@DrawableRes background: Int) {
        binding.button.background = AppCompatResources.getDrawable(context, background)
        binding.progress.visibility = View.GONE
        binding.button.isEnabled = true
    }

    private fun backgroundButtonDisabled(@DrawableRes background: Int, @ColorInt textColor: Int) {
        binding.button.background = AppCompatResources.getDrawable(context, background)
        binding.button.setTextColor(textColor)
    }

    private fun backgroundButtonLoading(
        @DrawableRes background: Int,
        @ColorInt backgroundTextColor: Int
    ) {
        binding.button.background = AppCompatResources.getDrawable(context, background)
        binding.button.setTextColor(backgroundTextColor)
        binding.button.isEnabled = false
        binding.progress.visibility = View.VISIBLE
    }

    private fun setColorTextAndProgress(@ColorInt textColor: Int, @ColorInt progressColor: Int) {
        binding.button.setTextColor(textColor)
        binding.progress.indeterminateTintList = ColorStateList.valueOf(progressColor)
    }

    private fun pixelsToSp(px: Float) = px / context.resources.displayMetrics.scaledDensity

}

fun interface ProgressButtonListener {
    fun onClick(view: ProgressButton)
}

enum class ProgressButtonStyle {
    PRIMARY,
    SECONDARY,
    SUBTLE,
    TEXT
}

enum class ProgressButtonSize {
    LARGE,
    MEDIUM,
    SMALL,
    NORMAL
}
