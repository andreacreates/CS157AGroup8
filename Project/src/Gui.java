import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
		StackPane layout = new StackPane();
		primaryStage.setTitle("Instrument Management");
		
		Scene scene = new Scene(layout, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();

		Button button = new Button("View Products");

		button.setOnAction(e -> {
			getViewProductMenu();
		});

		layout.getChildren().add(button);
		
	}

	private void getViewProductMenu() {

		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, width, height);
		
		
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

		back.setOnAction(e -> {
			getMainMenu();
		});
		
		delete.setOnAction(e -> {
			ProductPojo p = table.getSelectionModel().getSelectedItem();
			
			System.out.println(p.toString());
		});
		
		table.setItems(data);

		layout.setTop(back);
		layout.setCenter(table);
		layout.setRight(delete);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		guiAction = new GuiAction();
		getMainMenu();
		
	}

}