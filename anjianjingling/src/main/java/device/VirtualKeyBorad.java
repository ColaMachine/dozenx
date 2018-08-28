package device;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:37 2018/8/20
 * @Modified By:
 */
public interface VirtualKeyBorad {
    public  void keyDown(int keyCode);
    public void keyUp(int keyCode);
    public void keyPress(int keyCode);
    public void input(String coetent);

    
}
