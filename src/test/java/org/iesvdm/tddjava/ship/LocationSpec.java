package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class LocationSpec {

    private final int x = 12;
    private final int y = 32;
    private final Direction direction = Direction.NORTH;
    private Point max;
    private Location location;
    private List<Point> obstacles;

    @BeforeMethod
    public void beforeTest() {
        max = new Point(50, 50);
        location = new Location(new Point(x, y), direction);
        obstacles = new ArrayList<Point>();
    }

    public void whenInstantiatedThenXIsStored() {
        assertEquals(location.getX(), x);

    }

    public void whenInstantiatedThenYIsStored() {
        assertEquals(location.getY(), y);
    }

    public void whenInstantiatedThenDirectionIsStored() {
        assertEquals(location.getDirection(), direction);
    }

    public void givenDirectionNWhenForwardThenYDecreases() {
        location.setDirection(Direction.NORTH);
        location.forward();
        assertEquals(location.getY(), (y - 1));
    }

    public void givenDirectionSWhenForwardThenYIncreases() {
        location.setDirection(Direction.SOUTH);
        location.forward();
        assertEquals(location.getY(), (y + 1));
    }

    public void givenDirectionEWhenForwardThenXIncreases() {
        location.setDirection(Direction.EAST);
        location.forward();
        assertEquals(location.getX(), (x + 1));
    }

    public void givenDirectionWWhenForwardThenXDecreases() {
        location.setDirection(Direction.WEST);
        location.forward();
        assertEquals(location.getX(), (x - 1));
    }

    public void givenDirectionNWhenBackwardThenYIncreases() {
        location.setDirection(Direction.NORTH);
        location.backward();
        assertEquals(location.getY(), (y + 1));
    }

    public void givenDirectionSWhenBackwardThenYDecreases() {
        location.setDirection(Direction.SOUTH);
        location.backward();
        assertEquals(location.getY(), (y - 1));
    }

    public void givenDirectionEWhenBackwardThenXDecreases() {
        location.setDirection(Direction.EAST);
        location.backward();
        assertEquals(location.getX(), (x - 1));
    }

    public void givenDirectionWWhenBackwardThenXIncreases() {
        location.setDirection(Direction.WEST);
        location.backward();
        assertEquals(location.getX(), (x + 1));
    }

    public void whenTurnLeftThenDirectionIsSet() {
        location.turnLeft();
        assertEquals(location.getDirection(), Direction.WEST);
    }

    public void whenTurnRightThenDirectionIsSet() {
        location.turnRight();
        assertEquals(location.getDirection(), Direction.EAST);
    }

    public void givenSameObjectsWhenEqualsThenTrue() {
        Location location1 = new Location(new Point(x, y), direction);
        Location location2 = new Location(new Point(x, y), direction);
        assertEquals(location1.equals(location2), true);
    }

    public void givenDifferentObjectWhenEqualsThenFalse() {
        Location location1 = new Location(new Point(12, 23), Direction.NORTH);
        Location location2 = new Location(new Point(34, 48), Direction.SOUTH);
        assertEquals(location1.equals(location2), false);
    }

    public void givenDifferentXWhenEqualsThenFalse() {
        Location location1 = new Location(new Point(12, 23), Direction.NORTH);
        Location location2 = new Location(new Point(13, 23), Direction.NORTH);
        assertEquals(location1.equals(location2), false);
    }

    public void givenDifferentYWhenEqualsThenFalse() {
        Location location1 = new Location(new Point(12, 23), Direction.SOUTH);
        Location location2 = new Location(new Point(12, 24), Direction.SOUTH);
        assertEquals(location1.equals(location2), false);
    }

    public void givenDifferentDirectionWhenEqualsThenFalse() {
        Location location1 = new Location(new Point(12, 23), Direction.EAST);
        Location location2 = new Location(new Point(12, 23), Direction.NORTH);
        assertEquals(location1.equals(location2), false);
    }

    public void givenSameXYDirectionWhenEqualsThenTrue() {
        Location location1 = new Location(new Point(12, 23), Direction.NORTH);
        Location location2 = new Location(new Point(12, 23), Direction.NORTH);
        assertEquals(location1.equals(location2), true);
    }

    public void whenCopyThenDifferentObject() {
        Location location1 = new Location(new Point(12, 23), Direction.NORTH);

        assertEquals(location1.equals(location1 = location.copy()), false);

    }

    public void whenCopyThenEquals() {
        Location location1 = new Location(new Point(12, 23), Direction.NORTH);
        Location location2 = location1.copy();
        assertEquals(location1.equals(location2), true);
    }

    public void givenDirectionEAndXEqualsMaxXWhenForwardThen1() {
        Location location1 = new Location(max, Direction.EAST);
        location1.forward();
        assertEquals(location1.getX(), 1);
    }

    public void givenDirectionWAndXEquals1WhenForwardThenMaxX() {
        Location location1 = new Location(new Point(1,10), Direction.WEST);
        location1.forward();
        assertEquals(location1.getX(), max.getX());
    }

    public void givenDirectionNAndYEquals1WhenForwardThenMaxY() {
        Location location1 = new Location(new Point(10,1), Direction.NORTH);
        location1.forward();
        assertEquals(location1.getY(), max.getY());
    }

    public void givenDirectionSAndYEqualsMaxYWhenForwardThen1() {
        Location location1 = new Location(max, Direction.SOUTH);
        location1.forward();
        assertEquals(location1.getY(), 1);

    }

    public void givenObstacleWhenForwardThenReturnFalse() {
        obstacles.add(new Point(1,2));
        Location location1 = new Location(new Point(1,1), Direction.NORTH);
        assertEquals(!location.forward(new Point(1,2),obstacles),false);
    }

    public void givenObstacleWhenBackwardThenReturnFalse() {
        obstacles.add(new Point(1,1));

        assertEquals(!location.backward(new Point(1,1),obstacles),false);
    }

}
