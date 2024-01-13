import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    /**
     *Проверяем,что при передаче в конструктор null, будет выброшено IllegalArgumentException.
     * выброшенное исключение будет содержать сообщение "Horses cannot be null."
     */
    @Test
    public void nullHorsesExceptionMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    /**
     * Проверяем,что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException.
     * выброшенное исключение будет содержать сообщение "Horses cannot be empty."
     */
    @Test
    public void emptyHorsesExceptionMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    /**
     * Проверяем,что метод возвращает список, который содержит те же объекты и в той же последовательности, что и список который был передан в конструктор
     */
    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(new Horse("horse" + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    /**
     * Проверяем, что метод вызывает метод move у всех лошадей.
     */
    @Test
    public void move() {
        //arrange
        List<Horse> horses = new ArrayList<>();
        for (int i=0; i<50; i++){
            horses.add(mock(Horse.class));
        }

        //act
        new Hippodrome(horses).move();

        //assert
        for (Horse horse: horses){
            verify(horse).move();
        }
    }

    /**
     *Проверяем,что метод возвращает лошадь с самым большим значением distance
     */
    @Test
    public void getWinner() {
        Horse horse1 = new Horse("name1", 1, 1);
        Horse horse2 = new Horse("name2", 1, 2);
        Horse horse3 = new Horse("name3", 1, 3);
        Horse horse4 = new Horse("name4", 1, 4);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse4, hippodrome.getWinner());
    }
}