package com.example.securecipher.algorithms

object Hill_Cipher {

    // Function to calculate the greatest common divisor
    fun gcd(a: Int, b: Int): Int {
        if (b == 0) {
            return a
        } else {
            return gcd(b, a % b)
        }
    }

    // Function to encrypt the text
    fun encrypt(plaintext: String, key: Array<IntArray>): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var plaintext = plaintext.replace(" ", "").toUpperCase()
        val n = key.size
        plaintext += "X".repeat((n - plaintext.length % n) % n)  // Padding with 'X' if necessary
        var ciphertext = ""

        for (i in 0 until plaintext.length step n) {
            val chunk = plaintext.substring(i, i + n)
            val chunkIndices = chunk.map { alphabet.indexOf(it) }
            val transformedChunk = IntArray(n)
            for (row in 0 until n) {
                for (col in 0 until n) {
                    transformedChunk[row] += key[row][col] * chunkIndices[col]
                    transformedChunk[row] %= 26
                }
            }
            ciphertext += transformedChunk.joinToString("") { alphabet[it].toString() }
        }

        return ciphertext
    }

    // Calculate the modular multiplicative inverse of a number modulo m
    fun modInverse(a: Int, m: Int): Int? {
        for (x in 1 until m) {
            if ((a * x) % m == 1) {
                return x
            }
        }
        return null
    }

    // Function to decrypt the text
    fun decrypt(ciphertext: String, key: Array<IntArray>): String? {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val det = key[0][0] * key[1][1] - key[0][1] * key[1][0]  // Calculate the determinant of the key matrix
        println(det)
        // Ensure the determinant is relatively prime to 26
        if (gcd(det, 26) != 1) {
            println("Key is not invertible. Decryption not possible!")
            return null
        }

        val n = key.size
        val inverseDet = modInverse(det, 26)

        val adjugate = arrayOf(intArrayOf(key[1][1], -key[0][1]), intArrayOf(-key[1][0], key[0][0]))

        // Perform matrix multiplication: det * adjugate
        val inverseKey = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                inverseKey[i][j] = (inverseDet!! * adjugate[i][j]) % 26
                if (inverseKey[i][j] < 0) {
                    inverseKey[i][j] += 26
                }
            }
        }

        var plaintext = ""
        for (i in 0 until ciphertext.length step n) {
            val chunk = ciphertext.substring(i, i + n)
            val chunkIndices = chunk.map { alphabet.indexOf(it) }
            if (chunkIndices.contains(-1)) {
                println("Invalid ciphertext. Only English alphabets are allowed!")
                return null
            }
            val transformedChunk = IntArray(n)
            for (row in 0 until n) {
                for (col in 0 until n) {
                    transformedChunk[row] += inverseKey[row][col] * chunkIndices[col]
                    transformedChunk[row] %= 26
                }
            }
            plaintext += transformedChunk.joinToString("") { alphabet[it].toString() }
        }

        return plaintext
    }

}

