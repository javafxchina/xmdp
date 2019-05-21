package fx;

import java.text.NumberFormat;
import java.util.Stack;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;


public class EasyEditTable{
    private String lastKey = null;

    public TableView makeTable(ObservableList<LineItem> items) {
        TableView tv = new TableView(items);
        tv.setEditable(true);

        Stack<LineItem> deletedLines = new Stack<>();
        tv.setUserData(deletedLines);
        Callback<TableColumn<LineItem,String>, TableCell<LineItem,String>> txtCellFactory = 
                (TableColumn<LineItem,String> p) -> {return new EditingCell();};

        TableColumn<LineItem,String> descCol = new TableColumn<>("desc");
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        descCol.setCellFactory(txtCellFactory);
        descCol.setOnEditCommit((TableColumn.CellEditEvent<LineItem, String> evt) -> {
            evt.getTableView().getItems().get(evt.getTablePosition().getRow())
                    .descProperty().setValue(evt.getNewValue());
        });


        final NumberFormat currFmt = NumberFormat.getCurrencyInstance();
        TableColumn<LineItem, String> amountCol = new TableColumn<>("amount");
        amountCol.setCellValueFactory((TableColumn.CellDataFeatures<LineItem, String> p) -> {
                return new SimpleStringProperty(currFmt.format(p.getValue().amountProperty().get()));
        });
        amountCol.setCellFactory(txtCellFactory);
        amountCol.setOnEditCommit((TableColumn.CellEditEvent<LineItem, String> evt) -> {
            try {
              evt.getTableView().getItems().get(evt.getTablePosition().getRow())
                        .amountProperty().setValue(Double.parseDouble(evt.getNewValue().replace("$","")));
            } catch (NumberFormatException nfe) {
                //handle error properly somehow
            }
        });
        amountCol.setComparator((String o1, String o2) -> {
            try {//only works in $ countries, use currFmt.parse() instead
                return Double.compare(Double.parseDouble(o1.replace("$", "")),
                                      Double.parseDouble(o2.replace("$", "")));
            } catch (NumberFormatException numberFormatException) {
                return 0;
            }
        });

        TableColumn<LineItem,String> sortCol = new TableColumn<>("sort");
        sortCol.setCellValueFactory(new PropertyValueFactory("sort"));
        sortCol.setCellFactory(txtCellFactory);
        sortCol.setOnEditCommit((TableColumn.CellEditEvent<LineItem, String> evt) -> {
            evt.getTableView().getItems().get(evt.getTablePosition().getRow())
                    .sortProperty().setValue(Integer.parseInt(evt.getNewValue()));//throws nfe
        });

        tv.getColumns().setAll(descCol, amountCol, sortCol);
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.getSelectionModel().setCellSelectionEnabled(true);
        tv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tv.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            if (tv.getEditingCell() == null && t.getCode() == KeyCode.ENTER) {
                if (t.isShiftDown()) {
                    tv.getSelectionModel().selectAboveCell();
                } else {
                    tv.getSelectionModel().selectBelowCell();
                }
                t.consume();
            }
            //I decided not to override the default tab behavior
            //using ctrl tab for cell traversal, but arrow keys are better
            if (t.isControlDown() && t.getCode() == KeyCode.TAB) {
                if (t.isShiftDown()) {
                    tv.getSelectionModel().selectLeftCell();
                } else {
                    tv.getSelectionModel().selectRightCell();
                }
                t.consume();
            }
        });

        tv.setOnKeyPressed((KeyEvent t) -> {
            TablePosition tp;
            if (!t.isControlDown() && 
               (t.getCode().isLetterKey() || t.getCode().isDigitKey())) {
                lastKey = t.getText();
                tp = tv.getFocusModel().getFocusedCell();
                tv.edit(tp.getRow(),tp.getTableColumn());
                lastKey = null;
            }
        });

        tv.setOnKeyReleased((KeyEvent t) -> {
            TablePosition tp;
            switch (t.getCode()) {
                case INSERT:
                    items.add(new LineItem("",0d,0));//maybe try adding at position
                    break;
                case DELETE:
                    tp = tv.getFocusModel().getFocusedCell();
                    if (tp.getTableColumn() == descCol) {
                        deletedLines.push(items.remove(tp.getRow()));
                    } else { //maybe delete cell value
                    }
                    break;
                case Z:
                    if (t.isControlDown()) {
                        if (!deletedLines.isEmpty()) {
                            items.add(deletedLines.pop());
                        }
                    }
            }
        });

        return tv;
    }

    private class EditingCell extends TableCell{

        private TextField textField;

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                //setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
                Platform.runLater(() -> {//without this space erases text, f2 doesn't
                    textField.requestFocus();//also selects
                });
                if (lastKey != null) {
                    textField.setText(lastKey);
                    Platform.runLater(() -> {
                        textField.deselect();
                        textField.end();
                    });
                }
            }
        }

        public void commit(){
            commitEdit(textField.getText());
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            try {
                setText(getItem().toString());
            } catch (Exception e) {}
            setGraphic(null);
        }

        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
                if (getTableColumn().getText().equals("amount"))
                setAlignment(Pos.CENTER_RIGHT);
            }
        } 

        private void createTextField() {
            textField = new TextField(getString());

            //doesn't work if clicking a different cell, only focusing out of table
            textField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                if (!arg2) commitEdit(textField.getText());
            });

            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                    EditingCell.this.getTableView().getSelectionModel().selectBelowCell();
                }
                if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });

            textField.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent t) -> {
                if (t.getCode() == KeyCode.DELETE) {
                    t.consume();//stop from deleting line in table keyevent
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}