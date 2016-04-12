package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

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
import jfreechart.HoistryNode;

@SuppressWarnings("serial")
public class DrawHoistry extends JDialog  implements WindowListener{
	private DatePicker startime;
	private DatePicker endtime;
	private String pickFunc;
	private HoistryNode hnode = new HoistryNode();
	private final String pattern = "yyyy-MM-dd";
	private final ObservableList strings = FXCollections.observableArrayList("����", "����", "�쾮", "����", "����", "Ժ��", "����",
			"����", "��ϴ��","�᷿");

	/**
	 * Create the dialog.
	 */
	public DrawHoistry() {
		setBounds(100, 100, 1273, 1041);

		setVisible(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(200);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		JFXPanel fxPanel = new JFXPanel();
		System.out.println("��");
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("��ʼ");
				initFX(fxPanel);
				System.out.println("dfsdf");
			}
		});
		splitPane.setLeftComponent(fxPanel);
		splitPane.setRightComponent(hnode);
	}

	public Scene createScene() {
		VBox vbox = new VBox(20);
		vbox.setStyle("-fx-padding: 10;");
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);
		startime = new DatePicker();
		endtime = new DatePicker();
		// ��ʽ������
		StringConverter converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

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
		Label checkInlabel = new Label("��ʼʱ��:");
		gridPane.add(checkInlabel, 0, 0);
		GridPane.setHalignment(checkInlabel, HPos.LEFT);
		gridPane.add(startime, 0, 1);
		Label checkOutlabel = new Label("����ʱ��:");
		gridPane.add(checkOutlabel, 0, 2);
		GridPane.setHalignment(checkOutlabel, HPos.LEFT);
		gridPane.add(endtime, 0, 3);
		Label funcation = new Label("λ��ѡ��:");
		gridPane.add(funcation, 0, 4);

		// Non-editable combobox. Created with a builder
		ComboBox<String> ComboBox = new ComboBox<String>();
		ComboBox.setId("second-editable");
		ComboBox.setPromptText("Make a chioce");
		ComboBox.setItems(strings);
		ComboBox.setEditable(false);
		gridPane.add(ComboBox, 0, 5);
        //��ѡһ��ť,Ϊ Single��˵����yitiao8���ߡ�All����������
		ToggleGroup to = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Single");
		rb1.setToggleGroup(to);
		RadioButton rb2 = new RadioButton("All");
		rb2.setToggleGroup(to);
		rb2.setSelected(true);
		gridPane.add(rb1, 0, 6);
		gridPane.add(rb2, 0, 7);

		Button bn = new Button("ȷ��");
		bn.setStyle("-fx-base: rgb(30,170,255);");
		bn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				pickFunc = (String) ComboBox.getSelectionModel().getSelectedItem();
				if (pickFunc != null) {
					try {
						String st = startime.getValue().toString();
						String et = endtime.getValue().toString();
						hnode.setQueryValue(st, et, pickFunc);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,"��ѡ����ʼʱ��");
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"��ѡ��ýڵ�����λ��");
				}


			}
		});

		gridPane.add(bn, 0, 8);

		root.getChildren().add(gridPane);
		return scene;
	}

	private void initFX(JFXPanel fxPanel) {
		Scene scene = createScene();
		fxPanel.setScene(scene);
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
        this.setVisible(false);
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
