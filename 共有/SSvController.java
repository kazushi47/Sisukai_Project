package jp.ac.hsc.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class SSvController extends GridPane implements Common, ILoadFxml, Initializable {

	@FXML
    private GridPane SSV;

    @FXML
    private ComboBox<?> cboxDiv;

    @FXML
    private ComboBox<?> cboxYear;

    @FXML
    private ComboBox<?> cboxMonth;

    @FXML
    private Button btnSSv;

    @FXML
    private TableView<?> tblvSv;

    @FXML
    private TableColumn<?, ?> tblcEmpName;

    @FXML
    private TableColumn<?, ?> tblcSumSal;

    @FXML
    private TableColumn<?, ?> tblcBasicSal;

    @FXML
    private TableColumn<?, ?> tblcAllowance;

    @FXML
    private TableColumn<?, ?> tblcTimeout;

    @FXML
    private Button btnSAv;

    @FXML
    private Button btnSSlct;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCalc;

    @FXML
    private Label lblDivName;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
