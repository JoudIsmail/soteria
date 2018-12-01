module gui {
    requires javafx.fxml;
    requires javafx.controls;
    requires logic;
    requires java.sql;
    requires com.jfoenix;
    requires controlsfx;


    opens com.soteria.gui.mainwindow to javafx.fxml;
    opens com.soteria.gui.controller to javafx.fxml;
    exports com.soteria.gui.mainwindow;
    exports com.soteria.gui.controller;
}