package com.example.trashminder.utils

import android.content.Context
import com.example.trashminder.R

enum class TrashType {
    Plastic,
    Metals,
    Paper,
    Glass,
    Residuals
}

fun TrashType.toLocalizedString(context: Context): String {
    return when (this) {
        TrashType.Paper -> context.getString(R.string.trash_type_paper)
        TrashType.Metals -> context.getString(R.string.trash_type_metals)
        TrashType.Glass -> context.getString(R.string.trash_type_glass)
        TrashType.Residuals -> context.getString(R.string.trash_type_residuals)
        TrashType.Plastic -> context.getString(R.string.trash_type_plastic)
    }
}

fun String.toTrashType(context: Context): TrashType? {
    return when(this) {
        context.getString(R.string.trash_type_paper) -> TrashType.Paper
        context.getString(R.string.trash_type_metals) ->  TrashType.Metals
        context.getString(R.string.trash_type_glass) ->  TrashType.Glass
        context.getString(R.string.trash_type_residuals) ->  TrashType.Residuals
        context.getString(R.string.trash_type_plastic) ->  TrashType.Plastic
        else -> null
    }
}
