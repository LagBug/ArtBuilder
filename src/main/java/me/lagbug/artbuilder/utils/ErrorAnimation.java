package me.lagbug.artbuilder.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

public class ErrorAnimation {

	private String color = "white";
	private int counter;
	
	public ErrorAnimation(JFXButton button) {
		final Timer t = new Timer();

		t.schedule(new TimerTask() {
			public void run() {
				if (counter == 5) {
					button.setStyle("-fx-border-color: white; -fx-border-radius: 2em;");
					t.cancel();
				}
				color = color == "white" ? "#da7171" : "white";
				button.setStyle("-fx-border-color: " + color + "; -fx-border-radius: 2em;");
				counter++;
			}
		}, 0L, 150L);
	}
	
	public ErrorAnimation(JFXTextField field) {
		final Timer t = new Timer();

		t.schedule(new TimerTask() {
			public void run() {
				if (counter == 5) {
					field.setStyle("-fx-text-inner-color: white; -fx-prompt-text-fill: #c3b5b5;");
					t.cancel();
				}
				
				color = color == "white" ? "#da7171" : "white";
				field.setStyle("-fx-text-inner-color: " + color + "; -fx-prompt-text-fill: " + color + ";");
				counter++;
			}
		}, 0L, 150L);
	}
}