import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://Database.accdb");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from babyCares");
        String str = "";
        while (rs.next()) {
            str += String.valueOf(rs.getInt("empId")) + "\n";
        }
        rs.close();
        st.close();
        conn.close();
        Label l = new Label(str);
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
