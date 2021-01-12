package jp.ac.hsc.system;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class SAvController extends GridPane implements Common, ILoadFxml, Initializable {

	 @FXML
	    private TableView<Tbl> tblvAttendance;

	    @FXML
	    private TableColumn<Tbl, String> tblcName;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcGowork;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcRest;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcPaid;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcSpv;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcTrip;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcLate;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcEarly;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcGostraight;

	    @FXML
	    private TableColumn<Tbl, Integer> tblcReturn;

	    @FXML
	    private Button btnLogout;

	    @FXML
	    private Button btnHome;

	    @FXML
	    private Button btnSalary;

	    @FXML
	    private Button btnAttendance;

	    @FXML
	    private Button btnCalc;

	    @FXML
	    private ComboBox<String> cboxDiv;

	    @FXML
	    private ComboBox<String> cboxYear;

	    @FXML
	    private ComboBox<String> cboxMonth;

	    @FXML
	    private Label lblDivName;

	    @FXML
	    private Label lblEmpId;

	    @FXML
	    private Label lblName;

	    private final String name = "name";
	    private final String gowork = "gowork";
	    private final String rest = "rest";
	    private final String paid = "paid";
	    private final String spv = "spv";
	    private final String trip = "trip";
	    private final String late = "late";
	    private final String early = "early";
	    private final String gostraight = "gostraight";
	    private final String rt = "rt";
	    private final String divName = "divName";
	    private final String date = "date";
		private final String yearBack = "年";
		private final String monthBack = "月分";
		private final String empId = "empId";

	    private final String SQL_addTblAllData	= "SELECT e.empId,name,COUNT(*) as gowork,0 as rest,COUNT(paidHoliday) as paid,COUNT(specialHolidayType) as spv "
		    													+ ",COUNT(businessTripType) as trip,COUNT(lateness) as late,COUNT(leaveEarly) as early "
		    													+ "FROM employees e "
		    													+ "JOIN attendances a ON e.empId = a.empId "
																+ "JOIN divisions d ON e.divId = d.divId "
																+ "WHERE divName = ? AND YEAR(date) = ? AND MONTH(date) = ? "
																+ "GROUP BY e.empId";
	    private final String SQL_countGostraight = "SELECT COUNT(directBounce) as gostraight FROM attendances a WHERE empId = ? AND directBounce = '1' OR directBounce = '11'";
	    private final String SQL_countRt = "SELECT COUNT(directBounce) as rt FROM attendances a WHERE empId = ? AND directBounce = '10' OR directBounce = '11'";
	    private final String SQL_getAllDivs 		= "SELECT * FROM divisions";
	    /*部署指定         attendances -> employees -> divisions   empId -> divId -> divName */
	    private final String SQL_getAllYears 		= "SELECT YEAR(date) "
	    														+ "FROM employees as e JOIN attendances as a ON e.empId = a.empId "
	    														+ "WHERE e.divId = (SELECT divId FROM divisions WHERE divName = ? ) "
	    														+ "GROUP BY YEAR(date) "
	    														+ "ORDER BY YEAR(date) DESC";
	    private final String SQL_getAllMonths 	= "SELECT DISTINCT MONTH(date) "
	    														+ "FROM attendances "
	    														+ "WHERE YEAR(date) = ? "
	    														+ "ORDER BY MONTH(date) DESC";
	    private final String SQL_getEmpId = "SELECT empId FROM employees WHERE name = ?";
	    private final String checkTitle ="確認";
	    private final String checkHomeMsg = "初期画面に戻ります。\nよろしいですか？";
	    private final String checkLogoutMsg = "ログイン画面に戻ります。\nよろしいですか？";

	    private ObservableList<Tbl> tdata;
	    private final Tbl init = new Tbl("",0,0,0,0,0,0,0,0,0);
	    private Emp op;
	    private ResultSet rs;
	    private ResultSet rsg;
	    private ResultSet rsr;


	public SAvController(Emp op) {
		loadFxml(FXML_SAv);
		lblDivName.setText(op.getDivName());
		lblEmpId.setText(op.getEmpId());
		lblName.setText(op.getName());
		this.op = op;
		prepareTable();
		//部署・年・月登録
		cboxDiv.setItems(getAllDivs());
		initTbl();
	}

	public SAvController(Emp op,String divName,int year,int month) {
		loadFxml(FXML_SAv);
		lblDivName.setText(op.getDivName());
		lblEmpId.setText(op.getEmpId());
		lblName.setText(op.getName());
		this.op = op;
		prepareTable();
		//部署・年・月登録
		cboxDiv.setItems(getAllDivs());
		initTbl(divName,year,month);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//リスナー登録
		cboxDiv.valueProperty().addListener((obv,oldv,newv) -> {
			if(oldv != newv){//部署が変更された場合　　　　対象年を登録
				clearCboxAllData(cboxYear);
				clearCboxAllData(cboxMonth);
				initTbl();
				cboxYear.setItems(getAllYears(newv));
			}
		});
		cboxYear.valueProperty().addListener((obv,oldv,newv) -> {
			if(oldv != newv) {
				initTbl();
				cboxMonth.setItems(getAllMonths(newv));
			}
		});
		cboxMonth.valueProperty().addListener((obv,oldv,newv) ->{
			if(oldv != newv && cboxYear.getValue() != null && cboxMonth.getValue() != null) {//null参照を防ぐ
				System.out.println(cboxDiv.getValue() + ", " + cboxYear.getValue() + ", " + cboxMonth.getValue());
				addTableData(cboxDiv.getValue(), Integer.parseInt(cboxYear.getValue().replaceAll(yearBack,"")), Integer.parseInt(cboxMonth.getValue().replaceAll(monthBack, "")));
			}
		});
		btnLogout.setOnAction(event -> {
			if(checkReturn(checkLogoutMsg))Main.getInstance().sendLoginController(S);
		});
		btnHome.setOnAction(event -> {
			if(checkReturn(checkHomeMsg))Main.getInstance().sendSlctController();
		});
		tblvAttendance.setEditable(false);
		Callback<TableColumn<Tbl,String>, TableCell<Tbl,String>> cellFactory =
		        new Callback<TableColumn<Tbl,String>, TableCell<Tbl,String>>() {
		            public TableCell<Tbl,String> call(TableColumn<Tbl,String> p) {
		                TableCell<Tbl,String> cell = new TableCell<Tbl, String>() {
		                    @Override
		                    public void updateItem(String item, boolean empty) {
		                        super.updateItem(item, empty);
		                        setText(empty ? null : getString());
		                        setGraphic(null);
		                    }

		                    private String getString() {
		                        return getItem() == null ? "" : getItem().toString();
		                    }
		                };

		                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		                    @Override
		                    public void handle(MouseEvent event) {
		                        if (event.getClickCount() > 1) {
		                            TableCell<Tbl,String> c = (TableCell<Tbl,String>) event.getSource();
		                            System.out.println("Cell text: " + c.getText());
		                            Main.getInstance().sendSAavController(
		                            		op,getEmpId(c.getText())
		                            		,cboxDiv.getValue(),Integer.parseInt(cboxYear.getValue().replaceAll(yearBack,""))
		                            		,Integer.parseInt(cboxMonth.getValue().replaceAll(monthBack, "")));
		                        }
		                    }
		                });
		                return cell;
		            }
		        };
		        tblcName.setCellFactory(cellFactory);
	}

	private void initTbl() {
		tblvAttendance.getItems().clear();
		tdata = FXCollections.observableArrayList();
		tdata.add(init);
		tblvAttendance.setItems(tdata);
	}

	private void initTbl(String divName,int year,int month) {
		cboxDiv.setValue(divName);
		cboxYear.setValue(year + yearBack);
		cboxMonth.setValue(month + monthBack);
		addTableData(divName, year, month);
	}
	private boolean checkReturn(String content) {
		Alert alert = new Alert(AlertType.INFORMATION,checkTitle,ButtonType.OK,ButtonType.CANCEL);
		alert.setContentText(content);
		return alert.showAndWait().orElse(ButtonType.OK).equals(ButtonType.OK) ? true : false;
	}

	private int getEmpId(String empName) {
		int res = ZERO;
		try {
			PreparedStatement stmt = DBconnect.getConnection().prepareStatement(SQL_getEmpId);
			stmt.setString(ONE, empName);
			ResultSet rs = stmt.executeQuery();
			res = rs.next() ? rs.getInt(ONE) : ZERO;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return res;
	}

	private ObservableList<String> getAllDivs(){
		Connection con = DBconnect.getConnection();
		ObservableList<String> divs = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = con.prepareStatement(SQL_getAllDivs);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())divs.add(rs.getString(divName));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return divs;
	}

	private ObservableList<String> getAllYears(String divName){
		Connection con = DBconnect.getConnection();
		ObservableList<String> years = FXCollections.observableArrayList();

		if(divName == null)return years;
		try {
			PreparedStatement stmt = con.prepareStatement(SQL_getAllYears);
			stmt.setString(ONE,divName);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())years.add(rs.getInt(ONE) + yearBack);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return years;
	}

	private ObservableList<String> getAllMonths(String year){
		Connection con = DBconnect.getConnection();
		ObservableList<String> months = FXCollections.observableArrayList();
		if(year == null)return months;
		try {
			PreparedStatement stmt = con.prepareStatement(SQL_getAllMonths);
			stmt.setString(ONE,year.replaceAll(yearBack, ""));
			ResultSet rs = stmt.executeQuery();
			while(rs.next())months.add(rs.getInt(ONE) + monthBack);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return months;
	}

	private void prepareTable() {
		tblcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tblcGowork.setCellValueFactory(new PropertyValueFactory<>("gowork"));
		tblcRest.setCellValueFactory(new PropertyValueFactory<>("rest"));
		tblcPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
		tblcSpv.setCellValueFactory(new PropertyValueFactory<>("spv"));
		tblcTrip.setCellValueFactory(new PropertyValueFactory<>("trip"));
		tblcLate.setCellValueFactory(new PropertyValueFactory<>("late"));
		tblcEarly.setCellValueFactory(new PropertyValueFactory<>("early"));
		tblcGostraight.setCellValueFactory(new PropertyValueFactory<>("gostraight"));
		tblcReturn.setCellValueFactory(new PropertyValueFactory<>("rt"));

	}

	private void addTableData(String divName,int year,int month) {
		try {
			PreparedStatement stmt = DBconnect.getConnection().prepareStatement(SQL_addTblAllData);
			PreparedStatement stmtg = DBconnect.getConnection().prepareStatement(SQL_countGostraight);
			PreparedStatement stmtr = DBconnect.getConnection().prepareStatement(SQL_countRt);

			stmt.setString(ONE,divName);
			stmt.setInt(TWO, year);
			stmt.setInt(THREE, month);

			rs = stmt.executeQuery();

			tdata = FXCollections.observableArrayList();
			while(rs.next()) {
				stmtg.setInt(ONE, rs.getInt(empId));
				stmtr.setInt(ONE, rs.getInt(empId));
				rsg = stmtg.executeQuery();
				rsr = stmtr.executeQuery();

				rsg.next();
				rsr.next();
				tdata.add(new Tbl(rs.getString(name),rs.getInt(gowork),rs.getInt(rest),rs.getInt(paid),rs.getInt(spv)
						,rs.getInt(trip),rs.getInt(late),rs.getInt(early),rsg.getInt(gostraight),rsr.getInt(rt)));
			}
			tblvAttendance.setItems(tdata);
			stmt.close();
			rs.close();
			rsg.close();
			rsr.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private void clearCboxAllData(ComboBox<String> cbox) {
		cbox.getItems().clear();
		cbox.getItems().add("");
	}
	public class Tbl{
		private final StringProperty name;
		private final IntegerProperty gowork;
		private final IntegerProperty rest;
		private final IntegerProperty paid;
		private final IntegerProperty spv;
		private final IntegerProperty trip;
		private final IntegerProperty late;
		private final IntegerProperty early;
		private final IntegerProperty gostraight;
		private final IntegerProperty rt;

		public Tbl(String name,int gowork,int rest,int paid,int spv,int trip,int late,int early,int gostraight,int rt) {
			this.name = new SimpleStringProperty(name);
			this.gowork = new SimpleIntegerProperty(gowork);
			this.rest = new SimpleIntegerProperty(rest);
			this.paid = new SimpleIntegerProperty(paid);
			this.spv = new SimpleIntegerProperty(spv);
			this.trip = new SimpleIntegerProperty(trip);
			this.late = new SimpleIntegerProperty(late);
			this.early = new SimpleIntegerProperty(early);
			this.gostraight = new SimpleIntegerProperty(gostraight);
			this.rt = new SimpleIntegerProperty(rt);
		}

		public final String getName() {return name.get();}
		public final int getGowork() {return gowork.get();}
		public final int getRest() {return rest.get();}
		public final int getPaid() {return paid.get();}
		public final int getSpv() {return spv.get();}
		public final int getTrip() {return trip.get();}
		public final int getLate() {return late.get();}
		public final int getEarly() {return early.get();}
		public final int getGostraight() {return gostraight.get();}
		public final int getRt() {return rt.get();}

		public final void setName(String name) {this.name.set(name);}
		public final void setGowork(int gowork) {this.gowork.set(gowork);}
		public final void setRest(int rest) {this.rest.set(rest);}
		public final void setPaid(int paid) {this.paid.set(paid);}
		public final void setSpv(int spv) {this.spv.set(spv);}
		public final void setTrip(int trip) {this.trip.set(trip);}
		public final void setLate(int late) {this.late.set(late);}
		public final void setEarly(int early) {this.early.set(early);}
		public final void setGostraight(int gostraight) {this.gostraight.set(gostraight);}
		public final void setRt(int rt) {this.rt.set(rt);}

		public final StringProperty nameProperty() {return name;}
		public final IntegerProperty goworkProperty() {return gowork;}
		public final IntegerProperty restProperty() {return rest;}
		public final IntegerProperty paidProperty() {return paid;}
		public final IntegerProperty spvProperty() {return spv;}
		public final IntegerProperty tripProperty() {return trip;}
		public final IntegerProperty lateProperty() {return late;}
		public final IntegerProperty earlyProperty() {return early;}
		public final IntegerProperty gostraightProperty() {return gostraight;}
		public final IntegerProperty rtProperty() {return rt;}
	}

}
