package com.wooftown.core

import com.wooftown.core.pieces.*
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test


val desk = ChessBoard(8)
fun movies(x: Int, y: Int) = desk[x, y]!!.getPossibleMoves(x, y).toSet()

fun squares(list: List<String>): Set<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (notation in list) {
        result.add(notation[0] - 'a' to 8 - notation[1].toString().toInt())
    }
    return result.toSet()
}

class PiecesTest {

    @Test
    fun toStringTest() {
        assertEquals("black_king", King(MyColor.BLACK,desk).toString())
        assertEquals("black_knight", Knight(MyColor.BLACK,desk).toString())
        assertEquals("black_pawn", Pawn(MyColor.BLACK,desk).toString())
        assertEquals("black_queen", Queen(MyColor.BLACK,desk).toString())
        assertEquals("black_rook", Rook(MyColor.BLACK,desk).toString())
        assertEquals("black_bishop", Bishop(MyColor.BLACK,desk).toString())

        assertEquals("white_king", King(MyColor.WHITE,desk).toString())
        assertEquals("white_knight", Knight(MyColor.WHITE,desk).toString())
        assertEquals("white_pawn", Pawn(MyColor.WHITE,desk).toString())
        assertEquals("white_queen", Queen(MyColor.WHITE,desk).toString())
        assertEquals("white_rook", Rook(MyColor.WHITE,desk).toString())
        assertEquals("white_bishop", Bishop(MyColor.WHITE,desk).toString())

    }


    @Test
    fun bishopGetMovies() {
        desk.clear()
        desk[0, 0] = Bishop(MyColor.BLACK,desk)
        assertEquals(squares(listOf("b7", "c6", "d5", "e4", "f3", "g2", "h1")), movies(0, 0))
        desk[4, 4] = Bishop(MyColor.WHITE,desk)
        assertEquals(squares(listOf("f3", "g2", "h1", "d5", "c6", "b7", "a8", "f5", "g6", "h7", "d3", "c2", "b1")), movies(4, 4))
        desk[0, 1] = Bishop(MyColor.WHITE,desk)
        desk[1, 1] = Bishop(MyColor.WHITE,desk)
        desk[1, 0] = Bishop(MyColor.WHITE,desk)
        assertEquals(squares(listOf("b7")), movies(0, 0))
    }

    @Test
    fun kingGetMovies() {
        desk.clear()
        desk[0, 4] = King(MyColor.BLACK,desk)
        desk[7, 4] = King(MyColor.WHITE,desk)
        val blackMovies = movies(0, 4)
        assertEquals(squares(listOf("b3", "b4", "b5", "a5", "a3")), blackMovies)
        desk[1, 4] = King(MyColor.WHITE,desk)
        desk[0, 3] = King(MyColor.WHITE,desk)
        desk[0, 5] = King(MyColor.WHITE,desk)
        desk[1, 5] = King(MyColor.WHITE,desk)
        desk[1, 3] = King(MyColor.WHITE,desk)
        assertEquals(blackMovies, movies(0, 4))
        desk[6, 4] = King(MyColor.WHITE,desk)
        desk[7, 3] = King(MyColor.WHITE,desk)
        desk[7, 5] = King(MyColor.WHITE,desk)
        desk[6, 5] = King(MyColor.WHITE,desk)
        desk[6, 3] = King(MyColor.WHITE,desk)
        assertEquals(setOf<Pair<Int, Int>>(), desk[7, 4]!!.getPossibleMoves(7, 4).toSet())
    }

    @Test
    fun castling() {
        desk.clear()
        desk[0, 4] = King(MyColor.BLACK,desk)
        desk[0, 0] = Rook(MyColor.BLACK,desk)
        assertTrue(Pair(0, 2) in movies(0, 4))
        desk[0, 1] = Knight(MyColor.BLACK,desk)
        assertFalse(Pair(0, 2) in movies(0, 4))
    }

    @Test
    fun knightGetMovies() {
        desk.clear()
        desk[1, 1] = Knight(MyColor.BLACK,desk)
        assertEquals(squares(listOf("c5", "a5", "d6", "d8")), movies(1, 1))
        desk[0, 3] = Knight(MyColor.BLACK,desk)
        assertTrue(0 to 3 !in movies(1, 1))
    }

    @Test
    fun pawnGetMovies() {
        desk.clear()
        desk[1, 1] = Pawn(MyColor.BLACK,desk)
        assertEquals(setOf(3 to 1, 2 to 1), movies(1, 1))
        desk[4, 3] = Pawn(MyColor.BLACK,desk)
        desk[4, 2] = Pawn(MyColor.WHITE,desk)
        (desk[4, 2] as Pawn).moveDouble = true
        desk.setWalked(4,2)
        assertEquals(setOf(5 to 3, 5 to 2), movies(4, 3))
    }

    @Test
    fun queenGetMovies() {
        desk.clear()
        desk[3, 3] = Queen(MyColor.WHITE,desk)
        assertEquals(squares(listOf("e4", "f3", "g2", "h1", "d4", "d3", "d2", "d1", "c4", "b3", "a2",
                "c5", "b5", "a5", "c6", "b7", "a8", "d6", "d7", "d8", "e6", "f7", "g8", "e5", "f5", "g5", "h5")), movies(3, 3))
        desk[1, 1] = Queen(MyColor.WHITE,desk)
        desk[6, 6] = Queen(MyColor.BLACK,desk)
        desk[4, 2] = Queen(MyColor.WHITE,desk)
        desk[2, 4] = Queen(MyColor.WHITE,desk)
        assertEquals(squares(listOf("e4", "f3", "g2", "d4", "d3", "d2", "d1", "c5", "b5", "a5", "c6", "d6"
                , "d7", "d8", "e5", "f5", "g5", "h5")), movies(3, 3))
    }

    @Test
    fun rookGetMovies() {
        desk.clear()
        desk[0, 0] = Rook(MyColor.BLACK,desk)
        assertEquals(squares(listOf("a7", "a6", "a5", "a4", "a3", "a2", "a1", "b8", "c8", "d8", "e8", "f8", "g8", "h8")),
                movies(0, 0))
        desk[5, 0] = Rook(MyColor.BLACK,desk)
        desk[0, 3] = Rook(MyColor.WHITE,desk)
        assertEquals(squares(listOf("a7", "a6", "a5", "b8", "c8", "d8", "e8")), movies(0, 0))

    }

    @Test
    fun looser(){
        desk.clear()
        desk[0, 4] = King(MyColor.BLACK,desk)
        desk[1,4] = Queen(MyColor.WHITE,desk)
        desk[1,3] = Queen(MyColor.WHITE,desk)
        desk[1,5] = Queen(MyColor.WHITE,desk)
        desk[0,5] = Queen(MyColor.WHITE,desk)
        desk[0,3] = Queen(MyColor.WHITE,desk)
        assertTrue(desk.isLooser(MyColor.BLACK))
        desk.clear()
        desk[0, 4] = King(MyColor.BLACK,desk)
        desk[1,4] = Queen(MyColor.WHITE,desk)
        assertFalse(desk.isLooser(MyColor.BLACK))
    }

}