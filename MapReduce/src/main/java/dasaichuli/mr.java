package dasaichuli;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class mr {
    public  static  class mrmap extends Mapper {
        @Override
        protected void map(Object key, Object value, Context context) throws IOException, InterruptedException {
//            appid ip                  mid                                 userid  login_type   request                    status  http_referer user_agent      time
//            1003	211.167.248.22	228ca2b3-a8c5-4ba5-bdcd-ffbf16dca1d3	10022	    1	        GET /tologin HTTP/1.1	404	    null	        null	1523173976476

            String s = value.toString();
            String[] split = s.split("\t");
            String appid = split[0];
            String ip = split[1];
            String mid = split[2];
            String userid = split[3];
            String login_type = split[4];
            String request = split[5];
            String status = split[6];
            String http_referer = split[7];
            String time = split[8];

            if (!mid.equals("null")){
                if (login_type.equals("1")){
                    login_type="本系统登录";
                }else if (login_type.equals("0")){
                    login_type="第三方集成";
                }

               String v=ip+"\t"+mid+"\t"+userid+"\t"+login_type+"\t"+request+"\t"+status+"\t"+http_referer+"\t"+time;
                context.write(new Text(appid),new Text(v));
            }

        }
    }
}
