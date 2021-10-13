package redis;


import redis.clients.jedis.Jedis;


import java.util.Random;

/**
 * @Author: Yaolong
 * @Date: 2021/8/18 17:26
 * @Pagename: redis
 * @ProjectName: shujia
 * @Describe:
 **/
public class phonecode {

    public static void main(String[] args) {
//        String getcode = getcode();
//        System.out.println(getcode);
//        verifyCode("19156868");

        getRedisCode("19156868","204429");
    }

    //生成验证码
    public static String getcode() {
        Random random = new Random();

        String code = "";
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            code = code + i1;
        }
        return code;

    }






    
    public static void verifyCode(String phoneNum) {
        Jedis hadoop100 = new Jedis("Hadoop100", 6379);

        String keyphone = "verify" + phoneNum + ":phoneNum";

        String keyCount = "verify" + phoneNum + ":keyCount";

        String s = hadoop100.get(keyCount);
        if (s == null) {
            //第一次发送
            hadoop100.setex(keyCount, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(s) < 2) {
            hadoop100.incr(keyCount);
        } else {
            System.out.println("今天已经获取三次验证码了");
        }

        // 发送验证码放到redis中
        String getcode = getcode();
        hadoop100.setex(keyphone, 120, getcode);
        hadoop100.close();
    }

    public static void getRedisCode(String phone, String code) {
        //        String keyCode = "verify" + phoneNum + ":keyCode";
        Jedis hadoop100 = new Jedis("Hadoop100", 6379);
        String s = hadoop100.get("verify" + phone + ":phoneNum");
        if (code.equals(s)){
            System.out.println("成功！");
        }else {
            System.out.println("失败");
        }

    }
}
