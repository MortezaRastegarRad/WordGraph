package cs.sbu.config;

import java.io.IOException;

public class AppConfig extends Config {

    public AppConfig() throws IOException {
        super();
        super.properties.load(AppConfig.class.getClassLoader().getResourceAsStream("App.properties"));
    }
}
