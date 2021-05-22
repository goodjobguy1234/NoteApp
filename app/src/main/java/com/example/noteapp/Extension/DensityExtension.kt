package com.example.noteapp.Extension

import android.content.res.Resources

    fun Int.dp (): Int = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    fun Float.dp (): Int = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
