public class test2 {

    public static void main(String args[]) {
        int a = 1;
        int b = a + 1;
        test2 c = new test2();
        int d = c.method1(a);
    }

    public int method1(int v1) {
       // v1 = v1 + 1;
        //int c=(int)(Math.random()*1000);
       // v1+=c;
        v1 = method2(v1);
        return v1;
    }

    public int method2(int v2) {
        v2 = v2 + 1;
        return v2;
    }

}
