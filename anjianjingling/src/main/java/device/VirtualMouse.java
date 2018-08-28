package device;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:37 2018/8/20
 * @Modified By:
 */
public interface VirtualMouse {
    public  void leftClick(int x,int y);
    public void rightClick(int x,int y);
    public void drag(int fromX,int fromY,int toX,int toY,int secs);

}
