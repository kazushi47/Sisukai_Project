import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController extends GridPane implements Initializable, ILoadFxml {
	// レイアウトとの紐づけ
	@FXML private TextField			loginname;		// ログイン名
	@FXML private PasswordField 		password;		// パスワード
	@FXML private Button				authButton;		// 認証ボタン
	@FXML private Button				exitButton;		// 終了ボタン


	public LoginController() {
		loadFxml("Login.fxml");			// レイアウトのロードとコントローラ設定
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
