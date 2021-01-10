package fx;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TableViewSample extends Application {

	public static final String Column1MapKey = "A";
	public static final String Column2MapKey = "B";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(300);
		stage.setHeight(500);

		final Label label = new Label("Student IDs");
		label.setFont(new Font("Arial", 20));

		TableColumn<Map, String> firstDataColumn = new TableColumn<>("Class A");
		TableColumn<Map, String> secondDataColumn = new TableColumn<>("Class B");

		firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
		firstDataColumn.setMinWidth(130);
		secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
		secondDataColumn.setMinWidth(130);

		TableView tableView = new TableView<>(generateDataInMap());

		tableView.setEditable(true);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getColumns().setAll(firstDataColumn, secondDataColumn);
		Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = (
				TableColumn<Map, String> p) -> new TextFieldTableCell(new StringConverter() {
					@Override
					public String toString(Object t) {
						return t.toString();
					}

					@Override
					public Object fromString(String string) {
						return string;
					}
				});
		firstDataColumn.setCellFactory(cellFactoryForMap);
		secondDataColumn.setCellFactory(cellFactoryForMap);

		final VBox vbox = new VBox();

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, tableView);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);

		stage.show();
	}

	private ObservableList<Map> generateDataInMap() {
		int max = 10;
		ObservableList<Map> allData = FXCollections.observableArrayList();
		for (int i = 1; i < max; i++) {
			Map<String, String> dataRow = new HashMap<>();

			String value1 = "A" + i;
			String value2 = "B" + i;

			dataRow.put(Column1MapKey, value1);
			dataRow.put(Column2MapKey, value2);

			allData.add(dataRow);
		}
		return allData;
	}
}