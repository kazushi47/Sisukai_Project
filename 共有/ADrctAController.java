package jp.ac.hsc.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ADrctAController extends GridPane implements Common, ILoadFxml, Initializable {

	@FXML
    private Label lblDiv;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblGoStraightStart;

    @FXML
    private Label lblGoStraightEnd;

    @FXML
    private Button btnGoStraightEntry;

    @FXML
    private DatePicker dpStart;

    @FXML
    private DatePicker dpEnd;

    @FXML
    private Label lblGoStraight;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnBack;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
