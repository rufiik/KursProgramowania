class MojException extends Exception {};
public class Test{
    public static void main(String[] args) throws MojException {
        int n;
        try {
            n = Integer.parseInt(args[0]);
            if (n < 0) {
                throw new MojException();
            }
        }catch(MojException mniejsza){
            System.out.println(args[0]+" - Nieprawidlowy numer wiersza");
            return;
        } 
        catch (NumberFormatException ZlyTyp) {
            System.out.println("Nieprawidlowy pierwszy argument.");
            return;
        }
           
        WierszTrojkataPascala w=new WierszTrojkataPascala(n);
        w.stworz(n);

        for (int i = 1; i < args.length; i++) {
            int m=0;
            try{
                m=Integer.parseInt(args[i]);
                System.out.println(m+" - "+w.element(m));
            }
            catch(NumberFormatException ZlyTyp){
                System.out.println(args[i] + " - nieprawidlowa dana");
            }
            catch(MojException2 Poza){
                System.out.println(m + " - Liczba spoza zakresu");
                }
        }
        int j=0;
        boolean warunek=false;
        while(warunek!=true){
            WierszTrojkataPascala max=new WierszTrojkataPascala(j);
            max.stworz(j);
            for(int k=0;k<=j;k++){
                if(max.tab[k]<0){
                    warunek=true;
                    break;
                }
             
            }
            if(warunek){
                break;
            }
            j++;
        }

        System.out.println("Maksymalny wiersz dla int to "+(j-1));
    } 
   
}
