class MojException extends Exception {};
public class Test {
    public static void main(String[] args) throws MojException {
        int n;
        try {
            n = Integer.parseInt(args[0]);
            if (n < 2) {
                throw new MojException();
            }
        }catch(MojException mniejsza){
            System.out.println("Nieprawidlowy zakres");
            return;
        } 
        catch (NumberFormatException ZlyTyp) {
            System.out.println("Nieprawidlowy pierwszy argument.");
            return;
        }
        
        LiczbyPierwsze l = new LiczbyPierwsze(n);
        l.liczbyP(n);
        // for(int k=2;k<=n;k++){
        //     System.out.println(l.tab[k]);
        // }
        for (int i = 1; i < args.length; i++) {
            int m=0;
            try{
                m=Integer.parseInt(args[i]);
                System.out.println(m+" - "+l.liczba(m));
            }
            catch(NumberFormatException ZlyTyp){
                System.out.println(m + " - nieprawidlowa dana");
            }
            catch(MojException2 Poza){
                System.out.println(m + " - Liczba spoza zakresu");
                }
        }
    } 
}
