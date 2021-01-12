package jp.ac.hsc.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SlctController extends GridPane implements ILoadFxml, Initializable ,Common {

	@FXML
    private Button btnSlctS;

    @FXML
    private Button btnSlctA;

    @FXML
    private Button btnFin;

    @FXML
    private Label lblDate;

    @FXML
    private Button btnHide;

	public SlctController() {
		loadFxml(FXML_Slct);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnSlctA.setOnAction(event -> {
			Main.getInstance().sendLoginController(A);
		});
		btnSlctS.setOnAction(event -> {
			Main.getInstance().sendLoginController(S);
		});
		btnFin.setOnAction(event -> {
			Alert a = new Alert(AlertType.INFORMATION,"",ButtonType.OK,ButtonType.CANCEL);
			a.setHeaderText("終了前メッセージ");
			a.setContentText("終了しますか？");
			if(a.showAndWait().orElse(ButtonType.OK) == ButtonType.OK) {
				a.setContentText("作品展では終了できないようにしています");
				a.showAndWait();
			};
		});
		btnHide.setOnAction(event -> {
			Platform.exit();
		});
	}

}
