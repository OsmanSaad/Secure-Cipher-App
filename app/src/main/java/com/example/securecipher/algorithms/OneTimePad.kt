package com.example.securecipher.algorithms

object OneTimePad {

    fun oneTimePad(plain: String, key: String, flag: Boolean): String {
        val text = prepareTextAndKey(plain)
        val newkey = prepareTextAndKey(key)
        var result = ""
        for (i in text.indices) {
            val char = text[i]
            result += if (flag) {
                ((char.toInt() - 'a'.toInt() + (newkey[i % key.length].toInt() - 'a'.toInt())) % 26 + 'a'.toInt()).toChar()
            } else {
                ((char.toInt() - newkey[i % key.length].toInt() + 26) % 26 + 'a'.toInt()).toChar()
            }
        }
        return result
    }

    private fun prepareTextAndKey(text:String):String{
        val newtext = text.lowercase().replace(" ","")
        return newtext
    }
}