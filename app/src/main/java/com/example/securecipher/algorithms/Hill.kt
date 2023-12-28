package com.example.securecipher.algorithms

import java.lang.Math.sqrt

object Hill {

    fun parseKeyMatrix(keyString: String): Array<IntArray>? {
        val elements = keyString.trim().split("\\s+")

        val size = sqrt(elements.size.toDouble()).toInt()

        return if (size * size == elements.size) {
            Array(size) { row ->
                IntArray(size) { col ->
                    elements[row * size + col].toIntOrNull() ?: 0
                }
            }
        } else {
            null
        }
    }

    fun isSquareMatrix(matrix: Array<IntArray>): Boolean {
        val rows = matrix.size
        return rows > 0 && matrix.all { it.size == rows }
    }

    fun encrypt(plaintext: String, key: Array<IntArray>): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val n = key.size
        val plaintextUpperCase = plaintext.replace("[^A-Za-z]".toRegex(), "").toUpperCase()

        // Pad the plaintext with 'X' if necessary
        val paddedText = if (plaintextUpperCase.length % n != 0) {
            plaintextUpperCase.padEnd(plaintextUpperCase.length + n - plaintextUpperCase.length % n, 'X')
        } else {
            plaintextUpperCase
        }

        val result = StringBuilder()

        for (i in 0 until paddedText.length step n) {
            val chunk = paddedText.substring(i, i + n)
            val chunkIndices = chunk.map { alphabet.indexOf(it) }

            val transformedChunk = IntArray(n)
            for (row in 0 until n) {
                for (col in 0 until n) {
                    transformedChunk[row] += key[row][col] * chunkIndices[col]
                    transformedChunk[row] %= 26
                }
                result.append(alphabet[transformedChunk[row]])
            }
        }

        return result.toString()
    }

    fun modInverse(a: Int, m: Int): Int {
        for (x in 1 until m) {
            if ((a * x) % m == 1) {
                return x
            }
        }
        return 1
    }

    fun decrypt(ciphertext: String, key: Array<IntArray>): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val n = key.size
        val det = key[0][0] * key[1][1] - key[0][1] * key[1][0]

        if (det == 0 || gcd(det, 26) != 1) {
            throw IllegalArgumentException("Invalid key. Key is not invertible.")
        }

        val detInverse = modInverse(det, 26)

        val adjugate = arrayOf(intArrayOf(key[1][1], -key[0][1]), intArrayOf(-key[1][0], key[0][0]))

        val inverseKey = Array(n) { IntArray(n) }

        for (i in 0 until n) {
            for (j in 0 until n) {
                inverseKey[i][j] = (detInverse * adjugate[i][j]) % 26
                if (inverseKey[i][j] < 0) {
                    inverseKey[i][j] += 26
                }
            }
        }

        val result = StringBuilder()

        for (i in 0 until ciphertext.length step n) {
            val chunk = ciphertext.substring(i, i + n)
            val chunkIndices = chunk.map { alphabet.indexOf(it) }

            val transformedChunk = IntArray(n)
            for (row in 0 until n) {
                for (col in 0 until n) {
                    transformedChunk[row] += inverseKey[row][col] * chunkIndices[col]
                    transformedChunk[row] %= 26
                }
                result.append(alphabet[transformedChunk[row]])
            }
        }

        return result.toString()
    }

    fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }
}