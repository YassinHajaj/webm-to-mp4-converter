package be.yassinhajaj.webmtomp4converter.boundary;

import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

@CdiComponent
public class DownloadButton extends Anchor {

    private DownloadButton() {
        super();
        // force it to remain empty
        setText("");
        add();
    }

    public static DownloadButton forFile(String filePath) {
        DownloadButton downloadButton = new DownloadButton();
        downloadButton.setHref(filePath);
        downloadButton.setFileName(extractFileNameFromAbsolutePath(filePath));
        return downloadButton;
    }

    private void setFileName(String fileName) {
        getElement().setProperty("download", fileName);
    }

    private static String extractFileNameFromAbsolutePath(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    private static class InternalDownloadButton extends Button {

        private InternalDownloadButton() {
            getTranslation("download-button");
        }

    }
}
