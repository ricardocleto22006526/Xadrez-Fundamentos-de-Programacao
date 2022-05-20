fun buildMenu(): String {
    return "1-> Start New Game;\n2-> Exit Game.\n"
}

fun encortarFimBranco(mostrarPeca: String, countLinhas: Int, numLines: Int, showPieces: Boolean, startWhite: String, end: String): String {
    return "$startWhite $mostrarPeca $end"
}

fun encortarFimCinzento(mostrarPeca: String, countLinhas: Int, numLines: Int, showPieces: Boolean, startGrey: String, end: String): String {
    return "$startGrey $mostrarPeca $end"
}

fun checkIsNumber(number: String): Boolean {
    return number.toIntOrNull() != null
}

fun checkName(nome: String): Boolean {
    var count = 0
    var espaco = 0

    while (count < nome.length - 1) {
        if (nome[count] == ' ') {
            espaco++
            if ((nome[0] in 'A'..'Z') && (nome[count + 1] in 'A'..'Z') && espaco == 1) {
                return true
            }
        }
        count++
    }
    return false
}

fun showChessLegendOrPieces(message: String): Boolean? {
    if (message == "y" || message == "Y") {
        return true
    }
    if (message == "n" || message == "N") {
        return false
    }
    return null
}

fun createTotalPiecesAndTurn(numColumns: Int, numLines: Int): Array<Int?> {

    return if (numColumns == 8 && numLines == 8) {
        arrayOf(16, 16, 0)
    } else if (numColumns == 7 && numLines == 7) {
        arrayOf(14, 14, 0)
    } else if (numColumns == 6 && numLines == 6) {
        arrayOf(12, 12, 0)
    } else if (numColumns == 6 && numLines == 7) {
        arrayOf(12, 12, 0)
    } else if (numColumns == 4 && numLines == 4) {
        arrayOf(2, 2, 0)
    } else {
        arrayOf()
    }
}

fun convertStringToUnicode(piece: String, color: String): String {

    when {
        piece == "P" && color == "w" -> return "\u2659"
        piece == "P" && color == "b" -> return "\u265F"
        piece == "H" && color == "w" -> return "\u2658"
        piece == "H" && color == "b" -> return "\u265E"
        piece == "K" && color == "w" -> return "\u2654"
        piece == "K" && color == "b" -> return "\u265A"
        piece == "T" && color == "w" -> return "\u2656"
        piece == "T" && color == "b" -> return "\u265C"
        piece == "B" && color == "w" -> return "\u2657"
        piece == "B" && color == "b" -> return "\u265D"
        piece == "Q" && color == "w" -> return "\u2655"
        piece == "Q" && color == "b" -> return "\u265B"
    }
    return ""
}

fun getCoordinates(readText: String?): Pair<Int, Int>? {
    var letraCoord = 0

    if (readText == null || readText.length != 2 || readText[0].toInt() !in 48..57 && (readText[1] !in 'a'..'h' || readText[1] !in 'A'..'H')) {
        return null
    } else {
        when (readText[1]) {
            'A', 'a' -> letraCoord = 1
            'B', 'b' -> letraCoord = 2
            'C', 'c' -> letraCoord = 3
            'D', 'd' -> letraCoord = 4
            'E', 'e' -> letraCoord = 5
            'F', 'f' -> letraCoord = 6
            'G', 'g' -> letraCoord = 7
            'H', 'h' -> letraCoord = 8
        }
        return Pair(readText[0].toInt() - 48, letraCoord)
    }
}

fun checkRightPieceSelected(pieceColor: String, turn: Int): Boolean {
    if (turn == 0 && pieceColor == "w") {
        return true
    } else if (turn == 1 && pieceColor == "b") {
        return true
    }
    return false
}

fun isCoordinateInsideChess(coord: Pair<Int, Int>, numColumns: Int, numLines: Int): Boolean {
    return when {
        (coord.first in 1..numLines && coord.second in 1..numColumns) -> true
        else -> false
    }
}


fun isValidTargetPiece(currentSelectedPiece: Pair<String, String>, currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,
                       pieces: Array<Pair<String,String>?>, numColumns: Int, numLines: Int): Boolean {
    return when {
        currentSelectedPiece.first == "H" && isHorseValid(currentCoord, targetCoord, pieces, numColumns, numLines) -> true
        currentSelectedPiece.first == "K" && isKingValid(currentCoord, targetCoord, pieces, numColumns, numLines) -> true
        currentSelectedPiece.first == "B" && isBishopValid(currentCoord, targetCoord, pieces, numColumns, numLines) -> true
        currentSelectedPiece.first == "T" && isTowerValid(currentCoord, targetCoord, pieces, numColumns, numLines) -> true
        currentSelectedPiece.first == "Q" && isQueenValid(currentCoord, targetCoord, pieces, numColumns, numLines) -> true
        currentSelectedPiece.first == "P" && isKnightValid(currentCoord, targetCoord, pieces, numColumns, numLines) -> true
        else -> false
    }
}


