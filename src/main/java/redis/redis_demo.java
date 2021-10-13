package redis;

/**
 * @Author: Yaolong
 * @Date: 2021/8/18 17:16
 * @Pagename: redisClient
 * @ProjectName: shujia
 * @Describe:
 * 需求：
 *  输入手机号 发送后随机生成6位数子码，2分钟有效
 *  输入验证码，点击验证，返回成功或者失败
 *  每个手机号一天中只能获取三个验证码
 *
 *  解决方案：
 *      1. 随机生成6位数子码Random
 *      2. 验证码，点击验证，返回成功或者失败   把验证码放在redis里面，设置过期时间120s。
 *      3.判断验证码是否一致，从redis获取验证码和输入的验证码进行比较
 *      4. 每个手机只能发送3此验证码
 *      incr 每次发送之后+1
 *      大于2不能提交
 *
 *
 **/
public class redis_demo {


}
