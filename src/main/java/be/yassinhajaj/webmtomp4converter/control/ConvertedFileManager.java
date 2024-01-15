package be.yassinhajaj.webmtomp4converter.control;

import jakarta.enterprise.context.SessionScoped;

import java.util.function.Consumer;

@SessionScoped
public class ConvertedFileManager {

    private String convertedFilePath;
    private Consumer<String> onConvertedFileChange;

    public void setOnConvertedFileChange(Consumer<String> onConvertedFileChange) {
        this.onConvertedFileChange = onConvertedFileChange;
    }

    public void setConvertedFilePath(String convertedFilePath) {
        if (this.convertedFilePath != null && this.convertedFilePath.equals(convertedFilePath)) {
            throw new IllegalStateException("Converted file path is already set to " + convertedFilePath);
        }

        if (this.onConvertedFileChange == null) {
            throw new IllegalStateException("onConvertedFileChange is not set");
        }

        if (convertedFilePath != null && !convertedFilePath.isBlank()) {
            this.convertedFilePath = convertedFilePath;
        }

        this.onConvertedFileChange.accept(convertedFilePath);
    }
}
