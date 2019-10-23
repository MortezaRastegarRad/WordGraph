package cs.sbu;

import cs.sbu.config.AppConfig;
import cs.sbu.config.Config;
import cs.sbu.connection.RedisConnection;
import cs.sbu.crawl.Crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class App {

    public static void main(String[] args) throws IOException {
        RedisConnection redisConnection = new RedisConnection();
        AppConfig appConfig = new AppConfig();
        System.out.println(appConfig.getProperty("maxurlinqueue"));
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<String>(Integer.parseInt(appConfig.getProperty("maxurlinqueue")));
        arrayBlockingQueue.add("https://en.wikipedia.org/wiki/Main_Page");
        arrayBlockingQueue.add("https://www.amazon.com/gp/prime");
        ArrayList<String> checked = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(appConfig.getProperty("threadnumber")); i++) {
            Thread thread = new Thread(new Crawler(arrayBlockingQueue, redisConnection, checked));
            thread.start();
        }
    }
}
