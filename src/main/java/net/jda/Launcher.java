package net.jda;

import java.io.FileInputStream;

public class Launcher {
    public static void main(String[] args) throws Exception {
        try (FileInputStream stream = new FileInputStream("token.txt")) {
            new Bot().start(new String(stream.readAllBytes()));
        }
    }
}
