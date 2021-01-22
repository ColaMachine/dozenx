import com.dozenx.common.util.ImageUtil;
import com.dozenx.web.module.shop.action.ColorPicker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:05 2018/8/21
 * @Modified By:
 */
public class camera {
    public static void main1(String args[]){
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int left =233;
            int top=192;
            int height=756;
            int width =1677;

            int destWidth=1677;
            int destHeight=756;
            BufferedImage newImage =new BufferedImage(destWidth,destHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics =(Graphics2D) newImage.getGraphics();
            graphics.setColor(new Color(246, 96, 0));
            // graphics.scale(0.5, 0.5);
            // while(true){
//            System.out.println("the "+i+" jpg");
           // outputStream.write(("Content-Type: image/jpeg"+end).getBytes() );

            BufferedImage originalImage= null;
            try {
                originalImage = ColorPicker.captureScreen(left,top,width+left,height+top);
            } catch (Exception e) {
                e.printStackTrace();
            }


            graphics.drawImage(originalImage, 0, 0,destWidth,destHeight, null);

            Point mousepoint
                    = MouseInfo.getPointerInfo().getLocation();
            graphics.fillRect(((int)mousepoint.getX()-left)*destWidth/width, ((int)mousepoint.getY()-top)*destHeight/height, 15, 15);
            //ImageIO.write(originalImage, "PNG", new File("D:\\abc.png"));
            //  graphics.dispose();


//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write( newImage, "jpeg", new File("G:\\workspace\\dozenx1\\dozenx\\ui\\src\\main\\webapp\\static\\a.jpg") );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String args[]) {
        ServerSocket s = null;

        try {
            //设定服务端的端口号
            s = new ServerSocket(7073);
            System.out.println("开启服务:" + s);
            //等待请求,此方法会一直阻塞,直到获得请求才往下走

           while (true) {
                Socket socket = s.accept();
                System.out.println("新的请求");
                InputStream inputSteram = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                byte[] readbts = new byte[5000];
                inputSteram.read(readbts);
//                while(inputSteram.read(readbts)>1){
                System.out.println(new String(readbts));
//                }

                // serverContext.workerMap.put(worker.hashCode(),worker);

               // Writer writer =new OutputStreamWriter(outputStream);
               try {
                   writeJPG(outputStream);
                   // writeHtml(outputStream);

               }catch (Exception e){
                   e.printStackTrace();
               }finally {
                   try {


                   inputSteram.close();
                   outputStream.close();
                   socket.close();
                   }catch ( Exception e){
                       e.printStackTrace();
                   }
               }
           }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("Close.....");
            try {

                s.close();
            } catch (Exception e2) {

            }
        }
    }
    public static void writeHtml(OutputStream outputStream) throws Exception {

        String end ="\r\n";
        outputStream.write(("HTTP/1.0 200 OK"+end).getBytes() );

//        outputStream.write(("Connection: close"+end).getBytes() );
//        outputStream.write(("Server: MJPG-Streamer/0.2"+end).getBytes() );
//        outputStream.write(("Cache-Control: no-store, no-cache, must-revalidate, pre-check=0, post-check=0, max-age=0"+end).getBytes() );
//        outputStream.write(("Pragma: no-cache"+end).getBytes() );
//        outputStream.write(("Expires: Mon, 3 Jan 2000 12:34:56 GMT"+end).getBytes() );
        outputStream.write(("Content-Type: text/html;charset=ISO-8859-1"+end).getBytes() );

        String content ="asdfhjajsdfkjasjdfklajsdklf;jasdjfajdghjkhsajdfaklsdaf";

        outputStream.write(("Content-Length:" + content.length()+end).getBytes() );
        outputStream.write((end).getBytes() );
        outputStream.write((content).getBytes() );
        outputStream.write((end).getBytes() );

        outputStream.write((content).getBytes() );
        outputStream.write((end).getBytes() );

        outputStream.flush();
        outputStream.close();
    }
        public static void writeJPG(OutputStream outputStream) throws Exception {



        String end ="\r\n";
        outputStream.write(("HTTP/1.0 200 OK"+end).getBytes() );



            int left =233;
            int top=192;
            int height=756;
            int width =1677;

            int destWidth=1677;
            int destHeight=756;
        BufferedImage newImage =new BufferedImage(destWidth,destHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics =(Graphics2D) newImage.getGraphics();
            graphics.setColor(new Color(246, 96, 0));
         // graphics.scale(0.5, 0.5);
       // while(true){
//            System.out.println("the "+i+" jpg");


            BufferedImage originalImage= ColorPicker.captureScreen(left,top,width+left,height+top);


            graphics.drawImage(originalImage, 0, 0,destWidth,destHeight, null);

//            Point mousepoint
//                    = MouseInfo.getPointerInfo().getLocation();
//            graphics.fillRect(((int)mousepoint.getX()-left)*destWidth/width, ((int)mousepoint.getY()-top)*destHeight/height, 15, 15);
            //ImageIO.write(originalImage, "PNG", new File("D:\\abc.png"));
          //  graphics.dispose();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( newImage, "jpeg", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            System.out.println(imageInByte.length);
            outputStream.write(("Content-Type: image/jpeg"+end).getBytes() );
            outputStream.write(("Content-Length: "+imageInByte.length+end).getBytes() );
            outputStream.write((end).getBytes() );
            outputStream.write(imageInByte);

            outputStream.flush();
            Thread.sleep(200);

       // }
//        outputStream.close();
    }

}