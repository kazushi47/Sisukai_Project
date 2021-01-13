
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * 出退勤選択画面
 */
public class ASlctController extends GridPane implements ILoadFxml, Initializable, Common {

	public ASlctController() {
		loadFxml(FXML_ASlct);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
