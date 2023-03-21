module cc.wanforme.ofxDemo {
	requires cc.wanforme.ofx;
	
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	requires spring.beans;
	requires spring.boot.autoconfigure;
	requires spring.core;
	
	requires org.apache.tika.core;
	requires org.slf4j;
	
	//requires java.base;
	requires transitive java.sql;
	
	exports cc.wanforme.ofxDemo;
	
	opens cc.wanforme.ofxDemo;
}
