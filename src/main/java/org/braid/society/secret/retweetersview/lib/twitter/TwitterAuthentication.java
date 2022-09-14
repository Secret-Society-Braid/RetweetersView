package org.braid.society.secret.retweetersview.lib.twitter;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import com.google.common.base.Strings;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.braid.society.secret.retweetersview.lib.util.Base64ControllUtil;
import org.braid.society.secret.retweetersview.lib.util.PropertiesFileController;

/**
 * Implementation for Twitter authentication.
 * <p>
 * This class is used to authenticate with Twitter.
 * </p>
 * and if there is a credential data in local, read from local and return it.
 *
 * @author Ranfa
 * @since 1.0.0
 */
@Slf4j
@NoArgsConstructor
public class TwitterAuthentication {

  protected static final Properties TWITTER_CONSUMER_PROPERTIES;
  private static final String TWITTER_ACCESS_TOKEN_PROPERTIES_FILE_NAME = "twitter_access_token";
  private static final String CLIENT_ID;
  private static final String CLIENT_SECRET;
  private static final String TWITTER_CONSUMER_PROPERTIES_FILE_NAME = "twitter_consumer_token.properties";
  private static final String CALLBACK_URI = "https://io.github.com/Personal-pages/cushions/retweetersview/";
  private static final String REQUIRED_SCOPES = "tweet.read tweet.write users.read offline.access"; // add another scope if needed
  private static Random r;

  static {
    PropertiesFileController consumerKeyPropertiesController = new PropertiesFileController(
        TWITTER_CONSUMER_PROPERTIES_FILE_NAME);
    consumerKeyPropertiesController.loadPropertiesFromResource();
    TWITTER_CONSUMER_PROPERTIES = consumerKeyPropertiesController.getProperties();
    CLIENT_ID = TWITTER_CONSUMER_PROPERTIES.getProperty("clientId", "");
    CLIENT_SECRET = Base64ControllUtil.decodeBase64(
        TWITTER_CONSUMER_PROPERTIES.getProperty("clientSecret", ""));
  }

  protected PKCE pkce;

  @Nonnull
  private static String generateRandomString(int length) {
    if (length < 0 || length > 499) {
      throw new IllegalArgumentException("Invalid string length requested : " + length);
    }

    final int leftLimit = 97; // means 'a'
    final int rightLimit = 122; // means 'Z'
    // checks field if null
    if (r == null) {
      r = new Random();
    }
    log.info("Attempt to generate random string for auth state with length: {}", length);

    String generatedString = r.ints(leftLimit, rightLimit + 1)
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
    log.info("Generate complete.");
    log.info("generated string hash: {}", generatedString.hashCode());

    return generatedString;
  }

  private static void store(OAuth2AccessToken token) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(TWITTER_ACCESS_TOKEN_PROPERTIES_FILE_NAME))) {
      log.info("Attempt to record user's access token...");
      oos.writeObject(token);
      log.info("Record complete.");
    }
  }

  /**
   * read credentials from local.
   *
   * @return nullable {@link OAuth2AccessToken} instance representing user's credential
   * @throws IOException when IO error occurred
   */
  @Nullable
  public OAuth2AccessToken readLocal() throws IOException {
    OAuth2AccessToken res = null;
    try (ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream(TWITTER_ACCESS_TOKEN_PROPERTIES_FILE_NAME))) {
      log.info("Attempt to read user's access token");
      res = (OAuth2AccessToken) ois.readObject();
    } catch (ClassNotFoundException e) {
      log.error("Cannot cast correctly to OAuth2AccessToken", e);
    }
    return res;
  }

  /**
   * generate authorization URL for authenticating to twitter.
   *
   * @return nonnull string for Auth URL with twitter
   * @throws IOException when IO error occurred
   */
  @Nonnull
  public String generateAuthorizationUrl() throws IOException {
    log.info("Fetching user's authorization URL...");
    String result;

    try (TwitterOAuth20Service service = new TwitterOAuth20Service(
        CLIENT_ID,
        CLIENT_SECRET,
        CALLBACK_URI,
        REQUIRED_SCOPES
    )) {
      final String secretChallenge = generateRandomString(10);
      final String secretState = generateRandomString(8);
      pkce = new PKCE();
      pkce.setCodeChallenge(secretChallenge);
      pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.PLAIN);
      pkce.setCodeVerifier(secretChallenge);
      result = service.getAuthorizationUrl(pkce, secretState);
    }
    return result;
  }

  /**
   * Fetch user's credential from twitter and exchange it with authorization code
   *
   * @param code authorization code that user just retrieved
   * @return User's credential for twitter
   * @throws IOException when IO error occurred
   */
  @Nullable
  public OAuth2AccessToken getAccessTokenWithCode(String code) throws IOException {
    if (Strings.isNullOrEmpty(code)) {
      throw new IllegalArgumentException("Authorization code must not be null or empty.");
    }

    OAuth2AccessToken token = null;
    try (TwitterOAuth20Service service = new TwitterOAuth20Service(
        CLIENT_ID,
        CLIENT_SECRET,
        CALLBACK_URI,
        REQUIRED_SCOPES
    )) {
      token = service.getAccessToken(pkce, code);
      store(token);
      log.debug("Access token handshaking complete.");
    } catch (ExecutionException e) {
      log.error("Exception while handshaking access token with authorization code.", e);
    } catch (InterruptedException e) {
      log.error("The handling thread has been interrupted by someone.", e);
      log.error("Interrupt this ({}) thread.", Thread.currentThread());
      Thread.currentThread().interrupt();
    }
    return token;
  }

}