package com.example.securecipher.algorithms

object RowColumnTransportation {

    fun columnarEncrypt(plainText: String, key: String, ignoreSpecialChars: Boolean = true, padding: Char = '_'): String {
        if (!key.all { it.isLetterOrDigit() }) {
            throw IllegalArgumentException("Invalid key: $key")
        }

        var processedPlainText = plainText
        if (ignoreSpecialChars) {
            processedPlainText = plainText.filter { it.isLetterOrDigit() }
        }
        processedPlainText = processedPlainText.toUpperCase()
        val processedKey = key.toUpperCase()
        val size = processedKey.length
        val paddingNeeded = size - processedPlainText.length % size
        processedPlainText += padding.toString().repeat(if (processedPlainText.length % size != 0) paddingNeeded else 0)

        val sortedKey = processedKey.toList().sorted().joinToString("")
        val matrix = mutableListOf<List<Char>>()
        processedPlainText.chunked(size).forEach { substr ->
            val row = MutableList(size) { ' ' }
            val dictKey = sortedKey.associateBy({ it }, { sortedKey.indexOf(it) }).toMutableMap()
            processedKey.forEachIndexed { idx, c ->
                row[dictKey[c]!!] = substr[idx]
                dictKey[c] = dictKey[c]!! + 1
            }
            matrix.add(row)
        }

        val transposedMatrix = matrix.transpose()
        val cipherText = transposedMatrix.flatten().joinToString("")

        return cipherText
    }

    fun columnarDecrypt(cipherText: String, key: String, ignoreSpecialChars: Boolean = true, padding: Char = '_'): String {
        if (!key.all { it.isLetterOrDigit() }) {
            throw IllegalArgumentException("Invalid key: $key")
        }

        var processedCipherText = cipherText
        if (ignoreSpecialChars) {
            processedCipherText = cipherText.filter { it.isLetterOrDigit() }
        }
        processedCipherText = processedCipherText.toUpperCase()
        val processedKey = key.toUpperCase()
        val cipherTextLen = processedCipherText.length
        val size = processedKey.length

        val numberOfCols = (cipherTextLen + size - 1) / size
        val transposedMatrix = MutableList(size) { MutableList(numberOfCols) { ' ' } }
        val numberOfPads = if (cipherTextLen % size != 0) size - cipherTextLen % size else 0

        val sortedKey = processedKey.toList().sorted().joinToString("")
        val dictKey = sortedKey.associateBy({ it }, { sortedKey.indexOf(it) }).toMutableMap()
        val newKey = processedKey.map { dictKey[it]!! }
        val newDictKey = newKey.withIndex().associate { it.value to it.index }

        var i = 0
        var portion = 0
        while (i < cipherTextLen) {
            val substr: String
            if (portion >= size - numberOfPads && padding == '_') {
                substr = processedCipherText.substring(i, i + numberOfCols - 1)
                i += numberOfCols - 1
            } else {
                substr = processedCipherText.substring(i, i + numberOfCols)
                i += numberOfCols
            }
            val row = substr.toCharArray().toMutableList()
            if (padding == '_') {
                row.add(' ')
            }
            transposedMatrix[newDictKey[portion]!!] = row
            portion++
        }

        val matrix = transposedMatrix.transpose()
        var plainText = matrix.flatten().joinToString("")
        for (i in cipherTextLen - 1 downTo 0) {
            if (plainText[i] != padding) {
                plainText = plainText.substring(0, i + 1)
                break
            }
        }

        return plainText
    }

    fun <T> List<List<T>>.transpose(): List<List<T>> {
        val transposed = mutableListOf<MutableList<T>>()
        if (this.isEmpty()) return transposed
        for (i in this[0].indices) {
            transposed.add(mutableListOf())
        }
        for (outer in this) {
            for ((inner, item) in outer.withIndex()) {
                transposed[inner].add(item)
            }
        }
        return transposed
    }
}