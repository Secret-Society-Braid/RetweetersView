package org.braid.society.secret.retweetersview.lib.twitter;

import lombok.extern.slf4j.Slf4j;
import org.braid.society.secret.retweetersview.lib.util.PropertiesFileController;

import java.util.Properties;

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
    public static final String CONSUMER_KEY;
    public static final String CONSUMER_SECRET;

    static {
        PropertiesFileController consumerKeyPropertiesController = new PropertiesFileController(TWITTER_CONSUMER_PROPERTIES_FILE_NAME);
        consumerKeyPropertiesController.loadPropertiesFromResource();
        TWITTER_CONSUMER_PROPERTIES = consumerKeyPropertiesController.getProperties();
        CONSUMER_KEY = TWITTER_CONSUMER_PROPERTIES.getProperty("consumerId", "");
        CONSUMER_SECRET = TWITTER_CONSUMER_PROPERTIES.getProperty("consumerSecret", "");
    }

    /**
     * util class for twitter authentication.
     */
    private TwitterAuthentication() { /* do nothing */ }

    // test codes

}