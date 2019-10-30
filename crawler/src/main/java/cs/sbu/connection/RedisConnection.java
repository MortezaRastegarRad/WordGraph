package cs.sbu.connection;

import org.redisson.Redisson;
import org.redisson.api.RBatch;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;


public class RedisConnection implements ConnectionRedisInterface {

    RedissonClient client;

    public RedisConnection() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("127.0.0.1:6379");

        client = Redisson.create(config);
    }


    @Override
    public void setEntity(String value) {
        RBucket<Object> domain = client.getBucket(value);
        domain.set("1", 30, TimeUnit.SECONDS);
    }

    @Override
    public boolean isExist(String key) {
        String exist = client.getBucket(key).get().toString();
        if (exist.equals("1")) {
            return true;
        }
        return false;
    }
}
