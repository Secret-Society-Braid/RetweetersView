module org.braid.society.secret.retweetersview {
    requires javafx.controls;
    requires javafx.fxml;

    // main program entries
    requires java.xml;
    requires java.desktop;
    requires lombok;
    requires org.slf4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;
    requires jsr305;

    // test program entries
    requires org.junit.platform.engine;
    requires org.junit.jupiter.engine;
    requires scribejava.core;
    requires twitter.api.java.sdk;


    opens org.braid.society.secret.retweetersview to javafx.fxml;
    exports org.braid.society.secret.retweetersview;
}