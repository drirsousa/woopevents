package br.com.rosait.woopevents.common.extension

import java.text.SimpleDateFormat
import java.util.*


fun Long.getDate() : String {
    val date = Date(this)
    val locale = Locale("pt", "BR")
    val format = SimpleDateFormat("dd/MM/yyyy", locale)
    format.timeZone = TimeZone.getTimeZone("GMT")
    return format.format(date)
}