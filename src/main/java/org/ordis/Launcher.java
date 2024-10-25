package org.ordis;

import java.io.File;
import java.io.FileInputStream;

public class Launcher {
    public static void main(String[] args) throws Exception {
        var file = new File("token.txt");
        if (args.length == 1) {
            new Application().start(args[0]);
        } else if (file.exists()) {
            try (FileInputStream stream = new FileInputStream(file)) {
                new Application().start(new String(stream.readAllBytes()));
            }
        } else {
            System.err.println("No token file found and false argument size supplied");
        }
    }
}
