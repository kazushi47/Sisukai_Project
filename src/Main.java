
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * メインクラス
 */
public class Main extends Application {
    private final String STAGE_TITLE = "出退勤管理・給与計算システム";
    private final String EXITALERT_TITLE = "終了確認";
    private final String EXITALERT_MESSAGE = "アプリケーションを終了しますか？";

    /** Mainクラス自身のインスタンス */
    private static Main instance;
    /** ステージ保存用 */
    private static Stage stage;
    
    /**
     * メインメソッド
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        launch();
    }
    
    /**
     * JavaFXプログラム開始時に実行する。
     * 画面表示までの処理。
     * また、ステージの初期設定。
     * @param stage デフォルトのステージ 
     */
    @Override
    public void start(Stage stage) throws Exception {
        /* 自身のインスタンスを保存 */
        instance = this;

        /* ステージを保存 */
        Main.stage = stage;
        
        /* サイズ変更を不可にする */
        stage.setResizable(false);
        
        /* xボタン処理 */
        stage.setOnCloseRequest(e -> {
            /* 修了確認ダイアログ */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(EXITALERT_TITLE);
            alert.setContentText(EXITALERT_MESSAGE);
            ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (buttonType == ButtonType.CANCEL) {
                e.consume();    // 無効
            }
        });
        
        /* Stage終了後の処理 */
        stage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == true && newValue == false) {
                Platform.exit();
            }
        });
        
        /* 最初の画面遷移 */
        sendSlctController();
        
        /* Stageの表示 */
        stage.show();
    }

    /**
     * シーンの切り替え
     * 
     * 最初はsceneがnull
     * それ以外はelse
     * @param controller
     */
    private void replaceScene(Parent controller) {
        /* ステージからシーンを取得 */
        Scene scene = stage.getScene();

        if (scene == null) {
            /* まだステージにシーンが設定されていない場合：初期画面時 */
            scene = new Scene(controller);
            stage.setScene(scene);
        } else {
            /* すでにステージにシーンが設定されている場合：画面遷移時 */
            scene.setRoot(controller);
        }
    }

    /**
     * 保存したステージを返却
     * @return ステージ
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * 自身のインスタンスを返却
     * @return Mainインスタンス
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * 初期選択画面に遷移
     */
    public void sendSlctController() {
        stage.setTitle(STAGE_TITLE + " - 初期選択画面");
        SlctController controller = new SlctController();
        replaceScene(controller);
    }

    /**
     * ログイン画面に遷移
     * @param subName A or S
     */
    public void sendLoginController(String subName) {
        stage.setTitle(STAGE_TITLE + " - ログイン画面");
        LoginController controller = new LoginController(subName);
        replaceScene(controller);
    }

    /**
     * 出退勤選択画面に遷移
     * @param op オペレータ(操作している社員)
     */
    public void sendASlctController(Emp op) {
        stage.setTitle(STAGE_TITLE + " - 出退勤選択画面");
        ASlctController controller = new ASlctController();
        replaceScene(controller);
    }

    // TODO 休憩選択画面に遷移

    // TODO 直行画面に遷移

    // TODO 直帰画面に遷移

    /**
     * 給与機能選択画面に遷移
     * @param op オペレータ(操作している経理部社員)
     */
    public void sendSSlctController(Emp op) {
        stage.setTitle(STAGE_TITLE + " - 給与機能選択画面");
        SSlctController controller = new SSlctController(op);
        replaceScene(controller);
    }

    /**
     * 出退勤概要画面に遷移1
     * @param op オペレータ
     */
    public void sendSAvController(Emp op) {
        stage.setTitle(STAGE_TITLE + " - 出退勤概要画面");
        SAvController controller = new SAvController(op);
        replaceScene(controller);
    }

    /**
     * 出退勤概要画面に遷移2
     * @param op オペレータ
     * @param divName 対象者の部署名
     * @param year 対象期間の年
     * @param month 対象期間の月
     */
    public void sendSAvController(Emp op, String divName, int year, int month) {
        stage.setTitle(STAGE_TITLE + " - 出退勤概要画面");
        SAvController controller = new SAvController(op, divName, year, month);
        replaceScene(controller);
    }

    /**
     * 出退勤明細画面に遷移
     * @param op オペレータ
     */
    public void sendSAavController(Emp op, int empId, String divName, int year, int month) {
        stage.setTitle(STAGE_TITLE + " - 出退勤明細画面");
        SAavController controller = new SAavController(op, empId, divName, year, month);
        replaceScene(controller);
    }

    /**
     * 給与概要画面
     * @param op オペレータ
     */
    public void sendSSvController(Emp op) {
        stage.setTitle(STAGE_TITLE + " - 給与概要画面");
        SSvController controller = new SSvController(op);
        replaceScene(controller);
    }

    /**
     * 給与明細画面
     * @param op オペレータ
     * @param empId 対象社員ID
     */
    public void sendSSavController(Emp op, int empId, int year, int month) {
        stage.setTitle(STAGE_TITLE + " - 給与明細画面");
        SSavController controller = new SSavController(op, empId, year, month);
        replaceScene(controller);
    }
}
