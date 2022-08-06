package org.braid.society.secret.retweetersview;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
class GUITest {

    private Button button;

    @Start
    private void start(Stage stage) {
        button = new Button("Click me!");
        button.setId("myButton");
        button.setOnAction(event -> button.setText("omg! I am clicked!"));
        stage.setScene(new Scene(new StackPane(button), 100, 100));
        stage.show();
    }

    @Test
    void shouldContainButtonWithText(FxRobot robot) {
        // using button instance
        verifyThat(button, hasText("Click me!"));
        // using looking up by css id
        verifyThat("#myButton", hasText("Click me!"));
        // using looking up by css class
        verifyThat(".button", hasText("Click me"));
    }

    @Test
    void textShouldChangeWhenButtonIsClicked(FxRobot robot) {
        // when:
        robot.clickOn(".button");

        // then:
        // using button instance
        verifyThat(button, hasText("omg! I am clicked!"));
        // using looking up by css id
        verifyThat("#myButton", hasText("omg! I am clicked!"));
        // using looking up by css class
        verifyThat(".button", hasText("omg! I am clicked!"));
    }

}
