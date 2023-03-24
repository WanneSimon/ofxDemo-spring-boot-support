package cc.wanforme.ofxDemo;

import cc.wanforme.ofx.BaseView;
import cc.wanforme.ofx.FXMLView;

/**
 * this is a test case for multiple baseView
 */
@FXMLView(path = "fxml/ImageSelector.fxml")
public class TestView2 extends BaseView{

	@Override
	public void loaded() {
		// do something after fxml loaded
		String css = getClass().getResource("/fxml/root.css").toExternalForm();
		System.out.println("css resource: " + css);
		getPane().getStylesheets().add(css);
	}


}
