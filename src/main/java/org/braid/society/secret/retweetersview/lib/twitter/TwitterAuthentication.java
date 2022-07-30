package org.braid.society.secret.retweetersview.lib.twitter;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import lombok.extern.slf4j.Slf4j;
import org.braid.society.secret.retweetersview.lib.util.Base64ControllUtil;
import org.braid.society.secret.retweetersview.lib.util.InternalOperationUtil;
import org.braid.society.secret.retweetersview.lib.util.PropertiesFileController;

import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Implementation for Twitter authentication.
 * <p>
 *     This class is used to authenticate with Twitter.
 * </p>
 *
 * @author Ranfa
 * @since 1.0.0
 */
@Slf4j
public class TwitterAuthentication {

    private static final String TWITTER_CONSUMER_PROPERTIES_FILE_NAME = "twitter_consumer_token.properties";

    public static final String TWITTER_ACCESS_TOKEN_PROPERTIES_FILE_NAME = "twitter_access_token.properties";

    protected static final Properties TWITTER_CONSUMER_PROPERTIES;
    public static final String CONSUMER_ID;
    public static final String CONSUMER_SECRET;

    static {
        PropertiesFileController consumerKeyPropertiesController = new PropertiesFileController(TWITTER_CONSUMER_PROPERTIES_FILE_NAME);
        consumerKeyPropertiesController.loadPropertiesFromResource();
        TWITTER_CONSUMER_PROPERTIES = consumerKeyPropertiesController.getProperties();
        CONSUMER_ID = TWITTER_CONSUMER_PROPERTIES.getProperty("consumerId", "");
        CONSUMER_SECRET = TWITTER_CONSUMER_PROPERTIES.getProperty("consumerSecret", "");
    }

    /**
     * util class for twitter authentication.
     */
    private TwitterAuthentication() { /* do nothing */ }

    // test codes

    public static OAuth2AccessToken getUserAccessToken() {
        String consumerId = CONSUMER_ID;
        String obfuscatedConsumerSecret = CONSUMER_SECRET;
        String consumerSecret = Base64ControllUtil.decodeBase64(obfuscatedConsumerSecret);
        OAuth2AccessToken accessToken = null;
        log.info("Attempt to get grant token from twitter and user.");
        log.debug("using Twitter SDK for java.");
        log.debug("consumerId: {}", consumerId);
        log.debug("obfuscated consumerSecret: {}", obfuscatedConsumerSecret);
        try (TwitterOAuth20Service service = new TwitterOAuth20Service(consumerId,consumerSecret, "https://hizumiaoba.github.io/Personal-pages/cushions/retweetersview/", "tweet.read tweet.write user.read follows.read follows.write offline.access mute.read mute.write like.read block.read block.write")) {
            log.debug("Fetching the Authorization URL...");

            final String secretState = "secret";
            PKCE pkce = new PKCE();
            pkce.setCodeChallenge("challenge");
            pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.PLAIN);
            pkce.setCodeVerifier("challenge");
            String authorizationUrl = service.getAuthorizationUrl(pkce, secretState);
            log.debug("Authorization URL: {}", authorizationUrl);

            int userSelected = JOptionPane.showConfirmDialog(null, "これからTwitter認証画面へ移動します。認証後、画面に表示されるコードを次の画面で入力してください。");
            if(userSelected != JOptionPane.OK_OPTION) {
                log.warn("User rejected to authorize with Twitter.");
                JOptionPane.showMessageDialog(null, "認証が拒否されました。アプリケーションは利用できないため、終了します。");
                InternalOperationUtil.exit(-12);
            }
            URI uri = new URI(authorizationUrl);
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(uri);
            String code = JOptionPane.showInputDialog(null, "表示されたコードを入力してください。");

            accessToken = service.getAccessToken(pkce, code);

            log.debug("Got access token packet");
            log.debug("Access Token: {}", accessToken.getAccessToken());
            log.debug("Refresh Token: {}", accessToken.getRefreshToken());

            storeCredentials(accessToken);

        } catch (IOException e) {
            log.error("Encountered I/O Error while handshaking with twitter api.", e);
        } catch (MalformedURLException | URISyntaxException | ExecutionException another) {
            log.error("Exception while parsing Strings into URI.", another);
            JOptionPane.showInternalMessageDialog(null, "認証ページのURL処理中にエラーが発生しました。しばらく待ってやり直すか、ソフトウェアを再ダウンロードしてみてください。");
        } catch (InterruptedException interrupted) {
            log.error("Working thread has been interrupted by someone. This thread will interrupt again.");
            Thread.currentThread().interrupt();
        }
        return accessToken;
    }

    private static void storeCredentials(OAuth2AccessToken token) {
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        Properties extProp = new Properties();
        extProp.setProperty("consumerId", CONSUMER_ID);
        extProp.setProperty("consumerSecret", CONSUMER_SECRET);
        extProp.setProperty("accessToken", accessToken);
        extProp.setProperty("refreshToken", refreshToken);
        PropertiesFileController extPropController = new PropertiesFileController(extProp);
        extPropController.setFileName(TWITTER_ACCESS_TOKEN_PROPERTIES_FILE_NAME);
        extPropController.write();
    }

}