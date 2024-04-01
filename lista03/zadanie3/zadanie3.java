public class zadanie3{
    interface PoleObwod {
        public double obliczPole(double bok);
        public double obliczObwod(double bok);
        public String PodajNazwe();
}
interface PoleObwod2 {
    public double obliczPole(double bok, double kat);
    public double obliczObwod(double bok, double kat);
    public String PodajNazwe();
}
public enum JedenParametr implements PoleObwod{
    Kolo {
        public double obliczPole(double promien) {
            return Math.PI * promien * promien;
        }
        public double obliczObwod(double promien) {
            return 2 * Math.PI * promien;
        }
        public String PodajNazwe(){
            return "Kolo";
        }
    },
    Pieciokat {
        public double obliczPole(double bok) {
            return 0.5 * bok * bok * Math.sin(Math.toRadians(72));
        }
        public double obliczObwod(double bok) {
            return 5 * bok;
        }
        public String PodajNazwe(){
            return "Pieciokat";
        }
    },
    Szesciokat {
        public double obliczPole(double bok) {
            return  (Math.sqrt(3) * bok * bok)/4;
        }
        public double obliczObwod(double bok) {
            return 6 * bok;
        }
        public String PodajNazwe(){
            return "Szesciokat";
        }
    },
    Kwadrat {
        public double obliczPole(double bok) {
            return bok * bok;
        }
        public double obliczObwod(double bok) {
            return 4 * bok;
        }
        public String PodajNazwe(){
            return "Kwadrat";
        }
    } 

}
public enum DwaParametry implements PoleObwod2{
  Prostokat
    {
        public double obliczPole(double bok1, double bok2) {
            return bok1 * bok2;
        }
        public double obliczObwod(double bok1, double bok2) {
            return 2 * bok1 + 2 * bok2;
        }
        public String PodajNazwe(){
            return "Prostokat";
        }
    },
    Romb
    {
        public double obliczPole(double bok, double kat) {
            return bok * bok * Math.sin(Math.toRadians(kat));
        }
        public double obliczObwod(double bok, double kat) {
            return 4 * bok;
        }
        public String PodajNazwe(){
            return "Romb";
        }
      
}
}



    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Za malo argumentow");
            return;
        }
        char rodzaj = args[0].charAt(0);
        if(rodzaj!='o' && rodzaj!='c' && rodzaj!='p' && rodzaj!='s'){
            System.out.println("Zly rodzaj figury");
            return;}

        if (rodzaj=='o'){ //kolo
            if(args.length!=2){
                System.out.println("Zla ilosc argumentow");
                return;
            }
            try{
                if(Integer.parseInt(args[1])<=0){
                    throw new IllegalArgumentException();
                }
            }
            catch(NumberFormatException e){
                System.out.println("Zly typ argumentu");
                return;
            }
            catch(IllegalArgumentException e){
                System.out.println("Zla wartosc promienia");
                return;
            }

            double promien = Integer.parseInt(args[1]);
            JedenParametr kolo = JedenParametr.Kolo;
            System.out.println("Pole: " + kolo.obliczPole(promien));
            System.out.println("Obwod: " + kolo.obliczObwod(promien));
            System.out.println("Nazwa: " + kolo.PodajNazwe());
        } else if (rodzaj=='c') { //czworokat
            if(args.length!=6){
                System.out.println("Zla ilosc argumentow");
                return;
            }

            try{
                for(int i=1; i<5; i++){
                    if(Integer.parseInt(args[i])<=0){
                        throw new IllegalArgumentException("zly bok");
                    }
                }
                if(Integer.parseInt(args[5])>180 || Integer.parseInt(args[5])<=0){
                    throw new IllegalArgumentException("zly kat");
                   

                }
            }
            catch(NumberFormatException e){
                System.out.println("Zly typ argumentu");
                return;
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                return;
            }

            double bok1 = Integer.parseInt(args[1]);
            double bok2 = Integer.parseInt(args[2]);
            double bok3 = Integer.parseInt(args[3]);
            double bok4 = Integer.parseInt(args[4]);
            double kat = Integer.parseInt(args[5]);


            if(kat==90 && bok1==bok2 && bok3==bok4 && bok2==bok3){
                JedenParametr kwadrat = JedenParametr.Kwadrat;
                System.out.println("Pole: " + kwadrat.obliczPole(bok1));
                System.out.println("Obwod: " + kwadrat.obliczObwod(bok1));
                System.out.println("Nazwa: " + kwadrat.PodajNazwe());
            }
                else if (kat == 90) {
                    if (bok1 != bok2) {
                        DwaParametry prostokat = DwaParametry.Prostokat;
                        System.out.println("Pole: " + prostokat.obliczPole(bok1,bok2));
                        System.out.println("Obwod: " + prostokat.obliczObwod(bok1,bok2));
                        System.out.println("Nazwa: " + prostokat.PodajNazwe());
                    } else if (bok1 != bok3) {
                        DwaParametry prostokat = DwaParametry.Prostokat;
                        System.out.println("Pole: " + prostokat.obliczPole(bok1,bok2));
                        System.out.println("Obwod: " + prostokat.obliczObwod(bok1,bok2));
                        System.out.println("Nazwa: " + prostokat.PodajNazwe());
                    } else {
                        DwaParametry prostokat =DwaParametry.Prostokat;
                        System.out.println("Pole: " + prostokat.obliczPole(bok1,bok2));
                        System.out.println("Obwod: " + prostokat.obliczObwod(bok1,bok2));
                        System.out.println("Nazwa: " + prostokat.PodajNazwe());
                    }
                }
                else{
                    DwaParametry romb = DwaParametry.Romb;
                    System.out.println("Pole: " + romb.obliczPole(bok1,kat));
                    System.out.println("Obwod: " + romb.obliczObwod(bok1,kat));
                    System.out.println("Nazwa: " + romb.PodajNazwe());
                }
            }
                else if (rodzaj == 'p') { //pieciokat
                    if(args.length!=2){
                        System.out.println("Zla ilosc argumentow");
                        return;
                    }
                    double bok=0;

                    try{ 
                        bok = Double.parseDouble(args[1]);
                        if(bok<=0){
                            throw new IllegalArgumentException();
                        }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Zly typ argumentu");
                        return;
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Zla wartosc boku");
                        return;
                    }

                    JedenParametr pieciokat = JedenParametr.Pieciokat;
                    System.out.println("Pole: " + pieciokat.obliczPole(bok));
                    System.out.println("Obwod: " + pieciokat.obliczObwod(bok));
                    System.out.println("Nazwa: " + pieciokat.PodajNazwe());
                }
                else if (rodzaj == 's') { //szesciokat
                    if(args.length!=2){
                        System.out.println("Zla ilosc argumentow");
                        return;
                    }
                    double bok=0;
               
                    try{
                        bok = Double.parseDouble(args[1]);
                        if(bok<=0){
                            throw new IllegalArgumentException();
                        }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Zly typ argumentu");
                        return;
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Zla wartosc boku");
                        return;
                    }
     
                    JedenParametr szesciokat = JedenParametr.Szesciokat;
                    System.out.println("Pole: " + szesciokat.obliczPole(bok));
                    System.out.println("Obwod: " + szesciokat.obliczObwod(bok));
                    System.out.println("Nazwa: " + szesciokat.PodajNazwe());
                }
            }
        }
         

        
  

