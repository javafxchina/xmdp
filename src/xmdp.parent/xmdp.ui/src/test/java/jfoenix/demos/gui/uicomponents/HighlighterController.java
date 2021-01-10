package jfoenix.demos.gui.uicomponents;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.utils.JFXHighlighter;
import com.jfoenix.utils.JFXNodeUtils;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/ui/Highlighter.fxml", title = "Material Design Example")
public class HighlighterController {

    @FXML
    private JFXTextField searchField;
    @FXML
    private Pane content;

    private JFXHighlighter highlighter = new JFXHighlighter();

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        JFXDepthManager.setDepth(content, 1);
        JFXNodeUtils.addDelayedEventHandler(searchField, Duration.millis(400),
            KeyEvent.KEY_PRESSED, event -> highlighter.highlight(content, searchField.getText()));
    }
}
