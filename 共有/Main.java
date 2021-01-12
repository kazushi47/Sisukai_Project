package jp.ac.hsc.system;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	private static Main instance;					// ステージサイズの変更不可
	;					// Mainクラス(自身)のインスタンス
	private static Stage stage;					// ステージ

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;							// 自身のインスタンスを保存
		stage = primaryStage;						// ステージの保存
		sendSlctController();
		stage.setOnCloseRequest(e -> e.consume());	// ×ボタン処理
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * ステージ返却
	 * @return
	 */
	public static Stage getStage() {
		return stage;
	}


	/**
	 * インスタンス返却
	 * @return
	 */
	public static Main getInstance() {
		return instance;
	}

	/**
	 * 初期選択画面に遷移
	 * @param subName
	 */
	public void sendSlctController() {
		stage.setTitle("初期選択画面");
		SlctController controller = new SlctController();
		replaceScene(controller);
	}

	/**
	 * ログイン画面に遷移
	 * @param subName
	 */
	public void sendLoginController(String subName) {
		stage.setTitle("ログイン画面");
		LoginController controller = new LoginController(subName);
		replaceScene(controller);
	}


	/**
	 * 出退勤選択画面に遷移
	 * @param op
	 */
	public void sendASlctController(Emp op) {
		stage.setTitle("出退勤画面");
		ASlctController controller = new ASlctController();
		replaceScene(controller);
	}

	/**
	 * 休憩選択画面に遷移
	 * @param op
	 */
	public void sendARestController(Emp op) {
		stage.setTitle("休憩画面");
		ARestController controller = new ARestController();
		replaceScene(controller);
	}

	/**
	 * 直行画面に遷移
	 * @param op
	 */
	public void sendADrctAController(Emp op) {
		stage.setTitle("直行画面");
		ADrctAController controller = new ADrctAController();
		replaceScene(controller);
	}

	/**
	 * 直帰画面に遷移
	 * @param op
	 */
	public void sendADrctBController(Emp op) {
		stage.setTitle("直帰画面");
		ADrctBController controller = new ADrctBController();
		replaceScene(controller);
	}

	/**
	 * 給与機能選択画面に遷移
	 * @param op
	 */
	public void sendSSlctController(Emp op) {
		stage.setTitle("給与機能選択画面");
		SSlctController controller = new SSlctController(op);
		replaceScene(controller);
	}

	/**
	 * 給与一覧画面に遷移
	 * @param op
	 */
	public void sendSSvController(Emp op) {
		stage.setTitle("給与一覧画面");
		SSvController controller = new SSvController();
		replaceScene(controller);
	}

	/**
	 * 給与詳細画面に遷移
	 * @param op
	 */
	public void sendSSavController(Emp op) {
		stage.setTitle("給与詳細画面");
		SSavController controller = new SSavController();
		replaceScene(controller);
	}

	/**
	 * 出退勤一覧画面に遷移
	 * @param op
	 */
	public void sendSAvController(Emp op) {
		stage.setTitle("出退勤一覧画面");
		SAvController controller = new SAvController(op);
		replaceScene(controller);
	}

	/**
	 * 出退勤一覧画面に遷移
	 * @param op
	 */
	public void sendSAvController(Emp op,String divName,int year,int month) {
		stage.setTitle("出退勤一覧画面");
		SAvController controller = new SAvController(op,divName,year,month);
		replaceScene(controller);
	}
	/**
	 * 出退勤詳細画面に遷移
	 * @param op
	 */
	public void sendSAavController(Emp op,int empId,String divName,int year,int month) {
		stage.setTitle("出退勤詳細画面");
		SAavController controller = new SAavController(op,empId,divName,year,month);
		replaceScene(controller);
	}

	/**
	 * シーンの切り替え
	 * 最初はsceneがnull
	 * それ以外はelse
	 * @param controller
	 */
	private void replaceScene(Parent controller) {
		Scene scene = stage.getScene();		// ステージからシーンを取得
		if (scene == null) {							// まだステージにシーンが設定されていない場合：初期画面時
			scene = new Scene(controller);		// コントローラを使用してシーンの生成
			stage.setScene(scene);					// ステージにシーンを設定
		} else {												// すでにステージにシーンが設定されている場合：画面遷移時
			scene.setRoot(controller);				// ルートノードにコントローラを設定してシーンを置換する
		}
	}

}
