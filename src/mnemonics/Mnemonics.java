package mnemonics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class Mnemonics extends Application {

	static Button import_text;

	static Button convert_button;

	static TextArea input_box = new TextArea();
	static TextArea output_box = new TextArea();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		createButtons();

		HBox buttons = new HBox(12, import_text, convert_button);

		buttons.setAlignment(Pos.CENTER);

		input_box.setWrapText(true);
		output_box.setWrapText(true);

		VBox vbox = new VBox(12, buttons, input_box, output_box);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(12));

		// vbox.setStyle(

		// );

		Scene scene = new Scene(vbox);

		stage.setTitle("Text mnemonic generator");
		stage.setScene(scene);
		stage.setWidth(500);
		stage.show();
	}

	private static void createButtons() {

		convert_button = new Button("Create mnemonic");
		convert_button.setMnemonicParsing(true);
		convert_button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				output_box.setText(eng_to_mnemonic(input_box.getText()));

			}
		});

		import_text = new Button("_Import text");
		import_text.setMnemonicParsing(true);
		import_text.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				StringBuilder output = new StringBuilder();

				FileChooser fc = new FileChooser();
				fc.setTitle("Open Text File");

				File file = fc.showOpenDialog(import_text.getScene().getWindow());

				if (file != null) {

					try {
						DataInputStream in = new DataInputStream(new FileInputStream(file));

						for (;;) {
							try {

								output.append(in.readChar());

							} catch (IOException e) {
								break;
							}
						}

						input_box.setText(output.toString());

					} catch (FileNotFoundException e1) {
						System.out.println(e1.getMessage());
					}
				}

			}
		});

	}

	protected static String eng_to_mnemonic(String s) {

		StringBuilder converted = new StringBuilder();

		int ignored = 0;

		boolean flag = true;

		String space = "";

		for (char c : s.toCharArray()) {

			if (flag && Character.isAlphabetic(c)) {

				converted.append(space + c);
				flag = false;

			} else if (c == ' ') {
				flag = true;
			} else if (!Character.isLetter(c)) {
				converted.append(c);
			}

			space = " ";
		}

		if (ignored > 0) {
			// report_ignored_characters(ignored);
		}
		return converted.toString();
	}

}