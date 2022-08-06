package org.braid.society.secret.retweetersview.lib.twitter;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.TweetCreateRequest;
import com.twitter.clientlib.model.TweetCreateResponse;
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
     * post the tweet with specified text.
     * <p>
     *     this method will block the thread until the tweet is posted.
     * </p>
     * @param status the text of the tweet.
     * @return {@code true} if the tweet is posted successfully, {@code false} otherwise.
     */
    public boolean postTweet(String status) {
        boolean res = false;
        apiValidate();
        TweetCreateRequest request = new TweetCreateRequest();
        request.setText(status);
        try {
            TweetCreateResponse response = apiInstance.tweets().createTweet(request).execute();
            log.info("Tweet created successfully. Tweet id: {}", response);
            res = true;
        } catch (ApiException e) {
            log.error("Exception while creating tweet", e);
        }
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
