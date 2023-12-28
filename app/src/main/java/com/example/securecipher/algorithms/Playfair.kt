package com.example.securecipher.algorithms

object Playfair {
    fun prepareInput(text: String): String {
        return text.replace(" ", "").uppercase().replace("J", "I")
    }

    fun generateKey(key: String): List<List<Char>> {
        val keySet = mutableSetOf<Char>()
        val playfairMatrix = List(5) { MutableList(5) { ' ' } }
        var i = 0
        var j = 0

        for (letter in prepareInput(key)) {
            if (letter !in keySet) {
                playfairMatrix[i][j] = letter
                keySet.add(letter)
                j++
                if (j == 5) {
                    i++
                    j = 0
                }
            }
        }

        for (letter in "ABCDEFGHIJKLMNOPQRSTUVWXYZ".filter { it != 'J' && it !in keySet }) {
            playfairMatrix[i][j] = letter
            keySet.add(letter)
            j++
            if (j == 5) {
                i++
                j = 0
            }
        }

        return playfairMatrix
    }

    fun findCharPosition(matrix: List<List<Char>>, char: Char): Pair<Int, Int> {
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (matrix[i][j] == char) {
                    return Pair(i, j)
                }
            }
        }
        throw IllegalArgumentException("Character not found in the matrix: $char")
    }

    fun playfairEncrypt(plaintext: String, key: String): String {
        val matrix = generateKey(key)
        var preparedPlaintext = prepareInput(plaintext)
        val cipherText = StringBuilder()

        var i = 0
        while (i < preparedPlaintext.length) {
            if (i == preparedPlaintext.length - 1) {
                preparedPlaintext += "X"
            } else if (preparedPlaintext[i] == preparedPlaintext[i + 1]) {
                preparedPlaintext = preparedPlaintext.substring(0, i + 1) + "X" + preparedPlaintext.substring(i + 1)
            }
            i += 2
        }

        for (i in 0 until preparedPlaintext.length step 2) {
            val char1 = preparedPlaintext[i]
            val char2 = preparedPlaintext[i + 1]
            val (row1, col1) = findCharPosition(matrix, char1)
            val (row2, col2) = findCharPosition(matrix, char2)

            if (row1 == row2) {
                cipherText.append(matrix[row1][(col1 + 1) % 5])
                cipherText.append(matrix[row2][(col2 + 1) % 5])
            } else if (col1 == col2) {
                cipherText.append(matrix[(row1 + 1) % 5][col1])
                cipherText.append(matrix[(row2 + 1) % 5][col2])
            } else {
                cipherText.append(matrix[row1][col2])
                cipherText.append(matrix[row2][col1])
            }
        }

        return cipherText.toString()
    }

    fun playfairDecrypt(cipherText: String, key: String): String {
        val matrix = generateKey(key)
        val preparedCipherText = prepareInput(cipherText)
        val plaintext = StringBuilder()

        for (i in 0 until preparedCipherText.length step 2) {
            val char1 = preparedCipherText[i]
            val char2 = preparedCipherText[i + 1]
            val (row1, col1) = findCharPosition(matrix, char1)
            val (row2, col2) = findCharPosition(matrix, char2)

            if (row1 == row2) {
                plaintext.append(matrix[row1][(col1 - 1 + 5) % 5]) // Shift left instead of right
                plaintext.append(matrix[row2][(col2 - 1 + 5) % 5])
            } else if (col1 == col2) {
                plaintext.append(matrix[(row1 - 1 + 5) % 5][col1]) // Shift up instead of down
                plaintext.append(matrix[(row2 - 1 + 5) % 5][col2])
            } else {
                plaintext.append(matrix[row1][col2])
                plaintext.append(matrix[row2][col1])
            }
        }

        return plaintext.toString()
    }
}