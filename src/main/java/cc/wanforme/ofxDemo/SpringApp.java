package cc.wanforme.ofxDemo;

import cc.wanforme.ofx.BaseOFXApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApp{

	public static void main(String[] args) {
		BaseOFXApplication.launchOFX(OFXApp.class, SpringApp.class, TestView.class, args);
	}

}
