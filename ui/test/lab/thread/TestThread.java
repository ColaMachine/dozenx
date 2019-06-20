package lab.thread;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:19 2019/4/11
 * @Modified By:
 */
public class TestThread {
@Test
    public void TestExcutorWhenException(){
        ScheduledExecutorService executorService ;

        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("123123");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


//                String s ="123";
//                s=s.substring(5);
            }
        }, 5, 5, TimeUnit.SECONDS);

    try {
        Thread.sleep(50000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
}
