package org.braid.society.secret.retweetersview.lib.controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainController {

  @FXML
  private Label mainTitle;

  @FXML
  private AnchorPane mainAnchorPane;

  @FXML
  private Button btnRevealThoughtsMain;

  @FXML
  private Button btnYourTimeLineMain;

  @FXML
  private Button btnYourTweetsMain;

  @FXML
  private Button btnManageFollowFollowerMain;

  @FXML
  private Button btnManageBlocksMutesMain;

  @FXML
  private Button btnMainSettings;

  @FXML
  protected void onMainSettingButtonClicked(ActionEvent event) {
    log.info("Setting button pressed");
  }

  @FXML
  protected void onRevealThoughtsMainClicked(ActionEvent event) {
    log.info("Reveal thoughts button pressed");
  }

  @FXML
  protected void onYourTimeLineMainClicked(ActionEvent event) {
    log.info("Your time line button pressed");
  }

  @FXML
  protected void onYourTweetsMainClicked(ActionEvent event) {
    log.info("Your tweets button pressed");
  }

  @FXML
  protected void onManageFollowFollowerMainClicked(ActionEvent event) {
    log.info("Manage follow/follower button pressed");
  }

  @FXML
  protected void onManageBlocksMutesMainClicked(ActionEvent event) {
    log.info("Manage blocks/mutes button pressed");
  }
}