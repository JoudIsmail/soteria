module logic {
    requires mysql.connector.java;
    requires java.sql;
    requires javafx.controls;
    requires controlsfx;

    exports com.soteria.logic.customization;
    exports com.soteria.logic.connectivity;
    exports com.soteria.logic.validation;
    exports com.soteria.logic.decoration;
}