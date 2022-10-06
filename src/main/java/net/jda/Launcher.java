package net.jda;

import java.io.FileInputStream;

import net.jda.application.Application;

public class Launcher {
	public static void main(String[] args) throws Exception {
		try (FileInputStream stream = new FileInputStream("token.txt")) {
			Application.launch(App.class, new String(stream.readAllBytes()));
		}
	}
}
