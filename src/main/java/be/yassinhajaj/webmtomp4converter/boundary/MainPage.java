package be.yassinhajaj.webmtomp4converter.boundary;

import be.yassinhajaj.webmtomp4converter.control.ConvertedFileManager;
import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@Route("")
@CdiComponent
@CssImport("main-page.css")
public class MainPage extends VerticalLayout {

    private static final Logger LOGGER = Logger.getLogger(MainPage.class.getName());

    public MainPage(
            MainTitle mainTitle,
            UploadWebm uploadWebm,
            ConvertedFileManager convertedFileManager
    ) {
        add(mainTitle);
        add(uploadWebm);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setClassName("main-page");

        convertedFileManager.setOnConvertedFileChange(filePath -> {
            LOGGER.info("Converted file changed");

            Anchor anchor = new Anchor(
                    new StreamResource(
                            "converted.mp4",
                            () -> {
                                try {
                                    return Files.newInputStream(Path.of(filePath));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                    ),
                    ""
            );
            anchor.getElement().setProperty("download", filePath.substring(filePath.lastIndexOf("/") + 1));
            Button button = new Button("Download Your .MP4 📹", new Icon(VaadinIcon.DOWNLOAD_ALT));
            button.addClassName("download-button");
            anchor.add(button);
            add(anchor);
        });
    }
}
