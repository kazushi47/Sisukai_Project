import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * メインクラス
 */
public class Main extends Application {
    private final String STAGE_TITLE = "出退勤管理・給与計算システム";
    private final String EXITALERT_TITLE = "修了確認";
    private final String EXITALERT_MESSAGE = "アプリケーションを終了しますか？";
    
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
        /* Stageタイトルの設定 */
        stage.setTitle(STAGE_TITLE);
        
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
        
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController);
        stage.setScene(scene);
        
        /* Stageの表示 */
        stage.show();
    }
}