fun movePiece(pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int, currentCoord: Pair<Int, Int>,
              targetCoord: Pair<Int, Int>,totalPiecesAndTurn: Array<Int>): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)
    val currentSelectedPiece = pieces[pecaCoordMov]

    if (isValidTargetPiece(currentSelectedPiece!!, currentCoord, targetCoord, pieces, numColumns, numLines)
        && checkRightPieceSelected(pieces[pecaCoordMov]?.second.toString(), totalPiecesAndTurn[2])
    ) {

        if (pieces[targetPecaMov] != null && totalPiecesAndTurn[2] == 0) {
            totalPiecesAndTurn[1]--
        } else if (pieces[targetPecaMov] != null && totalPiecesAndTurn[2] == 1) {
            totalPiecesAndTurn[0]--
        }

        pieces[targetPecaMov] = pieces[pecaCoordMov]
        pieces[pecaCoordMov] = null

        if (pieces[targetPecaMov] != null) {

            if (totalPiecesAndTurn[2] == 0) {
                totalPiecesAndTurn[2] = 1
            } else {
                totalPiecesAndTurn[2] = 0
            }
        }
        return true
    }
    return false

}

fun startNewGame(whitePlayer: String, blackPlayer: String, pieces: Array<Pair<String, String>?>,totalPiecesAndTurn: Array<Int?>,
                 numColumns: Int, numLines: Int, showLegend: Boolean = false, showPieces: Boolean = false) {
    val player1Frase = "$whitePlayer, choose a piece (e.g 2D).\nMenu-> m;\n";val player2Frase = "$blackPlayer, choose a piece (e.g 2D).\nMenu-> m;\n"
    val player1Target = "$whitePlayer, choose a target piece (e.g 2D).\nMenu-> m;\n";
    val player2Target = "$blackPlayer, choose a target piece (e.g 2D).\nMenu-> m;\n";val respostaInvalida = "Invalid response.";
    var cor = "";var jogada1Target = "";var jogada2Target = ""

    do {
        println(buildBoard(numColumns, numLines, showLegend, showPieces, pieces))
        if (totalPiecesAndTurn[2] == 0) {
            println(player1Frase);val jogada1 = readLine().toString()
            if (jogada1 == "m" || jogada1 == "M") {return}
            else {
                if (jogada1.length == 2) {
                    val pecaCoordMov = ((((getCoordinates(jogada1)!!.first - 1) * numLines) + getCoordinates(jogada1)!!.second) - 1)
                    if (pecaCoordMov in 0..(numLines * numColumns - 1) && pieces[pecaCoordMov] == null) {cor = ""
                    } else if (pecaCoordMov in 0..(numLines * numColumns - 1)) {cor = pieces[pecaCoordMov]!!.second}
                    if ((isCoordinateInsideChess(getCoordinates(jogada1)!!, numColumns, numLines)
                         && jogada1.length == 2 && checkRightPieceSelected(cor, totalPiecesAndTurn[2]!!))
                        || (jogada1[0].toString() in "1"..numLines.toString()) && (jogada1[1].toString() in "A"..(numColumns + 64).toString()
                        || jogada1[1].toString() in "a"..(numColumns + 96).toString())) {
                        if (isCoordinateInsideChess(getCoordinates(jogada1)!!, numColumns, numLines)
                            && checkRightPieceSelected(cor, totalPiecesAndTurn[2]!!)) {
                            println(player1Target)
                            jogada1Target = readLine().toString()
                            if (jogada1Target == "m" || jogada1Target == "M") {return}
                            if (isCoordinateInsideChess(getCoordinates(jogada1Target)!!, numColumns, numLines)
                                && jogada1Target.length == 2 || jogada1Target[0].toString() in "1"..numLines.toString()
                                && (jogada1Target[1].toString() in "A"..(numColumns + 64).toString()
                                || jogada1Target[1].toString() in "a"..(numColumns + 96).toString())) {
                                if (!movePiece(pieces, numColumns, numLines, getCoordinates(jogada1)!!,getCoordinates(jogada1Target)!!,
                                    totalPiecesAndTurn.requireNoNulls())) {println(respostaInvalida)}
                            } else {println(respostaInvalida)}
                        } else {println(respostaInvalida)}
                    } else {println(respostaInvalida)}
                } else {println(respostaInvalida)}
            }
        } else {
            println(player2Frase);val jogada2 = readLine().toString()
            if (jogada2 == "m" || jogada2 == "M") {return}
            else {
                if (jogada2.length == 2) {
                    val pecaCoordMov = ((((getCoordinates(jogada2)!!.first - 1) * numLines) + getCoordinates(jogada2)!!.second) - 1)
                    if (pecaCoordMov in 0..(numLines * numColumns - 1) && pieces[pecaCoordMov] == null) {cor = ""
                    } else if (pecaCoordMov in 0..(numLines * numColumns - 1)) {cor = pieces[pecaCoordMov]!!.second}
                    if (isCoordinateInsideChess(getCoordinates(jogada2)!!, numColumns, numLines)
                        && jogada2.length == 2 && checkRightPieceSelected(cor, totalPiecesAndTurn[2]!!)
                        || jogada2[0].toString() in "1"..numLines.toString()
                        && (jogada2[1].toString() in "A"..(numColumns + 64).toString()
                        || jogada2[1].toString() in "a"..(numColumns + 96).toString())) {
                        if (isCoordinateInsideChess(getCoordinates(jogada2)!!, numColumns, numLines)
                            && checkRightPieceSelected(cor, totalPiecesAndTurn[2]!!)) {
                            println(player2Target)
                            jogada2Target = readLine().toString()
                            if (jogada2Target == "m" || jogada2Target == "M") {return}
                            if (isCoordinateInsideChess(getCoordinates(jogada2Target)!!, numColumns, numLines)
                                && jogada2Target.length == 2 || jogada2Target[0].toString() in "1"..numLines.toString()
                                && (jogada2Target[1].toString() in "A"..(numColumns + 64).toString()
                                || jogada2Target[1].toString() in "a"..(numColumns + 96).toString())) {
                                if (!movePiece(pieces, numColumns, numLines, getCoordinates(jogada2)!!,getCoordinates(jogada2Target)!!,
                                    totalPiecesAndTurn.requireNoNulls())) {println(respostaInvalida)}
                            } else {println(respostaInvalida)}
                        } else {println(respostaInvalida)}
                    } else {println(respostaInvalida)}
                } else {println(respostaInvalida)}
            }
        }
    } while (totalPiecesAndTurn[0] != 0 && totalPiecesAndTurn[1] != 0)

    if (totalPiecesAndTurn[0] == 0) {println("Congrats! $blackPlayer wins!")
    } else if (totalPiecesAndTurn[1] == 0) {println("Congrats! $whitePlayer wins!")}
}

