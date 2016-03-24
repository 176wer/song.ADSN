package test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;

public class XJdialog extends JDialog {
	private DatePicker startime;
	private DatePicker endtime;
	private final String pattern = "yyyy-MM-dd";
    private final ObservableList strings = FXCollections.observableArrayList(
            "卧室", "储藏", "天井",
            "厨房", "客厅", "院落",
            "厕所", "饲养", "盥洗室");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			XJdialog dialog = new XJdialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public XJdialog() {
		setBounds(100, 100, 873, 641);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(200);
		getContentPane().add(splitPane, BorderLayout.CENTER);
        JFXPanel fxPanel = new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
        splitPane.setLeftComponent(fxPanel);
	}


	public Scene createScene(){
		VBox vbox = new VBox(20);
		vbox.setStyle("-fx-padding: 10;");
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);
		startime = new DatePicker();
		endtime = new DatePicker();
		//格式化日期
		StringConverter converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter =
					DateTimeFormatter.ofPattern(pattern);
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		startime.setConverter(converter);
		startime.setPromptText(pattern.toLowerCase());
		endtime.setConverter(converter);
		endtime.setPromptText(pattern.toLowerCase());
		endtime.setValue(LocalDate.now());

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		Label checkInlabel = new Label("开始时间:");
		gridPane.add(checkInlabel, 0, 0);
		GridPane.setHalignment(checkInlabel, HPos.LEFT);
		gridPane.add(startime, 0, 1);
        Label checkOutlabel = new Label("结束时间:");
        gridPane.add(checkOutlabel, 0, 2);
        GridPane.setHalignment(checkOutlabel, HPos.LEFT);
        gridPane.add(endtime, 0, 3);
        Label funcation = new Label("位置选择:");
        gridPane.add(funcation, 0, 4);

        //Non-editable combobox. Created with a builder
        ComboBox<String> ComboBox = new ComboBox<String>();
        ComboBox.setId("second-editable");
        ComboBox.setPromptText("Make a chioce");
        ComboBox.setItems(strings);
        ComboBox.setEditable(false);
        gridPane.add(ComboBox, 0, 5);


        Button bn = new Button("确定");
        bn.setStyle("-fx-base: rgb(30,170,255);");
        bn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String item = (String) ComboBox.getSelectionModel().getSelectedItem();
                try{
                    String st = startime.getValue().toString();
                    String et = endtime.getValue().toString();
                }catch (Exception e){

                }

            }
        });

        gridPane.add(bn, 0, 6);



        root.getChildren().add(gridPane);
        return scene;
    }

    private void initFX(JFXPanel fxPanel){
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }
}
