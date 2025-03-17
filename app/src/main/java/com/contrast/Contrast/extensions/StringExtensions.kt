package com.contrast.Contrast.extensions

import android.text.Editable
// tránh null
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
