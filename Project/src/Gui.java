import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {

	Stage primaryStage;
	GuiAction guiAction;

	int height = 300;
	int width = 400;

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
			getViewProductMenu();
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
			System.out.println("Unimplemented");
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
					System.out.println("Clicked yes Unimplemented DELETE");
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
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		guiAction = new GuiAction();
		getMainMenu();
	}

}