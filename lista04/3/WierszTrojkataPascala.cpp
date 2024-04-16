#include <iostream>
using namespace std;
#include "header.hpp"


WierszTrojkataPascala::WierszTrojkataPascala(int n){
	}
    void WierszTrojkataPascala::stworz(int n){
    tab=new int[n+1]; 
    size=n+1;
    tab[0]=1; 
    for (int i = 1; i <= n; i++) {
       tab[i]=(tab[i-1]*(n-i+1))/i;
    }
 }
	int WierszTrojkataPascala::element(int m){
    for(int k=0;k<size;k++){
            if(k==m){
                return tab[k];
            }
        }
        throw (string)"liczba spoza zakresu\n";
        
    }
    WierszTrojkataPascala::~WierszTrojkataPascala() {
        delete[] tab; 
    }	
