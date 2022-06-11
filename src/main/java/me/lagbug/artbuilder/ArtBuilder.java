package me.lagbug.artbuilder;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ArtBuilder extends Application {
	
	private double xOffset = 0.0D, yOffset = 0.0D;
	private File inputImage, font, outputFolder;
	private Stage stage;
	private Scene scene;
	
	public static final String VERSION = "0.2";
	private static ArtBuilder instance;

	public void start(Stage primaryStage) throws IOException, FontFormatException {
		instance = this;
		stage = primaryStage;

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Builder.fxml"));
		root.setOnMousePressed(e -> { xOffset = e.getSceneX(); yOffset = e.getSceneY();});
		root.setOnMouseDragged(e -> {stage.setX(e.getScreenX() - xOffset); stage.setY(e.getScreenY() - yOffset);});
		
		scene = new Scene(root);
		stage.getIcons().add(new Image("icon.png"));
		stage.setScene(this.scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void switchStage(String fxmlName) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName));
			root.setOnMousePressed(e -> { xOffset = e.getSceneX(); yOffset = e.getSceneY();});
			root.setOnMouseDragged(e -> {stage.setX(e.getScreenX() - xOffset); stage.setY(e.getScreenY() - yOffset);});
			scene.setRoot(root);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Stage getStage() {
		return this.stage;
	}

	public static ArtBuilder getInstance() {
		return instance;
	}

	public File getInputImage() {
		return this.inputImage;
	}

	public void setInputImage(File inputImage) {
		this.inputImage = inputImage;
	}

	public File getFont() {
		return this.font;
	}

	public void setFont(File font) {
		this.font = font;
	}

	public File getOutputFolder() {
		return this.outputFolder;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}
}