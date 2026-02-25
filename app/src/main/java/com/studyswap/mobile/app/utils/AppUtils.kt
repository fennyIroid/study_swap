package com.studyswap.mobile.app.utils

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast

object AppUtils {

    fun showErrorMessage(context: Context, errorMsg: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toasty.error(context, errorMsg, Toast.LENGTH_SHORT, true).show()
        }
    }

    fun showSuccessMessage(context: Context, message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show()
        }
    }

    fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
        this then Modifier.clickable(
            enabled = enabled,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    }
}
