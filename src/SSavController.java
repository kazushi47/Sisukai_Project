
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

/**
 * 給与明細画面
 */
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

    /** オペレータ */
    private Emp op;

    public SSavController(Emp op) {
        loadFxml(FXML_SSav);
        this.op = op;
        
        /* オペレータ情報の表示 */
        lblDivName.setText(op.getDivName());
        lblEmpId.setText(op.getEmpId());
        lblName.setText(op.getName());
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        btnBack.setOnAction(event -> {
            /* 給与概要画面へ */
            Main.getInstance().sendSSvController(op);
        });

        btnEddite.setOnAction(event -> {
            // TODO 編集機能切り替え
        });

        btnEnter.setOnAction(event -> {
            // TODO 確定処理
        });

        // TODO 表示処理
	}

}
