package dataformat;

import org.apache.jute.compiler.JString;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
        String data1 = "1月20日";
        data1="2021年"+data1;

        String pa = "yyyy年MM月dd日";
        long time = new SimpleDateFormat(pa).parse(data1).getTime();
        System.out.println(time);





        System.out.printf(data1);


    }
}
