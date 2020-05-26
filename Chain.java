/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrpheurstics;

/**
 *
 * @author Gindullin
 */
public class Chain {
    private int n;
    private int m;
    private int[][] links;
    private int[] linklength;
    
    public Chain(int x) {
        this.n = x;
        this.Reset();
    }
    
    private void Reset() {
        this.links = new int[this.n+1][this.n+1];
        this.linklength = new int[this.n+1];
        this.m = 0;
        for (int i = 1; i <= this.n; i++) {
            for (int j = 1; j <= this.n; j++) {
                this.links[i][j] = 0;
            }
            this.linklength[i] = 0;
        }
    }
    
    public void Chain(int[] path, int n, int i, int j, int in, int jn) {
        if ((i>=in)||(j>=jn)||(i==j)||(in==jn)||(i==jn)||(j==in)) {
            System.out.println("Error #3");
        }
        else {
            this.Reset();
            int k = 0;
            int s = 0;
            int start = 0;
            while (k < n) {
                if ((s==0)&&((path[k]==i)||(path[k]==in)||(path[k]==j)||(path[k]==jn)/*||(k == n - 1)*/)) {
                    s = 0;
                    start = k + 1;
                }
                else if ((path[k]==i)||(path[k]==in)||(path[k]==j)||(path[k]==jn)||(k == n - 1)) {
                    if ((k == n - 1)&&((path[k]!=i)&&(path[k]!=in)&&(path[k]!=j)&&(path[k]!=jn))) {
                        s++;
                    }
                    int[] templink = new int[s];
                    for (int p = 0; p < s; p++) {
                        templink[p]=path[start + p];
                    }
                    this.AddLink(templink, s);
                    start = k + 1;
                    s = 0;
                }
                else {
                    s++;
                }
                k++;
            }
        }
    }
    
    public int[] Path() {
        int[] temppath = new int[2*n];
        int k = 0;
        for (int i = 1; i <= this.m; i++) {
            for (int j = 1; j <= this.linklength[i]; j++) {
                temppath[k] = this.links[i][j];
                k++;
            }
        }
        return temppath;
    }
    
    public void Split() {
        if (this.m<5) {
            do {
                int maxl=this.linklength[1];
                int maxi=1;
                for (int i = 1; i <= m; i++) {
                    if (maxl<this.linklength[i]) {
                        maxl=this.linklength[i];
                        maxi=i;
                    }
                }
                int cutoffpoint = (int) this.linklength[maxi] / 2;
                int[] cut = new int[this.linklength[maxi] - cutoffpoint];
                for (int i = cutoffpoint + 1; i <= this.linklength[maxi]; i++) {
                    cut[i - (cutoffpoint + 1)] = this.links[maxi][i];
                    this.links[maxi][i] = 0;
                }
                this.InsertLink(cut, this.linklength[maxi] - cutoffpoint, maxi);
                this.linklength[maxi] = cutoffpoint;
            } while (this.m<5);
        }
    }
    
    public int getM() {
        return m;
    }
    
    public void PrintChainsFullTest() {
        System.out.println(this.n + " " + this.m);
        for (int i = 1; i <= this.n; i++) {
            for (int j = 1; j <= this.n; j++) {
                System.out.print(this.links[i][j] + " ");
            }
            System.out.println(" " + this.linklength[i]);
        }
        System.out.println("");
    }
    
    public void PrintChains() {
        System.out.println(this.n + " " + this.m);
        for (int i = 1; i <= m; i++) {
            System.out.print(this.linklength[i] + "  ");
            for (int j = 1; j <= linklength[i]; j++) {
                System.out.print(this.links[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public void AddLink(int[] link, int length) {
        this.m++;
        this.linklength[this.m] = length;
        for (int i = 1; i <= length; i++) {
            this.links[this.m][i] = link[i-1];
        }
        for (int i = length+1; i <= this.n; i++) {
            this.links[this.m][i] = 0;
        }
    }
    
    public void InsertLink(int[] link, int length, int row) {
        if (row>this.m) {
            System.out.println("Error #1 " + this.m + " " + row);
        }
        else if (row<0) {
            System.out.println("Error #2 " + this.m + " " + row);
        }
        else if (row==this.m) {
            this.AddLink(link, length);
        }
        else {
            for (int i=this.m; i>=row+1; i--) {
                for (int j=0; j<=this.n; j++) {
                    this.links[i+1][j]=this.links[i][j];
                }
                this.linklength[i+1]=this.linklength[i];
            }
        this.linklength[row+1] = length;
        for (int i = 1; i <= length; i++) {
            this.links[row+1][i] = link[i-1];
        }
        for (int i = length+1; i <= this.n; i++) {
            this.links[row+1][i] = 0;
        }
            this.m++;
        }
    }
    
    
}
