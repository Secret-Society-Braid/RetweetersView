package org.braid.society.secret.retweetersview.lib.twitter;

import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitterOperation {

    private TwitterCredentialsOAuth2 credentials;

    private TwitterApi apiInstance;

    public TwitterOperation() {
        this(null);
    }

    public void setTwitterCredentials(TwitterCredentialsOAuth2 credentials) {
        if(this.credentials == null)
            this.credentials = credentials;
        else
            log.debug("Twitter credentials are already set. we will do nothing.");
    }

    public TwitterOperation(TwitterCredentialsOAuth2 credentials) {
        this.credentials = credentials;
    }

    /**
     *
     * @param status
     * @return
     */
    public boolean postTweet(String status) {
        boolean res = false;

        return res;
    }

    // instance vars check

    private void apiValidate() {
        if(credentials == null) {
            log.warn("Credential information are not set. Please set these information.");
            throw new IllegalArgumentException("Credential information are not set. Please set these information.");
        }
        this.apiInstance = new TwitterApi(this.credentials);
    }

    // static checks (move to dedicated class if it needed.)
}