fun isHorseValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int
): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    return when {
        (currentCoord.first + 2 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first + 2 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 2 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 2 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first + 1 == targetCoord.first && currentCoord.second + 2 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first + 1 == targetCoord.first && currentCoord.second - 2 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 1 == targetCoord.first && currentCoord.second + 2 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 1 == targetCoord.first && currentCoord.second - 2 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        else -> false

    }
}

fun isKingValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int
): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    return when {
        (currentCoord.first + 1 == targetCoord.first && currentCoord.second == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 1 == targetCoord.first && currentCoord.second == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first + 1 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first + 1 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 1 == targetCoord.first && currentCoord.second - 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 1 == targetCoord.first && currentCoord.second + 1 == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        else -> false
    }
}

fun isTowerValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int
): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    return when {
        (currentCoord.first != targetCoord.first && currentCoord.second == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first == targetCoord.first && currentCoord.second != targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        else -> false
    }
}

fun isBishopValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int
): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)
    var count = 1
    val encortarBispo = (pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
            || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")

    while (count < numColumns) {
        when {
            (currentCoord.first + count == targetCoord.first
                    && currentCoord.second + count == targetCoord.second) && encortarBispo -> return true
            (currentCoord.first - count == targetCoord.first
                    && currentCoord.second - count == targetCoord.second) && encortarBispo -> return true
            (currentCoord.first - count == targetCoord.first &&
                    currentCoord.second + count == targetCoord.second) && encortarBispo -> return true
            (currentCoord.first + count == targetCoord.first &&
                    currentCoord.second - count == targetCoord.second) && encortarBispo -> return true
        }
        count++
    }
    return false
}

fun isQueenValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int
): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    return when {
        (isTowerValid(currentCoord, targetCoord, pieces, numColumns, numLines)
                || isBishopValid(currentCoord, targetCoord, pieces, numColumns, numLines)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b"))) -> true

        else -> false
    }
}

