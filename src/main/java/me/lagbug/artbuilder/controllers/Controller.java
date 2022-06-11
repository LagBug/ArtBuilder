package me.lagbug.artbuilder.controllers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import me.lagbug.artbuilder.ArtBuilder;
import me.lagbug.artbuilder.utils.ErrorAnimation;
import me.lagbug.artbuilder.utils.Utils;

public class Controller {
	
	private final ArtBuilder ab = ArtBuilder.getInstance();
	
	@FXML
	private JFXButton inputImageButton, fontTypeButton, outputFolderButton, generateButton;

	@FXML
	private Label outputFolderLabel, fontTypeLabel, inputImageLabel;

	@FXML
	private JFXTextField fontSizeField, wordsField, drawPositionField, cropSizeField, cropPositionField;
	
	@FXML
	private ImageView closeImage, minimizeImage;
	
	@FXML
	public void initialize() {
		fontSizeField.setStyle("-fx-text-inner-color: white; -fx-prompt-text-fill: #c3b5b5;");
		wordsField.setStyle("-fx-text-inner-color: white; -fx-prompt-text-fill: #c3b5b5;");
		drawPositionField.setStyle("-fx-text-inner-color: white; -fx-prompt-text-fill: #c3b5b5;");
		cropSizeField.setStyle("-fx-text-inner-color: white; -fx-prompt-text-fill: #c3b5b5;");
		cropPositionField.setStyle("-fx-text-inner-color: white; -fx-prompt-text-fill: #c3b5b5;");
		
		closeImage.setImage(new Image("close.png"));
		minimizeImage.setImage(new Image("minimize.png"));
	}

	@FXML
	void onGenerate(ActionEvent e) throws NumberFormatException, FontFormatException, IOException {
		if (check()) {
			return;
		}
		
		BufferedImage picBefore = ImageIO.read(ab.getInputImage()), pic = null;
		Font font = Font.createFont(0, ab.getFont()).deriveFont(Float.parseFloat(fontSizeField.getText()));
		String[] words = wordsField.getText().split(", "), drawP = drawPositionField.getText().split(", ");
		
		for (String word : words) {
			pic = cropImage(picBefore);
			
			Graphics2D g = (Graphics2D) pic.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(font);
			g.drawString(word, Integer.parseInt(drawP[0]), Integer.parseInt(drawP[1]));
			ImageIO.write(pic, "jpg", new File(ab.getOutputFolder().getPath() + File.separator + word.toLowerCase().replace(" ", "_") + ".jpg"));
			g.dispose();
		}
		Utils.success(generateButton);
	}

	@FXML
	void onInputImage(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.png)", new String[] { "*.jpg", "*.png", "*.jpeg" });
		chooser.getExtensionFilters().add(extFilter);
		chooser.setTitle("Choose an image");
		
		File fileChoosen = chooser.showOpenDialog(ab.getStage());
		if (fileChoosen == null) {
			return;
		}
		inputImageLabel.setText(fileChoosen.getName());
		ab.setInputImage(fileChoosen);
	}

	@FXML
	void onFontType(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Font files (*.ttf, *.ttc, *.fnt)", new String[] { "*.ttf", "*.ttc" , ".fnt"});
		chooser.getExtensionFilters().add(extFilter);
		chooser.setTitle("Choose a font");
		chooser.setInitialDirectory(new File("C:\\Windows\\Fonts"));
		File fileChoosen = chooser.showOpenDialog(ab.getStage());
		if (fileChoosen == null) {
			return;
		}
		fontTypeLabel.setText(fileChoosen.getName());
		ab.setFont(fileChoosen);
	}

	@FXML
	void onOutputFolder(ActionEvent e) {
		DirectoryChooser chooser = new DirectoryChooser();
		File fileChoosen = chooser.showDialog(ab.getStage());
		if (fileChoosen == null) {
			return;
		}
		outputFolderLabel.setText(fileChoosen.getName());
		ab.setOutputFolder(fileChoosen);
	}

	@FXML
	void onClose(ActionEvent e) {
		ab.getStage().close();
		System.exit(0);
	}
	
	@FXML
	void onMinimize(ActionEvent e) {
		ab.getStage().setIconified(true);
	}

	private BufferedImage cropImage(BufferedImage src) {
		String[] data = cropSizeField.getText().split(", "), data1 = cropPositionField.getText().split(", ");
		
		BufferedImage img = src.getSubimage(Integer.valueOf(data1[0]), Integer.valueOf(data1[1]), Integer.valueOf(data[0]), Integer.valueOf(data[1]));
		BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), 1);
		copyOfImage.createGraphics().drawImage(img, 0, 0, null);
		return copyOfImage;
	}
	
	private boolean check() {
		List<JFXButton> toAlertButton = new ArrayList<>();
		List<JFXTextField> toAlertField  = new ArrayList<>(); 
		
		if (ab.getInputImage() == null) {
			toAlertButton.add(inputImageButton);
		}
		if (ab.getFont() == null) {
			toAlertButton.add(fontTypeButton);
		}
		if (ab.getOutputFolder() == null) {
			toAlertButton.add(outputFolderButton);
		}
		if (fontSizeField.getText().isEmpty() || !Utils.isInteger(fontSizeField.getText())) {
			toAlertField.add(fontSizeField);
		}
		if (wordsField.getText().isEmpty() || !wordsField.getText().contains(", ")) {
			toAlertField.add(wordsField);
		}
		if (drawPositionField.getText().isEmpty() || !drawPositionField.getText().contains(", ")) {
			toAlertField.add(drawPositionField);
		}
		
		if (cropSizeField.getText().isEmpty() || !cropSizeField.getText().contains(", ")) {
			toAlertField.add(cropSizeField);
		}
		
		if (cropPositionField.getText().isEmpty() ||  !cropPositionField.getText().contains(", ")) {
			toAlertField.add(cropPositionField);
		}
		
		if (toAlertButton.isEmpty() && toAlertField.isEmpty()) { return false; }
		
		toAlertButton.forEach(b -> new ErrorAnimation(b));
		toAlertField.forEach(f -> new ErrorAnimation(f));
		return true;
	}
}