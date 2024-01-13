import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    /**
     * Проверяем,что при передаче в конструктор первым параметром null,
     * будет выброшено IllegalArgumentException
     */
    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    /**
     * Проверяем,что при передаче в конструктор первым параметром null,
     * выброшенное исключение будет содержать сообщение "Name cannot be null."
     * если исключение не выброшено, тест должен упасть.
     */
    @Test
    public void nullNameMessage() {
        try {
            new Horse(null, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    /**
     * Проверяем,что при передаче в конструктор первым параметром
     * пустой строки или строки содержащей только пробельные символы
     * (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException.
     * и исключение будет содержать сообщение  исключение будет содержать сообщение "Name cannot be blank."     *
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n", "\t\n"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    /**
     * Проверяем,что при передаче в конструктор вторым параметром отрицательного числа,
     * будет выброшено IllegalArgumentException,
     * выброшенное исключение должно содержать сообщение "Speed cannot be negative.";
     */
    @Test
    public void negativeSpeedExceptionMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    /**
     * Проверяем,что при передаче в конструктор третьим параметром отрицательного числа,
     * будет выброшено IllegalArgumentException,
     * выброшенное исключение должно содержать сообщение "Distance cannot be negative.";
     */
    @Test
    public void negativeDistanceExceptionMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, -1));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    /**
     * Проверяем,что метод возвращает строку, которая была передана первым параметром в конструктор.
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        String expectedName = "testName";
        Horse horse = new Horse(expectedName, 1, 1);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);

        assertEquals(expectedName, nameValue);
    }

    /**
     * Проверяем,что метод возвращает число, которое было передано вторым параметром в конструктор.
     * проверяем вторым способом, но нет гарантии, что метод getSpeed() не переопределили и не изменили.
     */
    @Test
    public void getSpeed() {
        int expectedSpeed = 234;

        Horse horse = new Horse("name", expectedSpeed, 1);

        assertEquals(expectedSpeed, horse.getSpeed());
    }

    /**
     * Проверяем,что метод возвращает число, которое было передано третьим параметром в конструктор
     */
    @Test
    public void getDistance() {
        int expectedDistance = 234;

        Horse horse = new Horse("name", 1, expectedDistance);

        assertEquals(expectedDistance, horse.getDistance());
    }

    /**
     * Проверяем,что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами
     */
    @Test
    public void zeroDistance() {
        Horse horse = new Horse("name", 1);

        assertEquals(0, horse.getDistance());
    }

    /**
     * Проверяем, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9.
     */
    @Test
    public void moveUsesGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("name", 23, 234).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    /**
     * Проверяем, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9)
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.6, 1.0, 0.0, 99.99})
    public void move(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 23, 234);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(234 + 23 * random, horse.getDistance());
        }
    }
}