public class podzielnosc{
    public static int div(int n) {
        int maxPodzielnosc=0;
        for(int i=2;i<=n/2;i++){
            if(n%i==0){
                maxPodzielnosc=i;
            }
        }
    return maxPodzielnosc;
    }
    public static void main(String[] args) {
        for(int i=0;i<args.length;i++){
            try {
                 int n=Integer.parseInt(args[i]); 
                 System.out.println(n+" "+div(n));
                }
            catch (NumberFormatException ex) {
            System.out.println(args[i] + " nie jest liczba calkowita");
            }
        }
    }
}