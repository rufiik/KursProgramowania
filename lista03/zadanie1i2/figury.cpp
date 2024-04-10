#include "figury_geometryczne.hpp"
Kolo::Kolo(double promien) {
    std::cout << "Kolo" << std::endl;
    this->promien = promien;
}

double Kolo::obliczPole() {
    return M_PI * promien * promien;
}

double Kolo::obliczObwod() {
    return 2 * M_PI * promien;
}

Pieciokat::Pieciokat(double bok) {
    std::cout << "Pieciokat" << std::endl;
    this->bok = bok;
}

double Pieciokat::obliczPole() {
    return 0.5 * bok * bok * sin(72 * M_PI / 180.0);
}

double Pieciokat::obliczObwod() {
    return 5 * bok;
}

Szesciokat::Szesciokat(double bok) {
    std::cout << "Szesciokat" << std::endl;
    this->bok = bok;
}

double Szesciokat::obliczPole() {
    return (sqrt(3) * bok * bok) / 4;
}

double Szesciokat::obliczObwod() {
    return 6 * bok;
}


Kwadrat::Kwadrat(double bok) {
    std::cout << "Kwadrat" << std::endl;
    this->bok = bok;
}

double Kwadrat::obliczPole() {
    return bok * bok;
}

double Kwadrat::obliczObwod() {
    return 4 * bok;
}

Prostokat::Prostokat(double bok1, double bok2) {
    std::cout << "Prostokat" << std::endl;
    this->bok1 = bok1;
    this->bok2 = bok2;
}

double Prostokat::obliczPole() {
    return bok1 * bok2;
}

double Prostokat::obliczObwod() {
    return 2 * bok1 + 2 * bok2;
}

Romb::Romb(double bok, double kat) {
    std::cout << "Romb" << std::endl;
    this->bok = bok;
    this->kat = kat;
}

double Romb::obliczPole() {
    double radiany = kat * M_PI / 180.0;
    return bok * bok * sin(radiany);
}

double Romb::obliczObwod() {
    return bok * 4;
}
int main(int argc, char* argv[]) {
    if (argc < 3) {
        std::cout << "Za malo argumentow" << std::endl;
        return 1;
    }
    char rodzaj = argv[1][0];
    if(rodzaj!='o' && rodzaj!='c' && rodzaj!='p' && rodzaj!='s'){
        std::cout << "Zly rodzaj figury" << std::endl;
        return 1;
    }

    if (rodzaj=='o'){ //kolo
        if(argc!=3){
            std::cout << "Zla ilosc argumentow" << std::endl;
            return 1;
        }
        try{
            if(std::stoi(argv[2])<=0){
                throw std::invalid_argument("Zla wartosc promienia");
            }
        }
        catch(std::invalid_argument& e){
            std::cout << e.what() << std::endl;
            return 1;
        }

        double promien = std::stoi(argv[2]);
        Kolo kolo(promien);
        std::cout << "Pole: " << kolo.obliczPole() << std::endl;
        std::cout << "Obwod: " << kolo.obliczObwod() << std::endl;
    } 

    else if (rodzaj == 'c') { // czworokat
     if(argc!=7){
            std::cout << "Zla ilosc argumentow" << std::endl;
            return 1;
        }
        try {
            for (int i = 2; i < 6; i++) {
                if (std::stoi(argv[i]) <= 0) {
                    throw std::invalid_argument("zly bok");
                }
            }
            if (std::stoi(argv[6]) >= 180 || std::stoi(argv[6]) <= 0) {
                throw std::invalid_argument("zly kat");
            }
        }
        catch (const std::invalid_argument& e) {
            std::cout << e.what() << std::endl;
            return 0;
        }

        double bok1 = std::stoi(argv[2]);
        double bok2 = std::stoi(argv[3]);
        double bok3 = std::stoi(argv[4]);
        double bok4 = std::stoi(argv[5]);
        double kat = std::stoi(argv[6]);

        if (kat == 90 && bok1 == bok2 && bok3 == bok4 && bok2 == bok3) {
            Kwadrat kwadrat(bok1);
            std::cout << "Pole: " << kwadrat.obliczPole() << std::endl;
            std::cout << "Obwod: " << kwadrat.obliczObwod() << std::endl;
        } else if (kat == 90) {
            if (bok1 != bok2) {
                Prostokat prostokat(bok1, bok2);
                std::cout << "Pole: " << prostokat.obliczPole() << std::endl;
                std::cout << "Obwod: " << prostokat.obliczObwod() << std::endl;
            } else if (bok1 != bok3) {
                Prostokat prostokat(bok1, bok3);
                std::cout << "Pole: " << prostokat.obliczPole() << std::endl;
                std::cout << "Obwod: " << prostokat.obliczObwod() << std::endl;
     
            } else {
                Prostokat prostokat(bok1, bok4);
                std::cout << "Pole: " << prostokat.obliczPole() << std::endl;
                std::cout << "Obwod: " << prostokat.obliczObwod() << std::endl;
            }
        } else {
           Romb romb(bok1,kat);
            std::cout << "Pole: " << romb.obliczPole() << std::endl;
            std::cout << "Obwod: " << romb.obliczObwod() << std::endl;
        }
    }

    else if (rodzaj=='p'){ //pieciokat
    if(argc!=3){
        std::cout << "Zla ilosc argumentow" << std::endl;
        return 1;
    }
    double bok=0;
    try{
        bok = std::stod(argv[2]);
        if(bok<=0){
            throw std::invalid_argument("Zla wartosc boku");
        }
    }
    catch (std::invalid_argument& e){
        std::cout << "Zla wartosc boku" << std::endl;
        return 1;
    }

    Pieciokat pieciokat(bok);
    std::cout << "Pole: " << pieciokat.obliczPole() << std::endl;
    std::cout << "Obwod: " << pieciokat.obliczObwod() << std::endl;
}
else if (rodzaj=='s') { //szesciokat
    if(argc!=3){
        std::cout << "Zla ilosc argumentow" << std::endl;
        return 1;
    }
    double bok=0;
    try{
        bok = std::stod(argv[2]);
        if(bok<=0){
            throw std::invalid_argument("Zla wartosc boku");
        }
    }
    catch (std::invalid_argument& e){
        std::cout << "Zla wartosc boku" << std::endl;
        return 1;
    }

    Szesciokat szesciokat(bok);
    std::cout << "Pole: " << szesciokat.obliczPole() << std::endl;
    std::cout << "Obwod: " << szesciokat.obliczObwod() << std::endl;
}
   return 0;
}