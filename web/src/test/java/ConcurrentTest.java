/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:30 2018/4/13
 * @Modified By:
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.RandomUtil;
import com.dozenx.util.StringUtil;

public class ConcurrentTest {




        private static int thread_num = 1;
        private static int client_num = 1;


        public static void main(String[] args) {

            // TODO Auto-generated method stub

            ExecutorService exec = Executors.newCachedThreadPool();
            // 50个线程可以同时访问
          //  final Semaphore semp = new Semaphore(thread_num);

            // 模拟2000个客户端访问
            for (int index = 0; index < client_num; index++) {
                final int NO = index;
                Runnable run = new Runnable() {
                    public void run() {
                        try {
                            // 获取许可
//                                    semp.acquire();
                            System.out.println("Thread:" + NO);
                            String host = "http://127.0.0.1:8082/advertsrv/ads/req";
                            String para = "adpos=1&usermac="+ StringUtil.getRandomAlphaString(12);
                            String result=   HttpRequestUtil.sendGet(host,para);
                            System.out.println("第：" + result + " 个"+result);
                            Thread.sleep((long) (Math.random()) * 1000);
//             释放
                            System.out.println("第：" + NO + " 个");

//            semp.release();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                };
                exec.execute(run);
            }
            // 退出线程池
            exec.shutdown();
        }

}
