package org.ordis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class TestAnnotation {

    @SuppressWarnings("unused")
    @TestCommand(name = "Cmd", description = "A random command.")
    public static void command(String arg) {
        System.out.printf("I got %s!\n", arg);
    }

    @Test
    void test() {
        Assertions.assertDoesNotThrow(() -> {
            for (Method method : TestAnnotation.class.getMethods()) {
                if (method.isAnnotationPresent(TestCommand.class) ) {
                    method.invoke(null, "Cookies");
                    TestCommand command = method.getAnnotation(TestCommand.class);
                    System.out.printf("%s: %s\n", command.name(), command.description());
                    break;
                }
            }
        });
    }
}
