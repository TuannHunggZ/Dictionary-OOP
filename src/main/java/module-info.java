module MyDictionary {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires javafx.media;
    requires ormlite.jdbc;
    requires org.json;

    exports Dictionary;
    exports Dictionary.Controllers;
    exports Dictionary.Models;
    exports Dictionary.API;

    opens Dictionary.Controllers to javafx.fxml;
    opens Dictionary to javafx.fxml;
    opens Dictionary.Models;
}