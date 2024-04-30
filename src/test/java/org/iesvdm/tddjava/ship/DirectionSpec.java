package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class DirectionSpec {

    public void whenGetFromShortNameNThenReturnDirectionN() {
        char shortname = 'N';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion,Direction.NORTH);
    }

    public void whenGetFromShortNameWThenReturnDirectionW() {
        char shortname = 'W';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion,Direction.WEST);
    }

    public void whenGetFromShortNameBThenReturnNone() {
        char shortname = 'B';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion,Direction.NONE);
    }

    public void givenSWhenLeftThenE() {
        char shortname = 'S';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion.turnLeft(),Direction.EAST);
    }

    public void givenNWhenLeftThenW() {
        char shortname = 'N';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion.turnLeft(),Direction.WEST);
    }

    public void givenSWhenRightThenW() {
        char shortname = 'S';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion.turnRight(),Direction.WEST);
    }

    public void givenWWhenRightThenN() {
        char shortname = 'W';
        Direction direccion = Direction.getFromShortName(shortname);
        assertEquals(direccion.turnRight(),Direction.NORTH);
    }

}
