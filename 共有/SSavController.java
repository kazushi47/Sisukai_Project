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

public class SSavController extends GridPane implements Common, ILoadFxml, Initializable {

	@FXML
    private Button btnBack;

    @FXML
    private Button btnEddite;

    @FXML
    private Button btnEnter;

    @FXML
    private ComboBox<?> cboxEmpName;

    @FXML
    private ComboBox<?> cboxYear;

    @FXML
    private ComboBox<?> cboxMonth;

    @FXML
    private TableView<?> tblvBase;

    @FXML
    private TableColumn<?, ?> tblcBaseItemType;

    @FXML
    private TableColumn<?, ?> tblcBaseItem;

    @FXML
    private TableColumn<?, ?> tblcBaseMoney;

    @FXML
    private TableView<?> tblvDate;

    @FXML
    private TableColumn<?, ?> tblcDateItem;

    @FXML
    private TableColumn<?, ?> tblcDate;

    @FXML
    private TableView<?> tblvAllMoney;

    @FXML
    private TableColumn<?, ?> tblcAllItem;

    @FXML
    private TableColumn<?, ?> tblcAllMoney;

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
