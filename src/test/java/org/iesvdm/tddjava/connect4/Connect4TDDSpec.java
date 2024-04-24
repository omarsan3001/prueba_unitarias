package org.iesvdm.tddjava.connect4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


public class Connect4TDDSpec {
    private Connect4TDD tested;

    private OutputStream output;

    @BeforeEach
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();

        //Se instancia el juego modificado para acceder a la salida de consola
        tested = new Connect4TDD(new PrintStream(output));
    }

    /*
     * The board is composed by 7 horizontal and 6 vertical empty positions
     */

    @Test
    public void whenTheGameStartsTheBoardIsEmpty() {
        assertEquals(tested.getNumberOfDiscs(),0);
    }

    /*
     * Players introduce discs on the top of the columns.
     * Introduced disc drops down the board if the column is empty.
     * Future discs introduced in the same column will stack over previous ones
     */

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {tested.putDiscInColumn(8);});
        assertThrows(RuntimeException.class, () -> {tested.putDiscInColumn(-1);});
        assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i <= 7; i++) {
                tested.putDiscInColumn(6);
            }
        });
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
        for (int i = 0; i < 7; i++) {
            assertThat(tested.putDiscInColumn(i)).isEqualTo(0);
        }


    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        for (int i = 0; i < 7; i++) {
            tested.putDiscInColumn(i);
            assertThat(tested.putDiscInColumn(i)).isEqualTo(1);
        }
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {

        assertEquals(tested.getNumberOfDiscs(),0);
        tested.putDiscInColumn(0);
        assertEquals(tested.getNumberOfDiscs(),1);

    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {

        for (int i = 0; i < 6; i++) {

            tested.putDiscInColumn(0);
        }

        assertThrows(RuntimeException.class, () -> {tested.putDiscInColumn(0);});

    }

    /*
     * It is a two-person game so there is one colour for each player.
     * One player uses red ('R'), the other one uses green ('G').
     * Players alternate turns, inserting one disc every time
     */

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed() {
        assertThat(tested.getCurrentPlayer()).isEqualTo("R");
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsGreen() {
        tested.putDiscInColumn(0);
        assertThat(tested.getCurrentPlayer()).isEqualTo("G");
    }

    /*
     * We want feedback when either, event or error occur within the game.
     * The output shows the status of the board on every move
     */

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {

        assertThat(tested.getCurrentPlayer()).isEqualTo("R");
        tested.putDiscInColumn(0);
        assertThat(tested.getCurrentPlayer()).isEqualTo("G");

    }

    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {
        tested.putDiscInColumn(0);
        String expected = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                """;
        assertThat(output.toString().replace("\r","")).isEqualTo(expected);
    }

    /*
     * When no more discs can be inserted, the game finishes and it is considered a draw
     */

    @Test
    public void whenTheGameStartsItIsNotFinished() {

        tested.putDiscInColumn(0);
        assertFalse(tested.isFinished());

    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                tested.putDiscInColumn(i);
            }
        }

        assertTrue(tested.isFinished());
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight vertical line then that player wins
     */

    @Test
    public void when4VerticalDiscsAreConnectedThenThatPlayerWins() {
        for (int i = 0; i < 7; i++) {
            if (i % 2 == 0) {
                tested.putDiscInColumn(0);
            }else{
                tested.putDiscInColumn(1);
            }
        }
        assertEquals(tested.getWinner(),"R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight horizontal line then that player wins
     */

    @Test
    public void when4HorizontalDiscsAreConnectedThenThatPlayerWins() {
        int cont = 0;
        for (int i = 0; i < 7; i++) {
            if (i % 2 == 0) {
                tested.putDiscInColumn(cont);
                cont ++;
            }else{
                tested.putDiscInColumn(0);
            }

        }
        assertEquals(tested.getWinner(),"R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight diagonal line then that player wins
     */

    @Test
    public void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(0);

        assertEquals(tested.getWinner(),"R");
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(3);
        
        assertEquals(tested.getWinner(),"R");
    }
}
