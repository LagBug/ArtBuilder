package me.lagbug.artbuilder.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;

public class Utils {
	
	private static String color;
	private static int counter;

	public static void success(JFXButton button) {
		final Timer t = new Timer();
		color = "white";

		t.schedule(new TimerTask() {
			public void run() {
				if (Utils.counter == 5) {
					Utils.counter = 1;
					button.setStyle("-fx-border-color: white; -fx-border-radius: 2em;");
					t.cancel();
				}
				Utils.color = Utils.color == "white" ? "#99fcb2" : "white";
				button.setStyle("-fx-border-color: " + Utils.color + "; -fx-border-radius: 2em;");
				Utils.counter += 1;
			}
		}, 0L, 150L);
	}

	public static boolean isInteger(String text) {
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}