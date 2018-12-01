import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
		primaryStage.setTitle("Instrument Management");
		
		Scene scene = new Scene(main, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();

		Button button = new Button("View Products");

		button.setOnAction(e -> {
			getViewProductMenu();
		});

		main.getChildren().add(button);
		
	}

	private void getViewProductMenu() {

		BorderPane main = new BorderPane();
		
		main.setPadding(new Insets(10));
		
		Scene scene = new Scene(main, width, height);
		
		Text details = new Text("Select a Product");
		
		primaryStage.setTitle("Products");
		primaryStage.setScene(scene);
		primaryStage.show();

		
		
		ObservableList<ProductPojo> data = FXCollections.observableArrayList(); 

		TableView<ProductPojo> table = new TableView<>();
		table.setEditable(false);
		
		TableColumn model = new TableColumn("Model");	
		model.setCellValueFactory(new PropertyValueFactory<>("model"));
		TableColumn brand = new TableColumn("Brand");
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		TableColumn type = new TableColumn("Type");
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn view = new TableColumn("Views");
		view.setCellValueFactory(new PropertyValueFactory<>("views"));
		TableColumn bought = new TableColumn("bought");
		bought.setCellValueFactory(new PropertyValueFactory<>("bought"));
		
		table.getColumns().addAll(model, brand, type, view, bought);


		for (ProductPojo p : guiAction.getProducts()) {
			data.add(p);
		}

		
		Button back = new Button("Back");
		Button delete = new Button("Delete");
		Button getDetails = new Button("Details");

		back.setOnAction(e -> {
			getMainMenu();
		});
		
		delete.setOnAction(e -> {
			ProductPojo p = table.getSelectionModel().getSelectedItem();
			
			if (p == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("No Item Selected");
				alert.setHeaderText("No Item Selected");
				alert.setContentText("You have not selected any item");

				alert.showAndWait();
			}
			
			else {
				System.out.println(p.toString());
			}
			
		});
		
		getDetails.setOnAction(e -> {
			ProductPojo p = table.getSelectionModel().getSelectedItem();
			details.setText(guiAction.getProductDetails(p));
		});
		
		table.setItems(data);

		
		VBox right = new VBox(10);
		
		right.getChildren().addAll(back, delete, getDetails);

		main.setCenter(table);
		main.setRight(right);
		main.setBottom(details);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		guiAction = new GuiAction();
		getMainMenu();
		
	}

}