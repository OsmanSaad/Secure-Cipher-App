package com.example.securecipher.algorithms
object CaesarCipher {
    fun encrypt(plainText: String, shift: Int): String {
        val result = StringBuilder()

        for (char in plainText) {
            if (char.isLetter()) {
                val base = if (char.isUpperCase()) 'A' else 'a'
                val encryptedChar = ((char.toInt() - base.toInt() + shift) % 26 + 26) % 26 + base.toInt()
                result.append(encryptedChar.toChar())
            } else {
                result.append(char)
            }
        }

        return result.toString()
    }

    fun decrypt(text: String, shift: Int): String {
        return encrypt(text, 26 - shift)
    }
}