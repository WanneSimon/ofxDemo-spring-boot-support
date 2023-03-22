package cc.wanforme.ofxDemo;

import cc.wanforme.ofx.BaseView;
import cc.wanforme.ofx.ViewHolder;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import cc.wanforme.ofx.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@FXMLController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // controller can't be reused !
public class ImageSearchController {
	// 声明的顺序必须和 SceneBuilder 中的一致
	@Autowired
	private OFXApp app;

    @FXML
    private FlowPane imgShowContainer;
    @FXML
    private TextField imgSearcherInput;

    @Autowired
    private ImgSelectorHandler handler;

	public void inputChange(KeyEvent e) {
		e.consume();
		String inputText = imgSearcherInput.getText();
		if(inputText==null || inputText.trim().equals("")) {
			return;
		}
//		System.out.println("inputText: " + inputText);
//		searchImageExecutor.search(inputText);
		
		if(e.getCode() == KeyCode.ENTER) {
			handler.refresh(inputText, imgShowContainer);
		}
	}

	public void inputPathChange(KeyEvent e) {
		e.consume();
		String inputText = imgSearcherInput.getText();
		if(inputText==null || inputText.trim().equals("")) {
			return;
		}

		if(e.getCode() == KeyCode.ENTER) {
			handler.init(inputText);
		}
	}

	public void exit(Event e){
		e.consume();
		System.exit(0);
	}

	public void changeRootPane(Event e) {
		e.consume();
		BaseView view = ViewHolder.get().getBaseView(TestView.class);
		Stage stage = app.getStage();

		boolean isRoot = view.getLoader().getController() == this;
		if(isRoot) {
			view = ViewHolder.get().getBaseView(TestView2.class);
		}

		// controller can't be reused !
		//view.getLoader().setController(new ImageSearchController());
		Scene scene = view.getScene();
		stage.setScene(scene);
		stage.setTitle(isRoot ? "root" : "test-view 2");
		OFXApp.dragWindow(scene, stage);
	}

}
