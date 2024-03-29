module com.example.po {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.logging;
    requires org.apache.commons.io;

    opens com.example.po to javafx.fxml;
    exports com.example.po;
    exports com.example.po.backends;
    opens com.example.po.backends to javafx.fxml;
    exports com.example.po.NPChandling;
    opens com.example.po.NPChandling to javafx.fxml;
}