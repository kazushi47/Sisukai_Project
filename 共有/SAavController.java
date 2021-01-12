package jp.ac.hsc.system;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class SAavController extends GridPane implements Common, ILoadFxml, Initializable {

	@FXML
    private GridPane SAav;

    @FXML
    private TableView<Tbl> tblvSAav;

    @FXML
    private TableColumn<Tbl, LocalDate> tblcDay;

    @FXML
    private TableColumn<?, ?> tblcGowork;

    @FXML
    private TableColumn<?, ?> tblcLeaveWork;

    @FXML
    private TableColumn<?, ?> tblcBreakStart;

    @FXML
    private TableColumn<?, ?> tblcBreakEnd;

    @FXML
    private TableColumn<?, ?> Absence;

    @FXML
    private TableColumn<?, ?> tblcLate;

    @FXML
    private TableColumn<?, ?> tblcLeaveEarly;

    @FXML
    private TableColumn<?, ?> tblcPaidHoliday;

    @FXML
    private TableColumn<?, ?> tblcDirect;

    @FXML
    private TableColumn<?, ?> tblcBounce;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnCommit;

    @FXML
    private ComboBox<?> mbtnName;

    @FXML
    private ComboBox<?> mbtnYear;

    @FXML
    private ComboBox<?> mbtnMonth;

    @FXML
    private Label lblDivName;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblName;

    private String preDiv;
    /***************レコード名*******************/
    private String date = "date";
    private String attendanceTime = "attendanceTime";
    private String leaveTie = "leaveTime";
    private String breakTime = "breakTime";
    private String totalBreakTime = "totalBreakTime";
    private String absence = "absence";
    private String lateness = "lateness";
    private String leaveEarly = "leaveEarly";
    private String paidHoliday = "paidHoliday";
    private String directBounce = "directBounce";
    /****************************************/

    private final String SQL_addTblAllData	= "SELECT date,attendanceTime,leaveTime,breakTime,totalBreakTime,absence,lateness,leaveEarly,paidHoliday,directBounce"
															+ "FROM employees e "
															+ "JOIN attendances a ON e.empId = a.empId "
															+ "JOIN divisions d ON e.divId = d.divId "
															+ "WHERE empId = ? AND YEAR(date) = ? AND MONTH(date) = ? "
															+ "GROUP BY e.empId";
    private final String SQL_getAllDivs 		= "SELECT * FROM divisions";
    private final String SQL_getAllYears 		= "SELECT YEAR(date) "
    														+ "FROM employees as e JOIN attendances as a ON e.empId = a.empId "
    														+ "WHERE e.divId = (SELECT divId FROM divisions WHERE divName = ? ) "
    														+ "GROUP BY YEAR(date) "
    														+ "ORDER BY YEAR(date) DESC";
    private final String SQL_getAllMonths 	= "SELECT DISTINCT MONTH(date) "
    														+ "FROM attendances "
    														+ "WHERE YEAR(date) = ? "
    														+ "ORDER BY MONTH(date) DESC";

    private int preYear;
    private int preMonth;
    Emp op;

    public SAavController(Emp op,int empId,String preDiv,int preYear,int preMonth) {
    	loadFxml(FXML_SAav);
    	lblDivName.setText(op.getDivName());
    	lblEmpId.setText(op.getEmpId());
    	lblName.setText(op.getName());
    	this.op = op;
    	this.preDiv = preDiv;
    	this.preYear = preYear;
    	this.preMonth = preMonth;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnBack.setOnAction(event -> {
			Main.getInstance().sendSAvController(op,preDiv,preYear,preMonth);
		});

	}

	public class Tbl{

	}
}
