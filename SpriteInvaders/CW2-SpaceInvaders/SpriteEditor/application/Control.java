package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;

public class Control {

	private int ColumnNumber, RowNumber;
	@FXML ColorPicker myColorPicker;
	@FXML GridPane gridpane;
	@FXML AnchorPane bottomPane;
	@FXML Button myClean;
	@FXML MenuItem myImage;
	@FXML ScrollPane myScrollPane;
	@FXML MenuItem mySave;
	@FXML MenuItem myExit;
	@FXML Button mySaveButton;
	@FXML Button myLoadButton;
	@FXML MenuItem myDelete;
	@FXML Button OnUserCreate;
	@FXML TextArea ColumnText;
	@FXML TextArea RowText;
	@FXML MenuItem about;
	@FXML CheckBox myPreview;

	@FXML public void onCreate() {								   //for creating a new image
		try{
			ColumnNumber = Integer.parseInt(ColumnText.getText()); //check and convert user input
			RowNumber = Integer.parseInt(RowText.getText());
		}catch(NumberFormatException nfe){						   //if user input wrong number, appear a warning alert
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning :(");
			alert.setHeaderText("Wrong column/row number!!!");
			alert.setContentText("Only allow to input correct positive integer number, and please input less than 100 for good performance in editing your image :).\neg. 10");
			alert.showAndWait();
			return;
		}
		gridpane = new GridPane();									//create a new grid pane for drawing image
		for(int i = 0; i < ColumnNumber; i++){
			ColumnConstraints column = new ColumnConstraints(832/ColumnNumber);//use the width of screen to calculate the line space
			gridpane.getColumnConstraints().add(column);
			
		}
		for(int i = 0; i < RowNumber; i++){
			RowConstraints row = new RowConstraints(832/RowNumber);
			gridpane.getRowConstraints().add(row);
		}
		for(int i = 0; i < ColumnNumber; i++){
			for(int j = 0; j < RowNumber; j++){
				Pane pane = new Pane();								//fill in into each grid with a pane
				pane.setOnMouseClicked(new EventHandler<MouseEvent>() { //set a mouse event when next time clicked, change the background color
					public void handle(MouseEvent event){
						pane.setBackground(new Background(new BackgroundFill(myColorPicker.getValue(), null, null)));
					}
				});
				gridpane.add(pane, i, j);
			}
		}
		gridpane.setGridLinesVisible(true);							//show the grid line
		myScrollPane.setContent(gridpane);							//add this grid pane to scroll pane
	}
	
	@FXML public void onClean() {									//for cleaning the work place
		if(gridpane != null){										//if there has a grid pane, clean it
			gridpane.getChildren().clear();
			gridpane = null;										//set the grid pane to null
		}
	}

	@FXML public void onUpload() {									//for uploading an image
		FileChooser fileChooser = new FileChooser();				//create a file chooser
		fileChooser.setTitle("Open Your Image");					//set the title of file chooser
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));//set the initial directory where file chooser browse from           
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");//set the file format
		fileChooser.getExtensionFilters().add(extFilter);			//add a file format filter
		Stage mainStage = new Stage();								//create a window for file chooser
		File file = fileChooser.showOpenDialog(mainStage);			//show open file window
		if(file != null){											//if file exists
			try{
				BufferedImage myBuffer = ImageIO.read(file);		//read file and convert it to image
				Image myImage = SwingFXUtils.toFXImage(myBuffer, null);
				pixelRead(myImage);									//call the private method to read the image
			}catch(FileNotFoundException e){						//catch exceptions
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private void pixelRead(Image im){
		PixelReader pixelReader = im.getPixelReader();			  	//use pixel reader to read image pixel by pixel
		int width =(int) im.getWidth();			      				//get the width and height of image
        int height =(int) im.getHeight();
        gridpane = new GridPane();
		for(int i = 0; i < width; i++){
			ColumnConstraints column = new ColumnConstraints(20);  	//enlarge 1 pixel to 20 pixel
			gridpane.getColumnConstraints().add(column);
		}
		for(int i = 0; i < height; i++){
			RowConstraints row = new RowConstraints(20);
			gridpane.getRowConstraints().add(row);
		}
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Pane pane = new Pane();
				Color color = pixelReader.getColor(i, j);			//get color of image pixel by pixel
				pane.setBackground((new Background(new BackgroundFill(color, null, null))));//set the color to each pane
				pane.setOnMouseClicked(new EventHandler<MouseEvent>() {//set on mouse clicked event and handle it
					public void handle(MouseEvent event){
						pane.setBackground(new Background(new BackgroundFill(myColorPicker.getValue(), null, null)));
					}
				});
				gridpane.add(pane, i, j);
			}
		}
		gridpane.setGridLinesVisible(true);							//show the grid line
		myScrollPane.setContent(gridpane);							//add grid pane to scroll pane
	}

	@FXML public void onSaveImage() {
		FileChooser fileChooser = new FileChooser();				//new a file chooser and stage for save action
		fileChooser.setTitle("Save Your Image");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		Stage mainStage = new Stage();
		File file = fileChooser.showSaveDialog(mainStage);
		try{
			if(gridpane == null){
				return;												//if there has nothing to save, just return
			}
			int width = (int)(gridpane.getWidth()/20);				//set ratio to 1/20
			int height = (int)(gridpane.getHeight()/20);
			gridpane.setGridLinesVisible(false);					//set grid line to invisible
			
			SnapshotParameters sp = new SnapshotParameters();		//new a snap shot parameter
	        sp.setFill(Color.TRANSPARENT);							//set parameter to transparent to avoid background of snap shot have any color
			Image im = gridpane.snapshot(sp, null);					//get the snapshot of grid pane
			ImageView iv = new ImageView();							//put the image into image view for shrinking image
			iv.setImage(im);
			iv.setFitWidth(width);
			iv.setFitHeight(height);
			iv.setPreserveRatio(true);
	        iv.setSmooth(true);
	        iv.setCache(true);
	        
	        SnapshotParameters sp1 = new SnapshotParameters();
	        sp1.setFill(Color.TRANSPARENT);
			WritableImage newImage = iv.snapshot(sp1, null);		//get the snapshot again from image view
			ImageIO.write(SwingFXUtils.fromFXImage(newImage, null), "png", file);//write it to file
			gridpane.setGridLinesVisible(true);						//set line of grid pane visible inside work place
		}catch(IOException e){										//catch write file exception
			e.printStackTrace();
		}catch(IllegalArgumentException iae){
			gridpane.setGridLinesVisible(true);
			return;													//if user cancel the save process, just return
		}
	}

	@FXML public void onClose() {									//when user click close or close the window, exit the program
		System.exit(0);
	}

	@FXML public void onPreview() {									//when user click the check box of Preview, show preview only there has an image
		if(gridpane != null){
			if(myPreview.isSelected()){
				gridpane.setGridLinesVisible(false);				//when user selected, set the line to invisible
			}else{
				gridpane.setGridLinesVisible(true);					//if not, set the line back to visible
			}
		}
	}

	@FXML public void onAbout() {									//an alert set when user clicked About inside the Help menu choice
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About this editor :)");
		alert.setHeaderText("Sprite Editor");
		alert.setContentText("This is a sprite editor for helping game developer to create/edit image.\nAuthor: Hao Zuo");
		alert.showAndWait();
	}
}