fun isKnightValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>, numColumns: Int, numLines: Int
): Boolean {
    val pecaCoordMov = ((((currentCoord.first - 1) * numLines) + currentCoord.second) - 1)
    val targetPecaMov = ((((targetCoord.first - 1) * numLines) + targetCoord.second) - 1)

    return when {
        (currentCoord.first + 1 == targetCoord.first && currentCoord.second == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        (currentCoord.first - 1 == targetCoord.first && currentCoord.second == targetCoord.second)
                && ((pieces[pecaCoordMov]?.second == "w" && pieces[targetPecaMov]?.second != "w")
                || (pieces[pecaCoordMov]?.second == "b" && pieces[targetPecaMov]?.second != "b")) -> true

        else -> false
    }
}

fun buildBoard(numColumns: Int, numLines: Int, showLegend: Boolean = false,showPieces: Boolean = false, pieces: Array<Pair<String, String>?>
): String {
    val esc: String = Character.toString(27);
    val end = "$esc[0m";val startBlue = "$esc[30;44m";val startGrey = "$esc[30;47m";val startWhite = "$esc[30;30m";var countColunas: Int
    var countLinhas = 0;var frase = "";var letra = 'A';var countpecas = 0;val azul = "$startBlue   $end";var mostrarPeca = ""
    val linhasAzuis = if (showLegend) 2 else 0;countLinhas = 0

    while (countLinhas < numLines + linhasAzuis) {
        countColunas = 0
        if (showLegend && (countLinhas == 0 || countLinhas == numLines + 1)) {
            while (countColunas < numColumns + 2) { //+2
                if (countLinhas == 0) {
                    if (countColunas == 0 || countColunas == numColumns + 1) { //+1
                        frase += azul
                    } else {
                        frase += "$startBlue $letra $end"
                        letra++
                    }
                } else if (countLinhas == numLines + 1) { //+1
                    frase += azul
                };countColunas++
            }
        }
        if (showLegend && countColunas == 0) {
            frase += "$startBlue $countLinhas $end"
        }
        while (countColunas < numColumns) {
            mostrarPeca = if (showPieces && pieces[countpecas] == null) {
                " "
            } else if (showPieces) {
                convertStringToUnicode(pieces[countpecas]?.first.toString(), pieces[countpecas]?.second.toString())
            } else {
                " "
            }
            if (showLegend) {
                frase += if (countColunas % 2 == 0) {
                    if (countLinhas % 2 == 0) { //!=
                        encortarFimBranco(mostrarPeca, countLinhas, numLines, showPieces, startGrey, end)
                    } else {
                        encortarFimCinzento(mostrarPeca, countLinhas, numLines, showPieces, startWhite, end)
                    }
                } else {
                    if (countLinhas % 2 == 1) { // !=
                        encortarFimCinzento(mostrarPeca, countLinhas, numLines, showPieces, startGrey, end)
                    } else {
                        encortarFimBranco(mostrarPeca, countLinhas, numLines, showPieces, startWhite, end)
                    }

                };countColunas++;countpecas++
            }
            if (!showLegend) {
                if (countColunas % 2 == 0) {
                    if (countLinhas % 2 == 0) { //!=
                        frase += encortarFimBranco(mostrarPeca, countLinhas, numLines, showPieces, startWhite, end)
                    } else {
                        frase += encortarFimCinzento(mostrarPeca, countLinhas, numLines, showPieces, startGrey, end)
                    }
                } else {
                    if (countLinhas % 2 == 1) { // !=
                        frase += encortarFimCinzento(mostrarPeca, countLinhas, numLines, showPieces, startWhite, end)
                    } else {
                        frase += encortarFimBranco(mostrarPeca, countLinhas, numLines, showPieces, startGrey, end)
                    }
                };countColunas++;countpecas++
            }
        }
        if (showLegend && countColunas == numColumns) {
            frase += azul
        };frase += "\n";countLinhas++
    }
    return frase
}

fun createInitialBoard(numColumns: Int, numLines: Int): Array<Pair<String, String>?> {
    if (numColumns == 8 && numLines == 8) {
        return arrayOf(
            Pair("T", "b"), Pair("H", "b"), Pair("B", "b"), Pair("Q", "b"), Pair("K", "b"), Pair("B", "b"), Pair("H", "b"),
            Pair("T", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            Pair("P", "b"), Pair("P", "b"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Pair("P", "w"),
            Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
            Pair("T", "w"), Pair("H", "w"), Pair("B", "w"), Pair("K", "w"), Pair("Q", "w"), Pair("B", "w"), Pair("H", "w"),
            Pair("T", "w")
        )
    }
    if (numColumns == 4 && numLines == 4) {
        return arrayOf(
            null, null, Pair("T", "b"), Pair("B", "b"),
            null, null, null, null, null, null, null, null,
            Pair("T", "w"), Pair("Q", "w"), null, null
        )
    }
    if (numColumns == 7 && numLines == 7) {
        return arrayOf(
            Pair("T", "b"), Pair("H", "b"), Pair("B", "b"), Pair("K", "b"), Pair("B", "b"), Pair("H", "b"),
            Pair("T", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            Pair("P", "b"), Pair("P", "b"), null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("T", "w"), Pair("H", "w"),
            Pair("B", "w"), Pair("K", "w"), Pair("B", "w"), Pair("H", "w"), Pair("T", "w")
        )
    }

    if (numColumns == 6 && numLines == 6) {
        return arrayOf(
            Pair("H", "b"), Pair("B", "b"), Pair("Q", "b"), Pair("K", "b"), Pair("B", "b"), Pair("T", "b"),
            Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            null, null, null, null, null, null, null, null, null, null, null, null, Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("H", "w"), Pair("B", "w"),
            Pair("K", "w"), Pair("Q", "w"), Pair("B", "w"), Pair("T", "w")
        )
    }

    if (numColumns == 6 && numLines == 7) {
        return arrayOf(
            Pair("T", "b"), Pair("B", "b"), Pair("Q", "b"), Pair("K", "b"), Pair("B", "b"),
            Pair("H", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"), Pair("P", "b"),
            Pair("P", "b"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"), Pair("P", "w"),
            Pair("P", "w"), Pair("T", "w"), Pair("B", "w"), Pair("K", "w"), Pair("Q", "w"), Pair("B", "w"),
            Pair("H", "w")
        )
    }
    return arrayOf()
}


fun cortaIsso(numColumns: String, numLines: String): Boolean {
    return !((numColumns == "8" && numLines == "8") || (numColumns == "7" && numLines == "7")
            || (numColumns == "6" && (numLines == "6") || (numLines == "7"))
            || (numColumns == "4" && numLines == "4"))
}

fun main() {
    println("Welcome to the Chess Board Game!")
    val respostaInvalida = "Invalid response.";
    var numColumns: String;
    var numLines: String;
    var error = true;
    var menu: Int? = 1
    while (menu != 2) {
        println(buildMenu()); menu = readLine()?.toIntOrNull()
        when (menu) {
            1 -> {
                var firstP = ""
                var secondP = ""
                while (!checkName(firstP)) {
                    println("First player name?\n")
                    firstP = readLine().toString()
                    if (!checkName(firstP)){println(respostaInvalida)}
                }
                while (!checkName(secondP)) {
                    println("Second player name?\n")
                    secondP = readLine().toString()
                    if (!checkName(secondP)) {println(respostaInvalida)}
                }
                do {
                    numColumns = ""
                    while (error) {
                        println("How many chess columns?\n")
                        numColumns = readLine().toString()
                        error = false
                        if (!checkIsNumber(numColumns) || numColumns.toInt() !in 4..8) {error = true;println(respostaInvalida)}
                    }
                    error = true; numLines = ""
                    while (error) {
                        println("How many chess lines?\n")
                        numLines = readLine().toString()
                        error = false
                        if (!checkIsNumber(numLines) || numColumns.toInt() !in 4..8) {error = true;println(respostaInvalida)}
                    };error = true
                    if (createTotalPiecesAndTurn(numColumns.toInt(), numLines.toInt()).isEmpty()) {println(respostaInvalida);error = true}
                } while (cortaIsso(numColumns, numLines))

                var showLegend = ""
                while (showChessLegendOrPieces(showLegend) == null) {
                    println("Show legend (y/n)?\n")
                    showLegend = readLine().toString()
                    if (showChessLegendOrPieces(showLegend) == null) {println(respostaInvalida)}
                }
                var showPieces = ""
                while (showChessLegendOrPieces(showPieces) == null) {
                    println("Show pieces (y/n)?\n")
                    showPieces = readLine().toString()
                    if (showChessLegendOrPieces(showPieces) == null) {println(respostaInvalida)}
                }
                val pieces = createInitialBoard(numColumns.toInt(), numLines.toInt())
                val nullOuNao = showChessLegendOrPieces(showLegend) ?: false
                val nullOuNao1 = showChessLegendOrPieces(showPieces) ?: false
                val totalPiecesAndTurn = createTotalPiecesAndTurn(numColumns.toInt(), numLines.toInt())
                startNewGame(firstP, secondP, pieces, totalPiecesAndTurn, numColumns.toInt(), numLines.toInt(), nullOuNao, nullOuNao1)
            }
            2 -> return
        }
    }
}