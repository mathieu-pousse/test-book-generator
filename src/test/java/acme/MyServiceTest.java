package acme;

import documentation.TestBookGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * Created by poussma on 13/12/16.
 */
@ExtendWith(TestBookGenerator.class)
public class MyServiceTest {

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromIntStream() {
        // Generates tests for the first 10 even integers.
        return IntStream.range(0, 13)
                .mapToObj(n -> dynamicTest("test" + n + "IsBelow12", () ->
                {
                    assertTrue(n < 12);
                }));
    }

    @Nested
    @ExtendWith(TestBookGenerator.class)
    class CompleteMethodCheck {

        @DisplayName("ensure mathematics are right")
        @TestFactory
        Stream<DynamicTest> dynamicTestsFromIntStream() {
            // Generates tests for the first 10 even integers.
            return IntStream.range(0, 11)
                    .mapToObj(n -> dynamicTest("test" + n + "IsBelow12", () ->
                    {
                        System.out.printf("kikooo");
                        assertTrue(n < 12);
                    }));
        }
    }

    @Test
    void testAssertionFailure() {
        Assertions.fail("because 38 again");
    }

    @Test
    @Disabled("demonstration only")
    void testReloadTable() {

    }
}

