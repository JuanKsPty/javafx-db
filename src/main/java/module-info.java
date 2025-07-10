module com.juank.javafx.parcial3.javafxdbparcial3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.juank.javafx.parcial3.javafxdbparcial3 to javafx.fxml;
    opens com.juank.javafx.parcial3.javafxdbparcial3.controller to javafx.fxml;
    opens com.juank.javafx.parcial3.javafxdbparcial3.model to javafx.base;

    exports com.juank.javafx.parcial3.javafxdbparcial3;
    exports com.juank.javafx.parcial3.javafxdbparcial3.controller;
    exports com.juank.javafx.parcial3.javafxdbparcial3.model;
}