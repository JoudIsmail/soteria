package com.soteria.logic.decoration;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;

public class MyGraphicValidationDecoration extends GraphicValidationDecoration {
    public static  Image ERROR_IMAGE;

    static {try {
        ERROR_IMAGE = new Image(new File("D:\\soteria\\gui\\src\\com\\soteria\\gui\\style\\icons\\iconfinder_road__sign__alert__danger__2992209.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private static final String SHADOW_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
    @Override
    protected Collection<Decoration> createValidationDecorations(ValidationMessage message) {
        return Arrays.asList(new GraphicDecoration(createDecorationNode(message), Pos.CENTER_RIGHT));
    }
    protected Node createDecorationNode(ValidationMessage message) {
        Node graphic = Severity.ERROR == message.getSeverity() ? createErrorNode() : createWarningNode();
        graphic.setStyle(SHADOW_EFFECT);
        Label label = new Label();
        label.setGraphic(graphic);
        label.setTooltip(createTooltip(message));
        label.setAlignment(Pos.CENTER);
        return label;
    }
    @Override
    protected Node createErrorNode() {
        return new ImageView(ERROR_IMAGE);
    }
}
