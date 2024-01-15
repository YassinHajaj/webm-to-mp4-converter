package be.yassinhajaj.webmtomp4converter.boundary;

import be.yassinhajaj.webmtomp4converter.control.ConvertedFileManager;
import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@CdiComponent
@CssImport("upload-webm.css")
public class UploadWebm extends Upload {

    private static final Logger LOGGER = Logger.getLogger(UploadWebm.class.getName());

    public UploadWebm(
            @ConfigProperty(name = "webm.upload.max.size") int maxFileSize,
            @ConfigProperty(name = "webm.upload.file.location") String fileLocation,
            ConvertedFileManager convertedFileManager
    ) {
        setMaxFiles(1);
        setMaxFileSize(maxFileSize);
        setAutoUpload(true);
        setDropAllowed(true);
        setAcceptedFileTypes("video/webm");
        FileBuffer receiver = new FileBuffer();
        setReceiver(receiver);

        addStartedListener(event -> {
            LOGGER.info("Upload started");
        });

        addSucceededListener(event -> {
            LOGGER.info("Upload succeeded");
            String absolutePath = receiver.getFileData().getFile().getAbsolutePath();
            LOGGER.info("File absolute path: " + absolutePath);

            String absolutePathDir = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
            String outputFileName = absolutePathDir + "/" + receiver.getFileName().replaceAll("\\W", "_") + ".mp4";

            LOGGER.info("Output file name: " + outputFileName);

            String command = String.format("ffmpeg -i %s %s", absolutePath, outputFileName);

            try {
                Process process = Runtime.getRuntime().exec(command);
                InputStream inputStream = process.getInputStream();
                int read = inputStream.read();
                while (read != -1) {
                    System.out.print((char) read);
                    read = inputStream.read();
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Notification notification = new Notification(
                    "File uploaded",
                    5000,
                    Notification.Position.MIDDLE
            );

            notification.open();

            LOGGER.info("File converted to mp4 : " + outputFileName);

            convertedFileManager.setConvertedFilePath(outputFileName);

            this.setVisible(false);
        });

        addFileRejectedListener(event -> {
            LOGGER.info("Upload file rejected");
            LOGGER.info("Error message: " + event.getErrorMessage());

            Notification notification = new Notification(
                    "File rejected: " + event.getErrorMessage(),
                    5000,
                    Notification.Position.MIDDLE
            );

            notification.open();
        });
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("UploadWebm postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.info("UploadWebm preDestroy");
    }

    public UploadWebm getThis() {
        return this;
    }
}
