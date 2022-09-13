package org.braid.society.secret.retweetersview.twitter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.fail;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import org.braid.society.secret.retweetersview.lib.twitter.TwitterAuthentication;
import org.braid.society.secret.retweetersview.lib.util.Base64ControllUtil;
import org.braid.society.secret.retweetersview.lib.util.PropertiesFileController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TwitterAuthenticateTest {

  private static TwitterAuthentication instance;

  @BeforeAll
  static void setUp() {
    instance = new TwitterAuthentication();
  }

  @Disabled("disable because of not working remote environment")
  @Test
  void authorizationTest() {
    try {
      // prepare for assertion
      String actual = instance.generateAuthorizationUrl();
      // assertions
      assertThat(actual, not(isEmptyOrNullString()));
    } catch (IOException e) {
      fail(e);
    }
  }

  @Disabled("prototype method testing purpose")
  @Test
  void getAccessTokenMethodTest() {
    PropertiesFileController controller = new PropertiesFileController(
        "twitter_consumer_token.properties");
    controller.loadPropertiesFromResource();
    Properties tokenProperties = controller.getProperties();
    final String clientId = tokenProperties.getProperty("clientId");
    final String clientSecret = Base64ControllUtil.decodeBase64(
        tokenProperties.getProperty("clientSecret"));

    OAuth2AccessToken accessToken;

    try(TwitterOAuth20Service service = new TwitterOAuth20Service(
        clientId,
        clientSecret,
        "https://hizumiaoba.github.io/Personal-pages/cushions/retweetersview/",
        "tweet.read tweet.write users.read offline.access"
    )) {
      // log.info("Fetching the Authorization URL...");

      final String secretState = "valuestate";
      PKCE pkce = new PKCE();
      pkce.setCodeChallenge("challenge");
      pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.PLAIN);
      pkce.setCodeVerifier("challenge");
      String authorizationUrl = service.getAuthorizationUrl(pkce, secretState);

      final String code = JOptionPane.showInputDialog(
          "Please access this url to grant access to the app.", authorizationUrl);
      accessToken = service.getAccessToken(pkce, code);

      assertThat(accessToken.getAccessToken(), is(not(isEmptyOrNullString())));
      assertThat(accessToken.getRefreshToken(), is(not(isEmptyOrNullString())));

    } catch (IOException | ExecutionException | InterruptedException e) {
      fail(e); // replace this for production
    }
  }

}
