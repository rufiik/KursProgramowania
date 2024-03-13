#include <iostream>
using namespace std;
class LiczbyPierwsze{
	public:
	bool* tab;
	int size;
	LiczbyPierwsze(int n){
		
    
	}
    int liczbyP(int n){
        tab=new bool[n+1];
		size=n+1;
    for (int i = 2; i <= n; i++) {
        tab[i] = true;
    }
    for(int i=2;i*i<=n;i++){
        if(tab[i]){
            for(int j=i*i;j<=n;j+=i){
                tab[j]=false;         
                }
            }
        }
    }
	int liczba(int m){
        int licznik=0;
        for(int k=0;k<size;k++){
            if(tab[k]==true){
                
                if(licznik==m){
                    return k;
                }
                licznik++;
            }
        }
        throw (string)"liczba spoza zakresu\n";
        
    }
    ~LiczbyPierwsze() {
        delete[] tab; 
    }	
};
int main(int argc, char* argv[]){
    
    int n;
    try{
            if (argc < 2) {
            throw (string)"Brak argumentow.\n";
            return 1; 
            }
            n = stoi(argv[1]);
            if(n<2){
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
            
    LiczbyPierwsze l(n);
    l.liczbyP(n);
    for (int i = 2; i < argc; i++) {
            int m=0;
            try{
                m=stoi(argv[i]);
                cout<<m<<" - "<<l.liczba(m)<<"\n";
            }
            catch(invalid_argument& ZlyTyp){
                cout<<argv[i]<<" - nieprawidlowa dana\n";
            }
            catch (const string& e) {
                cout << e; }
        }
        } 