import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SacADos {
    private Scanner scanner;
    private ArrayList<Item> listItemPossible;
    private ArrayList<Item> listItemDansSac;
    private final File file;
    private float poids_maximal;

    public SacADos(String chemin, float poids_maximal) {
        this.poids_maximal = poids_maximal;
        this.file = new File(chemin);
        this.listItemPossible = new ArrayList<>();
        this.listItemDansSac = new ArrayList<>();
        chargerItems();
    }

    public ArrayList<Item> getListObjetPossible() {
        return listItemPossible;
    }

    public float getValeurSac() {
        float prixActuelSac = 0;
        for (Item item : this.listItemDansSac) {
            prixActuelSac += item.getPrix();
        }
        return prixActuelSac;
    }

    public float getPoidsSac() {
        float poidsActuelSac = 0;
        for (Item item : this.listItemDansSac) {
            poidsActuelSac += item.getPoids();
        }
        return poidsActuelSac;
    }

    public void chargerItems() {
        String[] tab;
        try {
            this.scanner = new Scanner(this.file);
            while (this.scanner.hasNextLine()) {
                tab = this.scanner.nextLine().split(";");
                this.listItemPossible.add(new Item(tab[0], Float.parseFloat(tab[1]), Float.parseFloat(tab[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + " : Erreur de chargement du fichier");
            System.exit(1);
        }
    }

    public float retournePoidsActuelSac() {
        float poidsActuel = 0;
        for (Item item : this.listItemDansSac) {
            poidsActuel += item.getPoids();
        }
        return poidsActuel;
    }

    public float retournePrixActuelSac() {
        float valeurActuelle = 0;
        for (Item item : this.listItemDansSac) {
            valeurActuelle += item.getPrix();
        }
        return valeurActuelle;
    }

    public void resolutionGloutonne() {
        QuickSort.tri_rapide(this.listItemPossible, 0, this.listItemPossible.size()-1, false);
        int count = 0;
        while (retournePoidsActuelSac() + this.listItemPossible.get(count).getPoids() < this.poids_maximal) {
            this.listItemDansSac.add(this.listItemPossible.get(count));
            count++;
        }
    }

    public void resolutionDynamique2() {
        float[][] items = new float[this.listItemPossible.size()][((int)this.poids_maximal+1)*10];
        for (int i = 0; i < items[0].length; i++) {
            if (this.listItemPossible.get(0).getPoids() > ((float)i)/10.0)
                items[0][i] = 0;
            else
                items [0][i] = this.listItemPossible.get(0).getPrix();
        }

        for (int i = 1; i < this.listItemPossible.size(); i++) {
            for (int j =0; j < items[0].length; j++) {
                if (this.listItemPossible.get(i).getPoids() > ((float)j)/10.0)
                    items[i][j] = items[i-1][j];
                else
                    items[i][j] = Math.max(items[i-1][j], items[i-1][(int) (( ( ( (float)j) /10)-this.listItemPossible.get(i).getPoids())*10)] + this.listItemPossible.get(i).getPrix());
            }
        }

        int column = (int)this.poids_maximal * 10;
        int line = this.listItemPossible.size() - 1;
        while (items[line][column] == items[line][column-1]) {
            column--;
        }
        while (column > 0) {
            while (line > 0 && items[line][column] == items[line-1][column]) {
                line--;
            }
            column = (int) (( (((float)column)/10) - this.listItemPossible.get(line).getPoids()) * 10);
            if (column >= 0) {
                this.listItemDansSac.add(this.listItemPossible.get(line));
                //System.out.println("Ajout item = " + this.listItemPossible.get(line));
            }
            line--;
        }
        //Test affichage
        /*
        for (int objet = 0; objet < this.listItemPossible.size(); objet++) {
            for (int poids =0; poids < items[0].length; poids++) {
                System.out.print(items[objet][poids] + "\t");
            }
            System.out.println();
        }*/
    }

    public void resolutionDynamique() {
        float[][] items = new float[this.listItemPossible.size()][((int)this.poids_maximal+1)*10];
        for (int i = 0; i < items[0].length; i++) {
            if (this.listItemPossible.get(0).getPoids() > i)
                items[0][i] = 0;
            else
                items [0][i] = this.listItemPossible.get(0).getPrix();
        }

        for (int i = 1; i < this.listItemPossible.size(); i++) {
            for (int j =0; j < items[0].length; j++) {
                if (this.listItemPossible.get(i).getPoids() > j)
                    items[i][j] = items[i-1][j];
                else
                    items[i][j] = Math.max(items[i-1][j], items[i-1][j-(int)this.listItemPossible.get(i).getPoids()] + this.listItemPossible.get(i).getPrix());
            }
        }
        int column = (int)this.poids_maximal;
        int line = this.listItemPossible.size() - 1;
        while (items[line][column] == items[line][column-1]) {
            column--;
        }
        while (column > 0) {
            while (line > 0 && items[line][column] == items[line-1][column]) {
                line--;
            }
            column = column - (int) this.listItemPossible.get(line).getPoids();
            if (column >= 0) {
                this.listItemDansSac.add(this.listItemPossible.get(line));
                //System.out.println("Ajout item = " + this.listItemPossible.get(line));
            }
            line--;
        }
        //Test affichage
        /*
        for (int objet = 0; objet < this.listItemPossible.size(); objet++) {
            for (int poids =0; poids < this.poids_maximal +1; poids++) {
                System.out.print(items[objet][poids] + "\t");
            }
            System.out.println();
        }
         */
    }

    public void resolutionPSE() {
        float borneInf = 0;
        float weight = 0;
        ArrayList<Item> bestSolution = new ArrayList<>();
        for (Item item : this.listItemPossible) {
            if (item.getPoids() + weight <= this.poids_maximal) {
                weight += item.getPoids();
                borneInf += item.getPrix();
                bestSolution.add(item);
            }
        }
        NodePSE nodePSE = new NodePSE(this.listItemPossible,this.poids_maximal, borneInf, bestSolution);
        for (Item item : this.listItemPossible) {
            nodePSE.insertion(item);
        }
        //nodePSE.recherche();
        this.listItemDansSac.addAll(nodePSE.returnBestSolution());
        this.listItemPossible.removeAll(nodePSE.returnBestSolution());
    }

    @Override
    public String toString() {
        return "SacADos{ \n" + listItemDansSac +
                ", poidsDuSac =" + this.getPoidsSac() +
                ", valeur= " + this.getValeurSac() +
                '}';
    }
}
