module com.keviron.cmms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.keviron.cmms to javafx.fxml;
    opens com.keviron.cmms.TableModels to javafx.base;
    exports com.keviron.cmms;
    exports com.keviron.cmms.Controllers;
    opens com.keviron.cmms.Controllers to javafx.fxml;
}