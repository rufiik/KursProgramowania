#include <iostream>
#include "header.hpp"
using namespace std;
int main(int argc, char* argv[]){
    int n;
    try{
            if (argc < 2) {
            throw (string)"Brak argumentow.\n";
            return 1; 
            }
            n = stoi(argv[1]);
            if(n<0){
                throw (string)"Nieprawidlowy zakres\n";
                }
    }
    catch (const invalid_argument& ZlyTyp) {
        cout << "Nieprawidlowy pierwszy argument\n"; 
        return 1; 
        }
    catch (const string& ZlyZakres) {
        cout << ZlyZakres; 
            return 1;}
    WierszTrojkataPascala w(n);
    w.stworz(n);
    for (int i = 2; i < argc; i++) {
            int m=0;
            try{
                m=stoi(argv[i]);
                cout<<m<<" - "<<w.element(m)<<"\n";
            }
            catch(invalid_argument& ZlyTyp){
                cout<<argv[i]<<" - nieprawidlowa dana\n";
            }
            catch (const string& e) {
                cout << e; }
        }
        } 