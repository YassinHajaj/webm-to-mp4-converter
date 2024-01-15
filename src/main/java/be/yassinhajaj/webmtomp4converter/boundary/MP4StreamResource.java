package be.yassinhajaj.webmtomp4converter.boundary;

import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MP4StreamResource extends StreamResource {
    public MP4StreamResource(String filePath) {
        super("converted.mp4", new MP4InputStreamFactory(filePath));
    }

    private static class MP4InputStreamFactory implements InputStreamFactory {

        private static final Logger LOGGER = Logger.getLogger(MP4InputStreamFactory.class.getName());
        private final String filePath;

        private MP4InputStreamFactory(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public InputStream createInputStream() {
            try {
                return Files.newInputStream(Path.of(filePath));
            } catch (IOException e) {
                LOGGER.severe("Error while creating input stream for file: " + filePath);
                LOGGER.severe(e.getMessage());

                if (LOGGER.isLoggable(Level.FINE)) {
                    //noinspection CallToPrintStackTrace
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}
