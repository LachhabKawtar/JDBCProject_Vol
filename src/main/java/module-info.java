module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

  //  requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.poi.ooxml;
    //   requires org.apache.poi.poi;
  //  requires org.apache.poi.ooxml;

    //opens com.example.application to javafx.fxml;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo.entities;
    opens com.example.demo.entities to javafx.fxml;

//    exports com.example.application.views;
//    opens com.example.lastapp.views to javafx.fxml;
    exports  com.example.demo.views;
    opens  com.example.demo.views to javafx.fxml;
}