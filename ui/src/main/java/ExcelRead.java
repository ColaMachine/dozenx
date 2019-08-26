import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:16 2019/8/22
 * @Modified By:
 */
public class ExcelRead {
    public static void main(String args[]) {


        File file = new File("C:\\Users\\dozen.zhang\\Desktop\\catalina.out");

        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一行");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            int index = -1;
//一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
//把当前行号显示出来

                if ((index = tempString.indexOf("img_cal.py[199")) != -1) {
                    tempString = tempString.substring(index + "img_cal.py[199]-INFO: ".length());
                    String arg[] = tempString.split(" ");

                    String detect = arg[2];
                    String blur = arg[4];
                    String vector = arg[6];

                    System.out.println("");
                    while ((tempString = reader.readLine()) != null) {
                        if ((index = tempString.indexOf("task.py[101]-INFO: ")) != -1) {
                            tempString = tempString.substring(index + "task.py[101]-INFO: ".length());
                            arg = tempString.split(" ");

                            String read = arg[0].split(":")[1];
                            String detect1 = arg[1].split(":")[1];
                            String url = arg[2].split(":")[1];

                            System.out.println(detect + " " + blur + " " + vector + " " + read + " " + detect1 + " " + url);
                            break;
                        }
                    }
                }

                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}