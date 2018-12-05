import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

		products.setOnAction(e -> {
			getProductMenu();
		});

		sales.setOnAction(e -> {
			getSalesMenu();
		});

		users.setOnAction(e -> {
			getUsersMenu();
		});

		main.getChildren().addAll(products, sales, users);
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
			getProductMenu();
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

				getProductMenu();
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

	private void getProductMenu() {

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
		Button moreDetails = new Button("Mode Details");
		Button stats = new Button("Stats");

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
					data.remove(p);
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
		
		stats.setOnAction(e -> {
			getProductStats();
		});

		VBox right = new VBox(10);

		right.getChildren().addAll(back, add, delete, getDetails, stats);

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
		table.autosize();

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
		Button stats = new Button("View Stats");

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

		getDetails.setOnAction(e -> {
			if (table.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert");
				alert.setHeaderText("No Item Selected");
				alert.setContentText("You have not selected any user");

				alert.showAndWait();

			}

			else {
				int userId = table.getSelectionModel().getSelectedItem().getId();
				getUserDetails(userId);
			}

		});

		stats.setOnAction(e -> {
			getUserStats();
		});

		VBox right = new VBox(10);
		VBox bottom = new VBox(10);

		right.getChildren().addAll(back, delete, getDetails, stats);

		main.setCenter(table);
		main.setRight(right);
		main.setBottom(bottom);
	}

	private void getUserDetails(int id) {
		VBox main = new VBox();
		main.setPadding(new Insets(10));

		ScrollPane sp = new ScrollPane(main);
		Scene scene = new Scene(sp, width, height);

		primaryStage.setTitle("User Detail for User ID: " + id);
		primaryStage.setScene(scene);
		primaryStage.show();

		Button back = new Button("Back");
		Text text = new Text();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Details for User ID: " + id + "\n\n");

		GuiAction g = new GuiAction();

		
		sb.append("Reviews\n\n");
		List<ReviewPojo> reviews = g.getReviewByUser(id);

		if (reviews.isEmpty()) {
			sb.append("No reviews made by this user\n\n");
		}
		
		for (ReviewPojo p : reviews) {
			sb.append(p.toString() + "\n\n");
		}
		
		sb.append("Sales\n\n");
		List<SalePojo> sales = g.getSaleByUser(id);

		if (sales.isEmpty()) {
			sb.append("No sales made by this user\n\n");
		}
		
		for (SalePojo p : sales) {
			sb.append(p.toString() + "\n\n");
		}

		back.setOnAction(e -> {
			getUsersMenu();
		});

		text.setText(sb.toString());
		
		main.getChildren().addAll(back, text);
	}

	private void getUserStats() {
		VBox main = new VBox();
		main.setPadding(new Insets(10));

		ScrollPane sp = new ScrollPane(main);

		Scene scene = new Scene(sp, 550, 550);

		primaryStage.setTitle("User Stats");
		primaryStage.setScene(scene);
		primaryStage.show();

		Button back = new Button("Back");

		back.setOnAction(e -> {
			getUsersMenu();
		});

		ComboBox<String> typeBox = new ComboBox<>();
		typeBox.getItems().addAll("Gender", "Browse Time");

		typeBox.setOnAction(e -> {
			if (typeBox.getValue().equals("Gender")) {
				if (main.getChildren().size() > 2)
					main.getChildren().remove(2);

				int[] stats = guiAction.getUserGenderStat(3);

				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
						new PieChart.Data("Female", stats[1]), new PieChart.Data("Male", stats[0]));

				PieChart chart = new PieChart(pieChartData);
				chart.setTitle("Gender Distribution");
				chart.setData(pieChartData);

				main.getChildren().add(chart);
			}

			else if (typeBox.getValue().equals("Browse Time")) {
				if (main.getChildren().size() > 2)
					main.getChildren().remove(2);

				String male = "Male";
				String female = "Female";

				CategoryAxis xAxis = new CategoryAxis();
				NumberAxis yAxis = new NumberAxis();
				BarChart<String, Integer> chart = new BarChart(xAxis, yAxis);
				chart.setTitle("Browse Time by Gender");
				xAxis.setLabel("Gender");
				yAxis.setLabel("Time in minutes");

				int[] result = guiAction.getUserGenderStat(2);

				XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
				series1.getData().add(new Data<String, Integer>(male, result[0]));
				series1.getData().add(new Data<String, Integer>(female, result[1]));

				chart.getData().addAll(series1);

				main.getChildren().add(chart);
			}
		});

		main.getChildren().addAll(typeBox, back);
	}
	
	private void getProductStats() {
		VBox main = new VBox();
		main.setPadding(new Insets(10));

		ScrollPane sp = new ScrollPane(main);

		Scene scene = new Scene(sp, 550, 550);

		primaryStage.setTitle("Product Stats");
		primaryStage.setScene(scene);
		primaryStage.show();

		Button back = new Button("Back");

		back.setOnAction(e -> {
			getProductMenu();
		});

		ComboBox<String> typeBox = new ComboBox<>();
		typeBox.getItems().addAll("Brand", "Type", "Brand Bought", "Type Bought");

		typeBox.setOnAction(e -> {
			if (typeBox.getValue().equals("Brand")) {
				if (main.getChildren().size() > 2)
					main.getChildren().remove(2);

				HashMap<String, int[]> hm = guiAction.getProductStats("brand");

				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

				
				
				for (String s : hm.keySet()) {
					pieChartData.add(new PieChart.Data(s, hm.get(s)[2]));
				}
				
				PieChart chart = new PieChart(pieChartData);
				chart.setTitle("Products by Brand");
				chart.setData(pieChartData);

				main.getChildren().add(chart);
			}

			else if (typeBox.getValue().equals("Type")) {
				if (main.getChildren().size() > 2)
					main.getChildren().remove(2);

				HashMap<String, int[]> hm = guiAction.getProductStats("type");

				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	
				for (String s : hm.keySet()) {
					pieChartData.add(new PieChart.Data(s, hm.get(s)[2]));
				}
				
				PieChart chart = new PieChart(pieChartData);
				chart.setTitle("Products by Type");
				chart.setData(pieChartData);

				main.getChildren().add(chart);
			}
			
			else if (typeBox.getValue().equals("Brand Bought")) {
				if (main.getChildren().size() > 2)
					main.getChildren().remove(2);

				
				HashMap<String, int[]> hm = guiAction.getProductStats("brand");


				CategoryAxis xAxis = new CategoryAxis();
				NumberAxis yAxis = new NumberAxis();
				BarChart<String, Integer> chart = new BarChart(xAxis, yAxis);
				chart.setTitle("Bough to View ratio by Brand");
				xAxis.setLabel("Brand");
				yAxis.setLabel("Count");
				
				XYChart.Series<String, Integer> views = new XYChart.Series<>();
				views.setName("Views");
				
				XYChart.Series<String, Integer> bought = new XYChart.Series<>();
				bought.setName("Bought"); 
				
				
				for (String s : hm.keySet()) {
					views.getData().add(new Data<String, Integer>(s, hm.get(s)[1]));
					bought.getData().add(new Data<String, Integer>(s, hm.get(s)[0]));
				}

				chart.getData().addAll(views, bought);
				main.getChildren().add(chart);
			}
			
			else if (typeBox.getValue().equals("Type Bought")) {
				if (main.getChildren().size() > 2)
					main.getChildren().remove(2);

				
				HashMap<String, int[]> hm = guiAction.getProductStats("type");


				CategoryAxis xAxis = new CategoryAxis();
				NumberAxis yAxis = new NumberAxis();
				BarChart<String, Integer> chart = new BarChart(xAxis, yAxis);
				chart.setTitle("Bough to View ratio by Type");
				xAxis.setLabel("Brand");
				yAxis.setLabel("Count");
				
				XYChart.Series<String, Integer> views = new XYChart.Series<>();
				views.setName("Views");
				
				XYChart.Series<String, Integer> bought = new XYChart.Series<>();
				bought.setName("Bought"); 
				
				
				for (String s : hm.keySet()) {
					views.getData().add(new Data<String, Integer>(s, hm.get(s)[1]));
					bought.getData().add(new Data<String, Integer>(s, hm.get(s)[0]));
				}

				chart.getData().addAll(views, bought);
				main.getChildren().add(chart);
			}
		});

		main.getChildren().addAll(typeBox, back);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		guiAction = new GuiAction();
		getMainMenu();
	}

}