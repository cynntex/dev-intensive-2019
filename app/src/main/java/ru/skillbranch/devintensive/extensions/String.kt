package ru.skillbranch.devintensive.extensions

fun String.truncate (lengtString: Int = 16):String {
    return if (this.trim().length > lengtString)
        "${this.substring(0, lengtString).trim()}..."
    else
        this.trim()

}

fun String.stripHtml () = this.replace(Regex("(\\<(/?[^>]+)>)|&.+;"), "").replace(Regex("\\s+"), " ")
