package cs.sbu;

import cs.sbu.config.AppConfig;
import cs.sbu.crawl.Crawler;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class App {

    public static void main(String[] args) throws IOException {
        AppConfig appConfig = new AppConfig();
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<String>(Integer.parseInt(appConfig.getProperty("maxurlinqueue")));
        arrayBlockingQueue.add("https://en.wikipedia.org/wiki/Main_Page");
        for (int i = 0; i < Integer.parseInt(appConfig.getProperty("threadnumber")); i++) {
            Thread thread = new Thread(new Crawler(arrayBlockingQueue));
        }
    }
}
