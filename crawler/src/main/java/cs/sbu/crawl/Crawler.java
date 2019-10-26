package cs.sbu.crawl;

import cs.sbu.connection.RedisConnection;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Crawler implements Runnable {

    RedisConnection redisConnection;
    ArrayBlockingQueue<String> arrayBlockingQueue;
    ArrayList<String> checked;
    private static final Logger LOGGER = Logger.getLogger(Crawler.class);


    public Crawler(ArrayBlockingQueue arrayBlockingQueue, RedisConnection redisConnection, ArrayList<String> checked) {
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.redisConnection = redisConnection;
        this.checked = checked;
    }

    @Override
    public void run() {
        while (arrayBlockingQueue.size() > 0) {
            System.out.println(Thread.currentThread().getName());
            String url = null;
            try {
                url = arrayBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//TODO:  !redisConnection.isExist(url)
            if (!checked.contains(url)) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(20000).get();
                    Elements links = doc.select("a[href]");
                    Thread newThread = new Thread(new addToQueue(links,arrayBlockingQueue));
                    newThread.start();
//                    TODO:redisConnection.setEntity(url);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            System.out.println("url : " + url);
            System.out.println("_____________________________________________________________________________________");
        }
    }
}

class addToQueue implements Runnable {
    Elements links;
    ArrayBlockingQueue arrayBlockingQueue;

    addToQueue(Elements links, ArrayBlockingQueue arrayBlockingQueue) {
        this.links = links;
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void run() {
        try {
            add(this.links);
        } catch (InterruptedException e) {
            System.out.println("salam dada");
        }
    }

    void add(Elements links) throws InterruptedException {
        System.out.println(arrayBlockingQueue.size());
        for (Element link : links) {
            arrayBlockingQueue.offer(link.attr("abs:href"), 3, TimeUnit.SECONDS);

            System.out.println(link.attr("abs:href"));
        }
    }
}
