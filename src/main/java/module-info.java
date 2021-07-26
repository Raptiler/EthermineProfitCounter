module pl.pzprojects {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires spring.web;
    requires spring.boot.devtools;

    opens pl.pzprojects to javafx.fxml;
    exports pl.pzprojects;
    exports pl.pzprojects.controller;
    exports pl.pzprojects.dto;
    exports pl.pzprojects.rest;
    exports pl.pzprojects.table;
    opens pl.pzprojects.controller to javafx.fxml;
}