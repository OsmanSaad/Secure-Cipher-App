package com.example.securecipher.algorithms

object RailFance {

    fun encryptRailFence(text: String, key: Int): String {
        val rail = Array(key) { "" }
        val plaintext = removeSpace(text)
        var dirDown = false
        var row = 0

        for (i in plaintext.indices) {
            if (row == 0 || row == key - 1) {
                dirDown = !dirDown
            }

            rail[row] = rail[row] + plaintext[i]

            row += if (dirDown) 1 else -1
        }

        return rail.joinToString("")
    }

    fun decryptRailFence(text: String, key: Int): String {
        val cipher = removeSpace(text)
        val rail = Array(key) { CharArray(text.length) { '\n' } }
        var dirDown = false
        var row = 0
        var col = 0

        for (i in cipher.indices) {
            if (row == 0 || row == key - 1) {
                dirDown = !dirDown
            }

            rail[row][col] = '*'
            col++

            row += if (dirDown) 1 else -1
        }

        var index = 0
        for (i in 0 until key) {
            for (j in cipher.indices) {
                if (rail[i][j] == '*') {
                    rail[i][j] = text[index]
                    index++
                }
            }
        }

        var result = ""
        dirDown = false
        row = 0
        col = 0
        for (i in cipher.indices) {
            if (row == 0 || row == key - 1) {
                dirDown = !dirDown
            }

            if (rail[row][col] != '*') {
                result += rail[row][col]
                col++
            }

            row += if (dirDown) 1 else -1
        }

        return result
    }

    fun removeSpace(text:String):String{
        return  text.replace(" ","")
    }
}