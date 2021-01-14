
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ComboBox<CboxValue> cboxEmpName;

    @FXML
    private ComboBox<CboxValue> cboxYear;

    @FXML
    private ComboBox<CboxValue> cboxMonth;

    @FXML
    private TableView<TblValue> tblvBase;

    @FXML
    private TableColumn<TblValue, String> tblcBaseItemType;

    @FXML
    private TableColumn<TblValue, String> tblcBaseItem;

    @FXML
    private TableColumn<TblValue, Integer> tblcBaseMoney;

    @FXML
    private TableView<TblValue> tblvDate;

    @FXML
    private TableColumn<TblValue, String> tblcDateItem;

    @FXML
    private TableColumn<TblValue, Integer> tblcDate;

    @FXML
    private TableView<TblValue> tblvAllMoney;

    @FXML
    private TableColumn<TblValue, String> tblcAllItem;

    @FXML
    private TableColumn<TblValue, Integer> tblcAllMoney;

    @FXML
    private Label lblDivName;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblName;

    /** DBコネクション */
    private Connection connection = DBconnect.getConnection();
    /** オペレータ */
    private Emp op;
    /** テーブルtblvBaseのデータ */
    private ObservableList<TblValue> bases = FXCollections.observableArrayList();
    /** テーブルtblvDateのデータ */
    private ObservableList<TblValue> dates = FXCollections.observableArrayList();
    /** テーブルtblvAllMoneyのデータ */
    private ObservableList<TblValue> allMoneys = FXCollections.observableArrayList();

    public SSavController(Emp op, int empId, int year, int month) {
        loadFxml(FXML_SSav);
        this.op = op;

        /* オペレータ情報の表示 */
        lblDivName.setText(op.getDivName());
        lblEmpId.setText(op.getEmpId());
        lblName.setText(op.getName());

        /* コンボボックスcboxEmpNameの設定 */
        ObservableList<CboxValue> emps = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select empId, name from employees where divId in(select divId from employees where empId = ?)"
            );
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emps.add(new CboxValue(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int emp_idx = 0;
        while (emps.get(emp_idx).value != empId) {
            emp_idx++;
        }
        cboxEmpName.setItems(emps);
        cboxEmpName.getSelectionModel().select(emp_idx);

        /* コンボボックスcboxYearの設定 */
        ObservableList<CboxValue> years = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "select distinct year(date) from salarys"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                years.add(new CboxValue(rs.getInt(1), rs.getInt(1) + "年"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int year_idx = 0;
        while (years.get(year_idx).value != year) {
            year_idx++;
        }
        cboxYear.setItems(years);
        cboxYear.getSelectionModel().select(year_idx);

        /* コンボボックスcboxMonthの設定 */
        ObservableList<CboxValue> months = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "select distinct month(date) from salarys"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                months.add(new CboxValue(rs.getInt(1), rs.getInt(1) + "月分"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int month_idx = 0;
        while (months.get(month_idx).value != month) {
            month_idx++;
        }
        cboxMonth.setItems(months);
        cboxMonth.getSelectionModel().select(month_idx);
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

        cboxEmpName.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                /* 値が変更された時の処理(但し最初も呼ばれる) */
                changeTblData();
            }
        });
        cboxYear.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                /* 値が変更された時の処理(但し最初も呼ばれる) */
                changeTblData();
            }
        });
        cboxMonth.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                /* 値が変更された時の処理(但し最初も呼ばれる) */
                changeTblData();
            }
        });

        /* 編集ボタンを無効にする */
        btnEddite.setDisable(true);

        /* 確定ボタンを無効にする */
        btnEnter.setDisable(true);

        /* テーブルビューの編集を無効にする */
        tblvBase.setEditable(false);
        tblvDate.setEditable(false);
        tblvAllMoney.setEditable(false);

        /* テーブルビューにデータを設定 */
        tblvBase.itemsProperty().setValue(bases);
        tblvBase.setItems(bases);
        tblvDate.itemsProperty().setValue(dates);
        tblvDate.setItems(dates);
        tblvAllMoney.itemsProperty().setValue(allMoneys);
        tblvAllMoney.setItems(allMoneys);

        /* テーブルカラムの設定 */
        tblcBaseItemType.setCellValueFactory(new PropertyValueFactory<>("type_name"));
        tblcBaseItem.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tblcBaseMoney.setCellValueFactory(new PropertyValueFactory<>("money_value"));
        tblcDateItem.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tblcDate.setCellValueFactory(new PropertyValueFactory<>("money_value"));
        tblcAllItem.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tblcAllMoney.setCellValueFactory(new PropertyValueFactory<>("money_value"));
    }

    /**
     * テーブルデータを変更
     */
    private void changeTblData() {
        if (cboxEmpName.getValue() != null && cboxYear.getValue() != null && cboxMonth.getValue() != null) {
            int empId = cboxEmpName.getValue().value;
            int year = cboxYear.getValue().value;
            int month = cboxMonth.getValue().value;
            try {
                /* テーブルビューtblvBase用データをデータベースから取得 */
                PreparedStatement psForBases = connection.prepareStatement(
                    "select ageSalary, abilitySalary, jobTitleSalary, specialWorkSalary, controlSalary, commuteSalary, businessTripSalary, overWorkSalary, holidayWorkSalary, nightWorkingSalary, specialHolidaySalary "
                    + "from salarys where empId = ? and date between ? and ?"
                );
                psForBases.setInt(1, empId);
                psForBases.setDate(2, Date.valueOf(year + "-" + month + "-21"));
                psForBases.setDate(3, Date.valueOf(year + "-" + (month + 1) + "-20"));
                ResultSet rsForBases = psForBases.executeQuery();

                /* テーブルビューtblvDate用データをデータベースから取得 */
                PreparedStatement psForDates = connection.prepareStatement(
                    "select overWorkTime, holidayWorkTime, nightWorkTime, targetspecialHolidays, notWorkTime, paidHolidays "
                    + "from salarys where empId = ? and date between ? and ?"
                );
                psForDates.setInt(1, empId);
                psForDates.setDate(2, Date.valueOf(year + "-" + month + "-21"));
                psForDates.setDate(3, Date.valueOf(year + "-" + (month + 1) + "-20"));
                ResultSet rsForDates = psForDates.executeQuery();

                /* テーブルビューtblvAllMoney用データをデータベースから取得 */
                PreparedStatement psForAllMoneys = connection.prepareStatement(
                    "select ageSalary + abilitySalary, jobTitleSalary + specialWorkSalary + controlSalary + commuteSalary + businessTripSalary, overWorkSalary + holidayWorkSalary + nightWorkingSalary + specialHolidaySalary, "
                    + "deduction, ageSalary + abilitySalary + jobTitleSalary + specialWorkSalary + controlSalary + commuteSalary + businessTripSalary + overWorkSalary + holidayWorkSalary + nightWorkingSalary + specialHolidaySalary - deduction "
                    + "from salarys where empId = ? and date between ? and ?"
                );
                psForAllMoneys.setInt(1, empId);
                psForAllMoneys.setDate(2, Date.valueOf(year + "-" + month + "-21"));
                psForAllMoneys.setDate(3, Date.valueOf(year + "-" + (month + 1) + "-20"));
                ResultSet rsForAllMoneys = psForAllMoneys.executeQuery();

                /* テーブルビューの既存データのクリア */
                bases.clear();
                dates.clear();
                allMoneys.clear();

                /* データの登録 */
                if (rsForBases.next()) {
                    bases.add(new TblValue("基本給", "年齢給", rsForBases.getInt(1)));
                    bases.add(new TblValue("基本給", "職能給", rsForBases.getInt(2)));
                    bases.add(new TblValue("諸手当", "役職手当", rsForBases.getInt(3)));
                    bases.add(new TblValue("諸手当", "特務手当", rsForBases.getInt(4)));
                    bases.add(new TblValue("諸手当", "調整手当", rsForBases.getInt(5)));
                    bases.add(new TblValue("諸手当", "通勤手当", rsForBases.getInt(6)));
                    bases.add(new TblValue("諸手当", "出張手当", rsForBases.getInt(7)));
                    bases.add(new TblValue("時間外給与", "時間外勤務給与", rsForBases.getInt(8)));
                    bases.add(new TblValue("時間外給与", "休日勤務給与", rsForBases.getInt(9)));
                    bases.add(new TblValue("時間外給与", "深夜勤務給与", rsForBases.getInt(10)));
                    bases.add(new TblValue("時間外給与", "特別休暇給与", rsForBases.getInt(11)));
                }
                if (rsForDates.next()) {
                    dates.add(new TblValue("", "時間外勤務時数", rsForDates.getInt(1)));
                    dates.add(new TblValue("", "休日勤務時数", rsForDates.getInt(2)));
                    dates.add(new TblValue("", "深夜勤務時数", rsForDates.getInt(3)));
                    dates.add(new TblValue("", "対象特別休暇日数", rsForDates.getInt(4)));
                    dates.add(new TblValue("", "非就業時間", rsForDates.getInt(5)));
                    dates.add(new TblValue("", "有給休暇日数", rsForDates.getInt(6)));
                }
                if (rsForAllMoneys.next()) {
                    allMoneys.add(new TblValue("", "基本給合計", rsForAllMoneys.getInt(1)));
                    allMoneys.add(new TblValue("", "諸手当合計", rsForAllMoneys.getInt(2)));
                    allMoneys.add(new TblValue("", "時間外給与合計", rsForAllMoneys.getInt(3)));
                    allMoneys.add(new TblValue("", "控除額", rsForAllMoneys.getInt(4)));
                    allMoneys.add(new TblValue("", "給与合計", rsForAllMoneys.getInt(5)));
                }

                rsForBases.close();
                psForBases.close();
                rsForDates.close();
                psForDates.close();
                rsForAllMoneys.close();
                psForAllMoneys.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * コンボボックスの値の型
     */
    private class CboxValue {
        /** 値 */
        public int value;
        /** 表示する文字列 */
        public String display_str;

        /**
         * メインコンストラクタ
         * @param value 値
         * @param display_str 表示する文字列
         */
        public CboxValue(int value, String display_str) {
            this.value = value;
            this.display_str = display_str;
        }

        @Override
        public String toString() {
            return display_str;
        }
    }

    /**
     * テーブルビューの値の型
     */
    public class TblValue {
        /** 項目種別 */
        public StringProperty type_name;
        /** 項目 */
        public StringProperty item_name;
        /** 金額 */
        public IntegerProperty money_value;

        /**
         * メインコンストラクタ
         * @param type_name 項目種別
         * @param item_name 項目
         * @param money_value 金額
         */
        public TblValue(String type_name, String item_name, int money_value) {
            this.type_name = new SimpleStringProperty(type_name);
            this.item_name = new SimpleStringProperty(item_name);
            this.money_value = new SimpleIntegerProperty(money_value);
        }

        /**
         * 項目種別
         * @return 項目種別
         */
        public String getType_name() {
            return type_name.get();
        }

        /**
         * 項目種別
         * @param type_name 項目種別
         */
        public void setType_name(String type_name) {
            this.type_name = new SimpleStringProperty(type_name);
        }

        /**
         * 項目
         * @return 項目
         */
        public String getItem_name() {
            return item_name.get();
        }

        /**
         * 項目
         * @param item_name 項目
         */
        public void setItem_name(String item_name) {
            this.item_name = new SimpleStringProperty(item_name);
        }

        /**
         * 金額
         * @return 金額
         */
        public int getMoney_value() {
            return money_value.get();
        }

        /**
         * 金額
         * @param money_value 金額
         */
        public void setMoney_value(int money_value) {
            this.money_value = new SimpleIntegerProperty(money_value);
        }
    }
}
