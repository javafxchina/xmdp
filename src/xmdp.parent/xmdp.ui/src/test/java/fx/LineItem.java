package fx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LineItem {

        private final StringProperty desc = new SimpleStringProperty();
        private final DoubleProperty amount = new SimpleDoubleProperty();
        private final IntegerProperty sort = new SimpleIntegerProperty();

        public StringProperty descProperty() {return desc;}
        public DoubleProperty amountProperty() {return amount;}
        public IntegerProperty sortProperty() {return sort;}

        public LineItem(String dsc, double amt, int srt) {
            desc.set(dsc); amount.set(amt); sort.set(srt);
        }
}