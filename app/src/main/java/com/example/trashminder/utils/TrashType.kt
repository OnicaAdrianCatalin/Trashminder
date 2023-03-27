package com.example.trashminder.utils

import com.example.trashminder.R

enum class TrashType {
    Plastic,
    Metal,
    Paper,
    Glass,
    Residual
}

fun TrashType.toResourceId(): Int {
    return when (this) {
        TrashType.Paper -> R.string.trash_type_paper
        TrashType.Metal -> R.string.trash_type_metal
        TrashType.Glass -> R.string.trash_type_glass
        TrashType.Residual -> R.string.trash_type_residual
        TrashType.Plastic -> R.string.trash_type_plastic
    }
}
