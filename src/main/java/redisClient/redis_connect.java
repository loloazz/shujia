package redisClient;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Author: Yaolong
 * @Date: 2021/8/18 17:01
 * @Pagename: redisClient
 * @ProjectName: shujia
 * @Describe:
 **/
public class redis_connect {
    public static void main(String[] args) {
        // TODO: 2021/8/18  创建Jedis对象
        Jedis hadoop100 = new Jedis("Hadoop100", 6379);
        String ping = hadoop100.ping();
        System.out.println(ping);
    }

    @Test
    public void demo1(){
        Jedis hadoop100 = new Jedis("Hadoop100", 6379);
        String yaolong = hadoop100.set("yaolong", "3591");
        Set<String> keys = hadoop100.keys("*");
        for (String key : keys) {
            System.out.println(key);
            String s = hadoop100.get(key);
            System.out.println(s);
        }

    }


}
