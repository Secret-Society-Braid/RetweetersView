package org.braid.society.secret.retweetersview.lib.controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingController {

  @FXML
  private Label settingTitle;

  @FXML
  private AnchorPane settingAnchorPane;

  @FXML
  private Button btnRevealThoughtsSetting;

  @FXML
  private Button btnYourTimeLineSetting;

  @FXML
  private Button btnYourTweetsSetting;

  @FXML
  private Button btnManageFollowFollowerSetting;

  @FXML
  private Button btnManageBlocksMutesSetting;

  @FXML
  private Button btnSettingSettings;

  @FXML
  protected void onSettingSettingButtonClicked(ActionEvent event) {
    log.info("Setting button pressed");
  }

  @FXML
  protected void onRevealThoughtsSettingClicked(ActionEvent event) {
    log.info("Reveal thoughts button pressed");
  }

  @FXML
  protected void onYourTimeLineSettingClicked(ActionEvent event) {
    log.info("Your time line button pressed");
  }

  @FXML
  protected void onYourTweetsSettingClicked(ActionEvent event) {
    log.info("Your tweets button pressed");
  }

  @FXML
  protected void onManageFollowFollowerSettingClicked(ActionEvent event) {
    log.info("Manage follow/follower button pressed");
  }

  @FXML
  protected void onManageBlocksMutesSettingClicked(ActionEvent event) {
    log.info("Manage blocks/mutes button pressed");
  }

}
