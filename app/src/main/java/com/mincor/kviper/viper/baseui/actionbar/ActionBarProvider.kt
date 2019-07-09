package com.mincor.kviper.viper.baseui.actionbar

import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar


interface ActionBarProvider {
    fun getSupportActionBar():ActionBar?
    fun setSupportActionBar(toolbar: Toolbar?)
}
