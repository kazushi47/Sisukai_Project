
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * ログイン画面
 */
public class LoginController extends GridPane implements Initializable,ILoadFxml,Common {

	@FXML
    private GridPane pane;

    @FXML
    private TextField txtfUserId;

    @FXML
    private PasswordField pwfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnHome;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblLogin;

    private String stat;

    private final String loginErrMsg = "利用者IDまたはパスワードが間違っています";
    private int id = 1000011;
    private String pw = "n2QcJatJ";

    public LoginController(String stat) {
    	loadFxml(FXML_Login);
    	txtfUserId.setText(Integer.toString(id));
    	pwfPassword.setText(pw);
    	this.stat = stat;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.disableProperty().bind(						// 認証ボタンの活性化バインダ
				Bindings.createBooleanBinding(
						() -> txtfUserId.getText().isEmpty() || pwfPassword.getText().isEmpty()
								,txtfUserId.textProperty(),pwfPassword.textProperty()));
		btnLogin.setOnAction(event ->{
			Emp op = Auth.authUser(txtfUserId.getText().strip(),pwfPassword.getText().strip(),stat);
			if(op != null) {
				switch(stat) {
				case A :
					Main.getInstance().sendASlctController(op);
					break;
				case S :
					Main.getInstance().sendSSlctController(op);
					break;
				}
			}else {
				txtfUserId.setText(loginErrMsg);
				txtfUserId.requestFocus();
		    	txtfUserId.selectAll();
				pwfPassword.clear();
			}
		});
		btnHome.setOnAction(event -> {
			Main.getInstance().sendSlctController();
		});
	}


}
