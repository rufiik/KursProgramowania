interface PoleObwod {
    public double obliczPole();
    public double obliczObwod();
}
abstract class Figura implements PoleObwod{
    public abstract double obliczPole();
    public abstract double obliczObwod();
}
abstract class Czworokat extends Figura {
    public abstract double obliczPole();
    public abstract double obliczObwod();
}
class Kolo extends Figura {
    private double promien;
    public Kolo(double promien) {
        this.promien = promien;
    }
    public double obliczPole() {
        System.out.println("Kolo");
        return Math.PI * promien * promien;
    }
    public double obliczObwod() {
        return 2 * Math.PI * promien;
    }
}
class Pieciokat extends Figura {
    private double bok;
    public Pieciokat(double bok) {
        this.bok = bok;
    }
    public double obliczPole() {
        System.out.println("Pieciokat");
        return 0.5 * bok * bok * Math.sin(Math.toRadians(72));
    }
    public double obliczObwod() {
        return 5 * bok;
    }
}

class Szesciokat extends Figura {
    private double bok;
    public Szesciokat(double bok) {
        this.bok = bok;
    }
    public double obliczPole() {
        System.out.println("Szesciokat");
        return  (Math.sqrt(3) * bok * bok)/4;
    }
    public double obliczObwod() {
        return 6 * bok;
    }
}
class Kwadrat extends Czworokat {
    private double bok;
    public Kwadrat(double bok) {
        this.bok = bok;
    }
    public double obliczPole() {
        System.out.println("Kwadrat");
        return bok * bok;
    }
    public double obliczObwod() {
        return 4 * bok;
    }
}

class Prostokat extends Czworokat {
    private double bok1;
    private double bok2;
    public Prostokat(double bok1, double bok2) {
        this.bok1 = bok1;
        this.bok2 = bok2;
    }
    public double obliczPole() {
        System.out.println("Prostokat");
        return bok1 * bok2;
    }
    public double obliczObwod() {
        return 2 * bok1 + 2 * bok2;
    }
}
class Romb extends Czworokat {
    private double bok;
    private double kat;
    public Romb(double bok, double kat) {
        this.bok = bok;
        this.kat = kat;
    }
    public double obliczPole() {
        System.out.println("Romb");
        return bok * bok * Math.sin(Math.toRadians(kat));
    }
    public double obliczObwod() {
        return 4 * bok;
    }
}
public class figury {
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
            Kolo kolo = new Kolo(promien);
            System.out.println("Pole: " + kolo.obliczPole());
            System.out.println("Obwod: " + kolo.obliczObwod());
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
                Czworokat kwadrat = new Kwadrat(bok1);
                System.out.println("Pole: " + kwadrat.obliczPole());
                System.out.println("Obwod: " + kwadrat.obliczObwod());
            }
                else if (kat == 90) {
                    if (bok1 != bok2) {
                        Czworokat prostokat = new Prostokat(bok1, bok2);
                        System.out.println("Pole: " + prostokat.obliczPole());
                        System.out.println("Obwod: " + prostokat.obliczObwod());
                    } else if (bok1 != bok3) {
                        Czworokat prostokat = new Prostokat(bok1, bok3);
                        System.out.println("Pole: " + prostokat.obliczPole());
                        System.out.println("Obwod: " + prostokat.obliczObwod());
                    } else {
                        Czworokat prostokat = new Prostokat(bok1, bok4);
                        System.out.println("Pole: " + prostokat.obliczPole());
                        System.out.println("Obwod: " + prostokat.obliczObwod());
                    }
                }
                else{
                    Czworokat romb = new Romb(bok1, kat);
                    System.out.println("Pole: " + romb.obliczPole());
                    System.out.println("Obwod: " + romb.obliczObwod());
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

                    Figura pieciokat = new Pieciokat(bok);
                    System.out.println("Pole: " + pieciokat.obliczPole());
                    System.out.println("Obwod: " + pieciokat.obliczObwod());
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
     
                    Figura szesciokat = new Szesciokat(bok);
                    System.out.println("Pole: " + szesciokat.obliczPole());
                    System.out.println("Obwod: " + szesciokat.obliczObwod());
                }
            }
         }

    

