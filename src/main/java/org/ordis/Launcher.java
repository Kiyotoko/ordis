package org.ordis;

import java.io.FileInputStream;

public class Launcher {
    public static void main(String[] args) throws Exception {
        try (FileInputStream stream = new FileInputStream("token.txt")) {
            new Application().start(new String(stream.readAllBytes()));
        }
    }
}
