package com.refresh.kitkotie.datam

data class Alarm(
    var time: String,  // For simplicity, use String, but you can use Date or Calendar
    var ringtoneName: String,
    var isOn: Boolean
)