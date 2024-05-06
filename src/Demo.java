public class Demo {
    public int max(int a,int b){
        return a>b?a:b;
    }
    public int max(int a,int b,int c){
        int d = max(b,c);
        return max(a,d);
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        System.out.println(demo.max(10, 11));
        System.out.println(demo.max(312, 120, 11));
    }
}
