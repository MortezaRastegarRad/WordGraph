package cs.sbu.connection;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisConnection implements ConnectionRedisInterface {


    public RedisConnection(){
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("127.0.0.1:6379");
//
//        RedissonClient client = Redisson.create(config);
    }


    @Override
    public void setEntity(String key) {

    }

    @Override
    public boolean isExist(String key) {
        return false;
    }
}
