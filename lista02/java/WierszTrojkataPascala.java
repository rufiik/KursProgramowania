class MojException2 extends Exception {};
public class WierszTrojkataPascala{
    int[] tab;
 public void stworz(int n){
    tab=new int[n+1]; 
    tab[0]=1; 
    for (int i = 1; i <= n; i++) {
       tab[i]=(tab[i-1]*(n-i+1))/i;
    }
 }
public WierszTrojkataPascala(int n){
}
    public int element(int m)throws MojException2{
        for(int k=0;k<tab.length;k++){
            if(k==m){
                return tab[k];
            }
        }
        throw new MojException2();
        
    }
}