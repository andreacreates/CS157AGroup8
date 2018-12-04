import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {

	Stage primaryStage;
	GuiAction guiAction;

	int height = 300;
	int width = 500;

	public static void main(String[] args) {
		launch(args);
	}

	private void getMainMenu() {
		VBox main = new VBox();
		main.setAlignment(Pos.CENTER);
		main.setPadding(new Insets(10));
		main.setSpacing(10);
		primaryStage.setTitle("Instrument Management");

		Scene scene = new Scene(main, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();

		Button products = new Button("View Products");
		Button sales = new Button("View Sales");
		Button users = new Button("View Users");
		Button userChart = new Button("User Chart by Gender");
		Button guitarChart = new Button("Guitar Graph");

		products.setOnAction(e -> {
			getViewProductMenu();
		});

		sales.setOnAction(e -> {
			getSalesMenu();
		});

		users.setOnAction(e -> {
			getUsersMenu();
		});

		userChart.setOnAction(e -> {
			getUserChart();
		});

		guitarChart.setOnAction(e -> {
			getGuitarChart();
		});

		main.getChildren().addAll(products, sales, users, userChart, guitarChart);

	}

	private void getSalesMenu() {
		BorderPane main = new BorderPane();
		main.setPadding(new Insets(10));

		Scene scene = new Scene(main, width, height);

		primaryStage.setTitle("Sales");
		primaryStage.setScene(scene);
		primaryStage.show();

		ObservableList<SalePojo> data = FXCollections.observableArrayList();

		TableView<SalePojo> table = new TableView<>();
		table.setEditable(false);

		TableColumn<SalePojo, String> id = new TableColumn<>("Sale ID");
		TableColumn<SalePojo, String> user = new TableColumn<>("User ID");
		TableColumn<SalePojo, String> item = new TableColumn<>("Item");
		TableColumn<SalePojo, String> amount = new TableColumn<>("Amount");

		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		user.setCellValueFactory(new PropertyValueFactory<>("user"));
		item.setCellValueFactory(new PropertyValueFactory<>("item"));
		amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

		table.getColumns().addAll(id, user, item, amount);
		table.setItems(data);

		for (SalePojo p : guiAction.getSales()) {
			data.add(p);
		}

		Button back = new Button("Back");

		back.setOnAction(e -> {
			getMainMenu();
		});

		VBox right = new VBox(10);

		right.getChildren().addAll(back);

		main.setRight(right);
		main.setCenter(table);
	}

	private void getAddMenu() {
		BorderPane main = new BorderPane();
		main.setPadding(new Insets(10));

		Scene scene = new Scene(main, width, height);

		primaryStage.setTitle("Add New Product");
		primaryStage.setScene(scene);
		primaryStage.show();

		VBox right = new VBox(10);
		TilePane center = new TilePane(10, 10);
		center.setPrefColumns(2);

		ComboBox<String> typeBox = new ComboBox<>();
		typeBox.getItems().addAll("Guitar", "Bass", "Percussion");

		Button cancel = new Button("Cancel");
		Button submit = new Button("Submit");

		Text type = new Text("Type");
		Text brand = new Text("Brand");
		Text model = new Text("Model");
		Text price = new Text("Price");
		Text kind = new Text("Kind");
		Text special = new Text();

		TextField brandText = new TextField();
		TextField modelText = new TextField();
		TextField priceText = new TextField();
		TextField kindText = new TextField();
		TextField specialText = new TextField();

		cancel.setOnAction(e -> {
			getViewProductMenu();
		});

		submit.setOnAction(e -> {

			boolean success = false;

			try {

				if (typeBox.getValue().equals("Guitar")) {
					success = guiAction.addGuitar(brandText.getText(), Integer.parseInt(modelText.getText()),
							Double.parseDouble(priceText.getText()), kindText.getText(), specialText.getText());
				}

				else if (typeBox.getValue().equals("Bass")) {
					success = guiAction.addBass(brandText.getText(), Integer.parseInt(modelText.getText()),
							Double.parseDouble(priceText.getText()), kindText.getText());
				}

				else if (typeBox.getValue().equals("Percussion")) {
					success = guiAction.addPercussion(brandText.getText(), Integer.parseInt(modelText.getText()),
							Double.parseDouble(priceText.getText()), kindText.getText(),
							Integer.parseInt(specialText.getText()));
					System.out.println("Added success!");
				}
			} catch (Exception ex) {
				System.out.println("Format error");
			}

			if (success) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success!");
				alert.setHeaderText("Success!");
				alert.setContentText("Item has been added to the Database!");

				alert.showAndWait();

				getViewProductMenu();
			}

			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert");
				alert.setHeaderText("Error adding product");
				alert.setContentText("There was an error adding to the Database.");

				alert.showAndWait();
			}
		});

		typeBox.setOnAction(e -> {

			if (typeBox.getValue().equals("Guitar")) {
				center.getChildren().removeAll(special, specialText, brand, brandText, model, modelText, price,
						priceText, kind, kindText);
				brandText.setDisable(false);
				modelText.setDisable(false);
				priceText.setDisable(false);
				kindText.setDisable(false);
				specialText.setDisable(false);

				special.setText("Size");
				center.getChildren().addAll(brand, brandText, model, modelText, price, priceText, kind, kindText,
						special, specialText);
			}

			else if (typeBox.getValue().equals("Bass")) {

				center.getChildren().removeAll(special, specialText, brand, brandText, model, modelText, price,
						priceText, kind, kindText);

				brandText.setDisable(false);
				modelText.setDisable(false);
				priceText.setDisable(false);
				kindText.setDisable(false);
				specialText.setDisable(false);

				center.getChildren().addAll(brand, brandText, model, modelText, price, priceText, kind, kindText);
			}

			else if (typeBox.getValue().equals("Percussion")) {
				center.getChildren().removeAll(special, specialText, brand, brandText, model, modelText, price,
						priceText, kind, kindText);

				brandText.setDisable(false);
				modelText.setDisable(false);
				priceText.setDisable(false);
				kindText.setDisable(false);
				specialText.setDisable(false);

				special.setText("Pieces");
				center.getChildren().addAll(brand, brandText, model, modelText, price, priceText, kind, kindText,
						special, specialText);
			}

		});

		right.getChildren().addAll(cancel, submit);
		center.getChildren().addAll(type, typeBox);

		main.setRight(right);
		main.setCenter(center);

	}

	private void getViewProductMenu() {

		BorderPane main = new BorderPane();
		main.setPadding(new Insets(10));

		Scene scene = new Scene(main, width, height);

		primaryStage.setTitle("Products");
		primaryStage.setScene(scene);
		primaryStage.show();

		ObservableList<ProductPojo> data = FXCollections.observableArrayList();

		TableView<ProductPojo> table = new TableView<>();
		table.setEditable(false);

		TableColumn<ProductPojo, String> model = new TableColumn<>("Model");
		TableColumn<ProductPojo, String> brand = new TableColumn<>("Brand");
		TableColumn<ProductPojo, String> type = new TableColumn<>("Type");
		TableColumn<ProductPojo, String> view = new TableColumn<>("Views");
		TableColumn<ProductPojo, String> bought = new TableColumn<>("Bought");

		model.setCellValueFactory(new PropertyValueFactory<>("model"));
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		view.setCellValueFactory(new PropertyValueFactory<>("views"));
		bought.setCellValueFactory(new PropertyValueFactory<>("bought"));

		table.getColumns().addAll(model, brand, type, view, bought);
		table.setItems(data);

		for (ProductPojo p : guiAction.getProducts()) {
			data.add(p);
		}

		Button back = new Button("Back");
		Button delete = new Button("Delete");
		Button getDetails = new Button("Details");
		Button add = new Button("New Item");

		Text details = new Text("Select a Product");

		back.setOnAction(e -> {
			getMainMenu();
		});

		add.setOnAction(e -> {
			getAddMenu();
		});

		delete.setOnAction(e -> {
			ProductPojo p = table.getSelectionModel().getSelectedItem();

			if (p == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert");
				alert.setHeaderText("No Item Selected");
				alert.setContentText("You have not selected any item");

				alert.showAndWait();

			}

			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Delete Item");
				alert.setContentText("Are you sure you want to delete item: " + p.getModel() + "?");
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					guiAction.deleteProduct(p.getModel());
				}

				else {
					System.out.println("Canceled Delete");
				}
			}

		});

		getDetails.setOnAction(e -> {
			ProductPojo p = table.getSelectionModel().getSelectedItem();

			if (p == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert");
				alert.setHeaderText("No Item Selected");
				alert.setContentText("You have not selected any item");
				alert.showAndWait();
			}

			else
				details.setText(guiAction.getProductDetails(p));
		});

		VBox right = new VBox(10);

		right.getChildren().addAll(back, add, delete, getDetails);

		main.setCenter(table);
		main.setRight(right);
		main.setBottom(details);

	}

	private void getUsersMenu() {
		BorderPane main = new BorderPane();
		main.setPadding(new Insets(10));

		Scene scene = new Scene(main, width, height);

		primaryStage.setTitle("Users");
		primaryStage.setScene(scene);
		primaryStage.show();

		ObservableList<UserPojo> data = FXCollections.observableArrayList();

		TableView<UserPojo> table = new TableView<>();
		table.setEditable(false);

		TableColumn<UserPojo, String> id = new TableColumn<>("User ID");
		TableColumn<UserPojo, String> gender = new TableColumn<>("Gender");
		TableColumn<UserPojo, String> age = new TableColumn<>("Age");
		TableColumn<UserPojo, String> browseTime = new TableColumn<>("Browse Time");

		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		age.setCellValueFactory(new PropertyValueFactory<>("age"));
		browseTime.setCellValueFactory(new PropertyValueFactory<>("browseTime"));

		table.getColumns().addAll(id, gender, age, browseTime);
		table.setItems(data);

		for (UserPojo p : guiAction.getUsers()) {
			data.add(p);
		}

		Button back = new Button("Back");
		Button delete = new Button("Delete");
		Button getDetails = new Button("Details");

		Text details = new Text("Select a User");

		back.setOnAction(e -> {
			getMainMenu();
		});

		delete.setOnAction(e -> {
			UserPojo p = table.getSelectionModel().getSelectedItem();

			if (p == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert");
				alert.setHeaderText("No Item Selected");
				alert.setContentText("You have not selected any user");

				alert.showAndWait();

			}

			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Delete User");
				alert.setContentText("Are you sure you want to delete user: " + p.getId() + "?");
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					System.out.println("Clicked yes Unimplemented DELETE");
				}

				else {
					System.out.println("Canceled Delete");
				}
			}

		});

		/*
		 * getDetails.setOnAction(e -> { UserPojo p =
		 * table.getSelectionModel().getSelectedItem();
		 * 
		 * if (p == null) { Alert alert = new Alert(AlertType.ERROR);
		 * alert.setTitle("Alert"); alert.setHeaderText("No Item Selected");
		 * alert.setContentText("You have not selected any item"); alert.showAndWait();
		 * }
		 * 
		 * else details.setText(guiAction.getUserDetails(p)); });
		 */

		VBox right = new VBox(10);

		right.getChildren().addAll(back, delete, getDetails);

		main.setCenter(table);
		main.setRight(right);
		main.setBottom(details);
	}

	private void getUserChart() {

		BorderPane main = new BorderPane();
		main.setPadding(new Insets(10));

		Scene scene = new Scene(main, width, height);

		primaryStage.setTitle("User Gender Chart");
		primaryStage.setScene(scene);
		primaryStage.show();

		VBox right = new VBox(10);
		TilePane center = new TilePane(10, 10);
		center.setPrefColumns(2);
		
		int[] stats = guiAction.getUserGenderStat();

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Female", stats[1]),
				new PieChart.Data("Male", stats[0]));

		PieChart chart = new PieChart(pieChartData);
		chart.setTitle("User Gender Distribution");

		chart.setData(pieChartData);

		main.setCenter(chart);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void getGuitarChart() {
		/*
		 * final static String austria = "Austria"; final static String brazil =
		 * "Brazil"; final static String france = "France"; final static String italy =
		 * "Italy"; final static String usa = "USA";
		 * 
		 * @Override public void start(Stage stage) {
		 * stage.setTitle("Bar Chart Sample"); final CategoryAxis xAxis = new
		 * CategoryAxis(); final NumberAxis yAxis = new NumberAxis(); final
		 * BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
		 * bc.setTitle("Country Summary"); xAxis.setLabel("Country");
		 * yAxis.setLabel("Value");
		 * 
		 * XYChart.Series series1 = new XYChart.Series(); series1.setName("2003");
		 * series1.getData().add(new XYChart.Data(austria, 25601.34));
		 * series1.getData().add(new XYChart.Data(brazil, 20148.82));
		 * series1.getData().add(new XYChart.Data(france, 10000));
		 * series1.getData().add(new XYChart.Data(italy, 35407.15));
		 * series1.getData().add(new XYChart.Data(usa, 12000));
		 * 
		 * XYChart.Series series2 = new XYChart.Series(); series2.setName("2004");
		 * series2.getData().add(new XYChart.Data(austria, 57401.85));
		 * series2.getData().add(new XYChart.Data(brazil, 41941.19));
		 * series2.getData().add(new XYChart.Data(france, 45263.37));
		 * series2.getData().add(new XYChart.Data(italy, 117320.16));
		 * series2.getData().add(new XYChart.Data(usa, 14845.27));
		 * 
		 * XYChart.Series series3 = new XYChart.Series(); series3.setName("2005");
		 * series3.getData().add(new XYChart.Data(austria, 45000.65));
		 * series3.getData().add(new XYChart.Data(brazil, 44835.76));
		 * series3.getData().add(new XYChart.Data(france, 18722.18));
		 * series3.getData().add(new XYChart.Data(italy, 17557.31));
		 * series3.getData().add(new XYChart.Data(usa, 92633.68));
		 * 
		 * Scene scene = new Scene(bc,800,600); bc.getData().addAll(series1, series2,
		 * series3); stage.setScene(scene); stage.show(); }
		 */
	}

	// }

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		guiAction = new GuiAction();
		getMainMenu();
	}

}