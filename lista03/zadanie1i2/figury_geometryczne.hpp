#ifndef FIGURY_GEOMETRYCZNE_HPP
#define FIGURY_GEOMETRYCZNE_HPP

#include <iostream>
#include <stdexcept>
#include <vector>
#include <cmath>
#include <string>

#ifndef M_PI
    #define M_PI 3.14159265358979323846
#endif

class Figura {
public:
    virtual double obliczPole() = 0;
    virtual double obliczObwod() = 0;
};

class Czworokat : public Figura {
public:
    virtual double obliczPole() = 0;
    virtual double obliczObwod() = 0;
};

class Kolo : public Figura {
private:             
    double promien;
public:
    Kolo(double promien);
    double obliczPole();
    double obliczObwod();
};

class Pieciokat : public Figura {
private:
    double bok;
public:
    Pieciokat(double bok);
    double obliczPole();
    double obliczObwod();
};

class Szesciokat : public Figura {
private:
    double bok;
public:
    Szesciokat(double bok);
    double obliczPole();
    double obliczObwod();
};

class Kwadrat : public Czworokat {
private:
    double bok;
public:
    Kwadrat(double bok);
    double obliczPole();
    double obliczObwod();
};

class Prostokat : public Czworokat {
private:
    double bok1;
    double bok2;
public:
    Prostokat(double bok1, double bok2);
    double obliczPole();
    double obliczObwod();
};

class Romb : public Czworokat {
private:
    double bok;
    double kat;
public:
    Romb(double bok, double kat);
    double obliczPole();
    double obliczObwod();
};

#endif // FIGURY_GEOMETRYCZNE_H