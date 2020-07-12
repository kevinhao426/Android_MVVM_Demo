package com.kehao.myapplication.utils

import android.content.Context
import android.widget.Toast


fun showShortToast(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}