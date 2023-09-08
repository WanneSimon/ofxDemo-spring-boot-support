package cc.wanforme.ofxDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/** 图片选择器
 * @author wanne
 *
 */
@Component
public class ImgSelectorHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ImgSelectorHandler.class);
	/** 文件夹路径 */
	private String path;
	/** 文件夹对象 */
	private File root;
	/** 检测图片文件的 tika 对象 */
	private Tika tika;
	
	
	private int defailtLimit = 500;
	
	private String lastSearch;
	
	public ImgSelectorHandler() {
//		this("G:\\Q图MessicPictures");
		this("D:\\Picture");
	}
	
	public ImgSelectorHandler(String path) {
		super();
		this.init(path);
	}
	
	public void init(String path) {
		if(!Objects.equals(this.path, path)) {
			this.path = path;
			root = new File(path);
		}
		
		if(tika == null) {
			tika = new Tika();
		}
	}
	
	public void refresh(String name, FlowPane flowPane) {
		this.setStyles(flowPane);
		this.showSelector(name, flowPane);
	}
	
	/** 文件夹是否有效 */
	public boolean isValid() {
		return root.exists() && root.isDirectory() ;
	}
	
	/** 展示文件夹下的所有图片 */
	public void showSelector(String name, FlowPane flowPane) {
		if(Objects.equals(lastSearch, name)) {
			return;
		}
		
		flowPane.getChildren().clear();
		
		if(!isValid()) {
			return;
		}
		
		File[] files = root.listFiles();
		if(files == null || files.length==0) {
			return;
		}
		
		int count = 0;
		for (File file : files) {
			if(!this.isImage(file)) {
				continue;
			}
			
			if(name!=null && !file.getName().contains(name)) {
				continue;
			}
			
			try {
				Pane imagePane = this.createImagePane(file);
				if(imagePane != null) {
					flowPane.getChildren().add(imagePane);
					count++;
				}
			} catch (FileNotFoundException e) {
				LOG.error("Not existed！ " + file.getAbsolutePath(), e);
			}
			
			if(name == null && count >= defailtLimit) {
				break;
			}
		}
		LOG.info("find: "+count);
		this.lastSearch = name;
	}
	
	/** 样式 */
	public void setStyles(FlowPane flowPane) {
//		Insets padding = new Insets(10, 0, 10, 0);
//		flowPane.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		flowPane.setAlignment(Pos.TOP_CENTER);
//		flowPane.setPadding(padding);
		
	}
	
	/** 显示某个图片 */
	private Pane createImagePane(File file) throws FileNotFoundException {
		Image image = new Image(new FileInputStream(file));
		if(image.isError()) {
			LOG.error("Image load error! " + file.getAbsolutePath(), image.getException());
			return null;
		}
		
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(100);
		imageView.setFitHeight(80);
		
		// 添加双击复制事件
		this.addDoubleClickEvent(imageView, file);
		
		Label textFlow = new Label(file.getName());

		VBox vBox = new VBox(imageView, textFlow);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPrefWidth(imageView.getFitWidth());
		vBox.setPrefHeight(imageView.getFitHeight());
		
		Insets padding = new Insets(5, 5, 5, 5);
		vBox.setPadding(padding);
		
		return vBox;
	}
	
	/** 鼠标双击事件 */
	private void addDoubleClickEvent(ImageView imageView, File pic) {
		imageView.setOnMouseClicked(e -> {
			if(e.getClickCount() != 2) {
				return;
			}
			
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			try {
				Image image = new Image(new FileInputStream(pic));
	        	content.putImage(image);

				List<File> fileList = new ArrayList<>(1);
				fileList.add(pic);
				content.putFiles(fileList);

	        	clipboard.setContent(content);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		
	}
	
	/** 判断是否是图片文件 */
	private boolean isImage(File file) {
		if(!file.isFile()) {
			return false;
		}
		
		try {
			String detect = tika.detect(file);
			return detect.startsWith("image/");
		} catch (Exception e) {
			LOG.error("文件类型检测失败! " + file.getAbsolutePath(), file);
			return false;
		}
	}
	
	public String getPath() {
		return path;
	}
	
}
