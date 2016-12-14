package acme;

import documentation.TestBookGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Created by poussma on 13/12/16.
 */
@ExtendWith(TestBookGenerator.class)
public class MyResourceTest {

    @Test
    @DisplayName("double check mathematics basics")
    void testBasics() {
        Assertions.assertEquals(2, 1 + 1);
    }

    @Test
    void testCannotDivideBy0() {
        int result = 10 / 0;
        Assertions.fail("should not be possible");
    }

    @Test
    void testAssertionFailure() {
        Assertions.fail("because 38");
    }

    @Test
    @Disabled("demonstration only")
    void testReloadTable() {

    }
}
