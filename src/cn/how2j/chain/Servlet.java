package cn.how2j.chain;
 
import java.util.Random;
 
public class Servlet {
 
    public void service(Request request, Response response) {
        int random = 500 + new Random().nextInt(500);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("servlet have done some time consume business");
    }
}