class MojException2 extends Exception {};
public class LiczbyPierwsze{
    boolean[] tab;
    public void liczbyP(int n){
        tab=new boolean[n+1];  
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

public LiczbyPierwsze(int n){
    
    }

    public int liczba(int m)throws MojException2{
        int licznik=0;
        for(int k=0;k<tab.length;k++){
            if(tab[k]==true){
                
                if(licznik==m){
                    return k;
                }
                licznik++;
            }
        }
        throw new MojException2();
        
    }
}