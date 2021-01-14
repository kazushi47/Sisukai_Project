
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
 * 給与概要画面
 */
public class SSvController extends GridPane implements Common, ILoadFxml, Initializable {

    @FXML
    private GridPane SSV;

    @FXML
    private ComboBox<CboxValue> cboxDiv;

    @FXML
    private ComboBox<CboxValue> cboxYear;

    @FXML
    private ComboBox<CboxValue> cboxMonth;

    @FXML
    private Button btnSSv;

    @FXML
    private TableView<TblValue> tblvSv;

    @FXML
    private TableColumn<TblValue, String> tblcEmpName;

    @FXML
    private TableColumn<TblValue, Integer> tblcSumSal;

    @FXML
    private TableColumn<TblValue, Integer> tblcBasicSal;

    @FXML
    private TableColumn<TblValue, Integer> tblcAllowance;

    @FXML
    private TableColumn<TblValue, Integer> tblcTimeout;

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

    /** DBコネクション */
    private Connection connection = DBconnect.getConnection();
    /** オペレータ */
    private Emp op;
    /** テーブルデータ */
    private ObservableList<TblValue> tblData = FXCollections.observableArrayList();

    public SSvController(Emp op) {
        loadFxml(FXML_SSv);
        this.op = op;

        /* オペレータ情報の表示 */
        lblDivName.setText(op.getDivName());
        lblEmpId.setText(op.getEmpId());
        lblName.setText(op.getName());

        /* コンボボックスcboxDivの設定 */
        ObservableList<CboxValue> divs = FXCollections.observableArrayList();
        try (
            PreparedStatement ps = connection.prepareStatement("select divId, divName from divisions");
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                divs.add(new CboxValue(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cboxDiv.setItems(divs);
        cboxDiv.getSelectionModel().select(0);

        /* コンボボックスcboxYearの設定 */
        ObservableList<CboxValue> years = FXCollections.observableArrayList();
        try (
            PreparedStatement ps = connection.prepareStatement("select distinct year(date) from salarys");
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                years.add(new CboxValue(rs.getInt(1), rs.getInt(1) + "年"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cboxYear.setItems(years);
        cboxYear.getSelectionModel().select(0);

        /* コンボボックスcboxMonthの設定 */
        ObservableList<CboxValue> months = FXCollections.observableArrayList();
        try (
            PreparedStatement ps = connection.prepareStatement("select distinct month(date) from salarys");
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                months.add(new CboxValue(rs.getInt(1), rs.getInt(1) + "月分"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cboxMonth.setItems(months);
        cboxMonth.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLogout.setOnAction(event -> {
            /* ログイン画面へ */
            Main.getInstance().sendLoginController(S);
        });

        btnSSlct.setOnAction(event -> {
            /* 給与選択画面へ */
            Main.getInstance().sendSSlctController(op);
        });

        btnCalc.setOnAction(event -> {
            /* 給与再計算を行い、給与概要画面(自身)に遷移 */
            if (cboxDiv.getValue() != null && cboxYear.getValue() != null && cboxMonth.getValue() != null) {
                int divId = cboxDiv.getValue().value;
                int year = cboxYear.getValue().value;
                int month = cboxMonth.getValue().value;
                try {
                    new CalcSalary(divId, year + "-" + month + "-21", year + "-" + (month + 1) + "-20").executeCalc(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.getInstance().sendSSvController(op);
            }
        });

        /* 自身に遷移する必要はないので、給与概要ボタン非活性 */
        btnSSv.setDisable(true);

        btnSAv.setOnAction(event -> {
            /* 出退勤概要画面へ */
            Main.getInstance().sendSAvController(op, cboxDiv.getValue().display_str, cboxYear.getValue().value, cboxMonth.getValue().value);
        });

        cboxDiv.valueProperty().addListener((observable, oldValue, newValue) -> {
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

        /* テーブルビューtblvSvの編集を無効 */
        tblvSv.setEditable(false);

        /* テーブルビューtblSvにデータtblDataを設定 */
        tblvSv.itemsProperty().setValue(tblData);
        tblvSv.setItems(tblData);

        /* テーブルカラムの設定 */
        tblcEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblcSumSal.setCellValueFactory(new PropertyValueFactory<>("sumSalarys"));
        tblcBasicSal.setCellValueFactory(new PropertyValueFactory<>("sumBasics"));
        tblcAllowance.setCellValueFactory(new PropertyValueFactory<>("sumAllowances"));
        tblcTimeout.setCellValueFactory(new PropertyValueFactory<>("sumOvertimes"));

        tblvSv.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                /* ダブルクリックで対象社員の給与明細画面へ */
                Main.getInstance().sendSSavController(op, tblvSv.getSelectionModel().getSelectedItem().empId, cboxYear.getValue().value, cboxMonth.getValue().value);
            }
        });
    }

    /**
     * テーブルデータを変更
     */
    private void changeTblData() {
        if (cboxDiv.getValue() != null && cboxYear.getValue() != null && cboxMonth.getValue() != null) {
            int divId = cboxDiv.getValue().value;
            int year = cboxYear.getValue().value;
            int month = cboxMonth.getValue().value;
            try {
                PreparedStatement ps = connection.prepareStatement(
                    "select e.empId, e.name, s.ageSalary + s.abilitySalary + s.jobTitleSalary + s.specialWorkSalary + s.controlSalary + s.commuteSalary + s.businessTripSalary, s.overWorkSalary + s.holidayWorkSalary + s.nightWorkingSalary + s.specialHolidaySalary - s.deduction, "
                     + "s.ageSalary + s.abilitySalary, s.jobTitleSalary + s.specialWorkSalary + s.controlSalary + s.commuteSalary + s.businessTripSalary, s.overWorkSalary + s.holidayWorkSalary + s.nightWorkingSalary + s.specialHolidaySalary "
                     + "from employees e inner join salarys s on e.empId = s.empId "
                     + "where e.divId = ? and s.date between ? and ?"
                );
                ps.setInt(1, divId);
                ps.setDate(2, Date.valueOf(year + "-" + month + "-" + 21));
                ps.setDate(3, Date.valueOf(year + "-" + (month + 1) + "-" + 20));
                ResultSet rs = ps.executeQuery();
                tblData.clear();
                while (rs.next()) {
                    tblData.add(new TblValue(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
                }
                rs.close();
                ps.close();
            } catch (Exception e) {
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
        /** 社員ID(テーブルビューには非表示) */
        public int empId;

        /** 社員名 */
        public StringProperty empName;
        /** 給与合計 */
        public IntegerProperty sumSalarys;
        /** 基本給 */
        public IntegerProperty sumBasics;
        /** 手当 */
        public IntegerProperty sumAllowances;
        /** 時間外手当 */
        public IntegerProperty sumOvertimes;

        /**
         * メインコンストラクタ
         * @param empName 社員名
         * @param sumSalarys 給与合計
         * @param sumBasics 基本給
         * @param sumAllowances 手当
         * @param sumOvertimes 時間外手当
         */
        public TblValue(int empId, String empName, int sumSalarys, int sumBasics, int sumAllowances, int sumOvertimes) {
            this.empId = empId;
            this.empName = new SimpleStringProperty(empName);
            this.sumSalarys = new SimpleIntegerProperty(sumSalarys);
            this.sumBasics = new SimpleIntegerProperty(sumBasics);
            this.sumAllowances = new SimpleIntegerProperty(sumAllowances);
            this.sumOvertimes = new SimpleIntegerProperty(sumOvertimes);
        }

        /**
         * 社員名
         * @return 社員名
         */
        public String getEmpName() {
            return empName.get();
        }

        /**
         * 社員名
         * @param empName 社員名
         */
        public void setEmpName(String empName) {
            this.empName = new SimpleStringProperty(empName);
        }

        /**
         * 給与合計
         * @return 給与合計
         */
        public int getSumSalarys() {
            return sumSalarys.get();
        }

        /**
         * 給与合計
         * @param sumSalarys 給与合計
         */
        public void setSumSalarys(int sumSalarys) {
            this.sumSalarys = new SimpleIntegerProperty(sumSalarys);
        }

        /**
         * 基本給
         * @return 基本給
         */
        public int getSumBasics() {
            return sumBasics.get();
        }

        /**
         * 基本給
         * @param sumBasics 基本給
         */
        public void setSumBasics(int sumBasics) {
            this.sumBasics = new SimpleIntegerProperty(sumBasics);
        }

        /**
         * 手当
         * @return 手当
         */
        public int getSumAllowances() {
            return sumAllowances.get();
        }

        /**
         * 手当
         * @param sumAllowances 手当
         */
        public void setSumAllowances(int sumAllowances) {
            this.sumAllowances = new SimpleIntegerProperty(sumAllowances);
        }

        /**
         * 時間外手当
         * @return 時間外手当
         */
        public int getSumOvertimes() {
            return sumOvertimes.get();
        }

        /**
         * 時間外手当
         * @param sumOvertimes 時間外手当
         */
        public void setSumOvertimes(int sumOvertimes) {
            this.sumOvertimes = new SimpleIntegerProperty(sumOvertimes);
        }
    }
}