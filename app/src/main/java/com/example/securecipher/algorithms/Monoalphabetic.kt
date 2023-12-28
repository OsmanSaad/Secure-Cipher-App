package com.example.securecipher.algorithms

object Monoalphabetic {
    fun encrypt(plaintext: String, cipher: String): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val map = alphabet.zip(cipher).toMap()
        return plaintext.map { map[it] ?: it }.joinToString("")
    }
    fun decrypt(encryptedText: String, cipher: String): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val map = cipher.zip(alphabet).toMap()
        return encryptedText.map { map[it] ?: it }.joinToString("")
    }
}