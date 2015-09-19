package com.chookie.sense.twitter;

import com.chookie.sense.infrastructure.BroadcastingServerEndpoint;
import com.chookie.sense.infrastructure.DaemonThreadFactory;
import com.chookie.sense.infrastructure.WebSocketServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.logging.Level.WARNING;

/**
 * Reads tweets from a file and sends them to the Twitter Service endpoint.
 */
public class CannedTweetsService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(CannedTweetsService.class.getName());

    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private final BroadcastingServerEndpoint<String> tweetsEndpoint = new BroadcastingServerEndpoint<>();
    private final WebSocketServer server = new WebSocketServer("/tweets/", 8081, tweetsEndpoint);
    private final Path filePath;

    public CannedTweetsService(Path filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) throws URISyntaxException {
        new CannedTweetsService(Paths.get(getSystemResource("./tweetdata60-mins.txt").toURI())).run();
    }

    @Override
    public void run() {
        executor.submit(server);

        // TODO: get a stream of lines in the file
        // TODO: filter out "OK" noise
        // TODO: send each line to be broadcast via websockets

    }

    private void addArtificialDelay() {
        try {
            //reading the file is FAST, add an artificial delay
            MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOGGER.log(WARNING, e.getMessage(), e);
        }
    }

    public void stop() throws Exception {
        server.stop();
        executor.shutdownNow();
    }
}