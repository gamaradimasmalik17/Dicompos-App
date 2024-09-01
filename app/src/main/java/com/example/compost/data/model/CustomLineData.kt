package com.example.compost.data.model

import com.himanshoe.charty.common.ChartData

class CustomLineData (override val yValue: Float, override val xValue: Any, val label: String) : ChartData {
    override val chartString: String
        get() = "Line Chart"
}