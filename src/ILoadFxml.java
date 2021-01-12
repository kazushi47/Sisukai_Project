import java.io.IOException;

import javafx.fxml.FXMLLoader;

/**
 * 画面遷移のある全てのシーンに適用するインタフェース。
 * 既に定義されているloadFxml(String fxml)を使用できるようになる。
 */
public interface ILoadFxml {

    /**
     * 画面遷移を行う。
     * あらかじめFXMLのリソースがフィールドで定義されているMainクラスが必要。
     * IOException例外発生時にはRuntimeException例外を通知する。
     * @param fxml String型でMainクラスにあるFXMLのリソース名。
     */
    default void loadFxml(String fxml) {
        // レイアウト取得
		FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
		loader.setRoot(this);					// ルートノードの設定
		loader.setController(this);				// コントローラの設定
		try {
			loader.load();						// レイアウトのロード(try-catch構文必須)
		} catch (IOException e) {
			throw new RuntimeException(e);		// 例外時は、実行時例外を通知する
		}
    }
}
