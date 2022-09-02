package org.braid.society.secret.retweetersview.twitter;

import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.braid.society.secret.retweetersview.lib.twitter.TwitterAuthentication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class TwitterAuthenticateTest {

    @Test
    void checkCredentials() {
        OAuth2AccessToken credentials = TwitterAuthentication.getUserAccessToken();

        assertNotNull(credentials);
    }

}
