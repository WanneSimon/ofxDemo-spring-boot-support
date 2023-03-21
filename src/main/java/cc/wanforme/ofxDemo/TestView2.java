package cc.wanforme.ofxDemo;

import cc.wanforme.ofx.BaseView;
import cc.wanforme.ofx.FXMLView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * this is a test case for multiple baseView
 */
@FXMLView(path = "fxml/ImageSelector.fxml")
public class TestView2 extends BaseView{

	@Override
	public void loaded() {
		// do something after fxml loaded
		getPane().getStylesheets().add("fxml/root.css");
	}

}
