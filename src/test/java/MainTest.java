import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {

    /**
     *  Проверяем, что метод выполняется не дольше 22 секунд
     */
    @Disabled
    @Test
    @Timeout(value = 22)
    public void timeout() throws Exception {
        Main.main(null);
    }
}