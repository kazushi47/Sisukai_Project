package jp.ac.hsc.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SSlctController extends GridPane implements Initializable ,ILoadFxml ,Common{

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnAv;

    @FXML
    private Button btnSv;

    @FXML
    private Label lblInstructions;

    @FXML
    private Label lblDivName;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblName;

    public static Emp op;

	public SSlctController(Emp op) {
		loadFxml(FXML_SSlct);
		lblDivName.setText(op.getDivName());
		lblEmpId.setText(op.getEmpId());
		lblName.setText(op.getName());
		this.op = op;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAv.setOnAction(event -> {
			Main.getInstance().sendSAvController(op);
		});
		btnSv.setOnAction(event -> {
			Main.getInstance().sendSSvController(op);
		});
		btnLogout.setOnAction(event -> {
			Main.getInstance().sendLoginController(S);
		});
	}

}
