package cs.sbu.connection;

public interface ConnectionRedisInterface {

    public void setEntity (String key);
    public boolean isExist(String key);
}
