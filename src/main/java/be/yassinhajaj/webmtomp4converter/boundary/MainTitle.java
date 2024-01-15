package be.yassinhajaj.webmtomp4converter.boundary;

import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;

import java.util.logging.Logger;

@CdiComponent
@CssImport("main-title.css")
public class MainTitle extends H1 {

    private static final Logger LOGGER = Logger.getLogger(MainTitle.class.getName());

    public MainTitle() {
        LOGGER.info("MainTitle constructor");
        String translation = getTranslation("main.title");
        LOGGER.info("main.title: " + translation);
        setText(translation);


        setClassName("main-title");
    }
}
