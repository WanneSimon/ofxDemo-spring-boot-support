package cc.wanforme.ofxDemo;

import org.springframework.beans.factory.annotation.Autowired;

import cc.wanforme.ofx.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

@FXMLController
public class ImageSearchController {
	// 声明的顺序必须和 SceneBuilder 中的一致
	
    @FXML
    private FlowPane imgShowContainer;
	@FXML
	private TextField pathInput;
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

	public void exit(){
		System.exit(0);
	}
	
}
