package sacADos;

import tri.NodePSE;
import tri.QuickSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe SacADos utiliser pour la résolution du problème du Sac à dos
 * @author lngeth ybettayeb
 */
public class SacADos {
    private final File fichier;
    private Scanner scanner;
    private ArrayList<Item> listItemPossible;
    private ArrayList<Item> listItemDansSac;
    private float poids_maximal;
    private float poidsActuelSac;
    private float valeurActuelSac;

    /**
     * Constructeur initial d'un SacADos
     * @param chemin chemin absolu vers le fichier source
     * @param poids_maximal poids maximal que le sac peut porter
     */
    public SacADos(String chemin, float poids_maximal) {
        this.fichier = new File(chemin);
        this.poids_maximal = poids_maximal;
        this.poidsActuelSac = 0;
        this.valeurActuelSac = 0;
        this.listItemPossible = new ArrayList<>();
        this.listItemDansSac = new ArrayList<>();
        chargerItems();
    }

    /**
     * Permet d'initialiser la liste des Items à analyser pour le problème du SacADos
     * Initialise listeItemPossible avec les valeurs du fichier.txt
     */
    public void chargerItems() {
        String[] tab;
        try {
            this.scanner = new Scanner(this.fichier);
            while (this.scanner.hasNextLine()) {
                tab = this.scanner.nextLine().split(";");
                this.listItemPossible.add(new Item(tab[0], Float.parseFloat(tab[1]), Float.parseFloat(tab[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + " : Erreur de chargement du fichier");
            System.exit(1);
        }
    }

    /**
     * Méthode 1 de résolution du problème du Sac à dos
     * Utilise l'algorithme de tri rapide
     */
    public void resolutionGloutonne() {
        QuickSort.triDesc(this.listItemPossible);
        for (Item item : this.listItemPossible) {
            if (this.poidsActuelSac + item.getPoids() < this.poids_maximal) {
                this.listItemDansSac.add(item);
                this.poidsActuelSac += item.getPoids();
                this.valeurActuelSac += item.getPrix();
            }
        }
        System.out.println("Méthode Gloutonne : ");
    }

    /**
     * Méthode 2 (version float) de la résolution du problème du Sac à dos
     * Utilise la programmation dynamique
     */
    public void resolutionDynamique2() {
        float[][] items = new float[this.listItemPossible.size()][(((int)this.poids_maximal)*10) + 1];

        for (int i = 0; i < this.listItemPossible.size(); i++) {
            if (i == 0) {
                for (int j = 0; j < items[0].length; j++) {
                    if (this.listItemPossible.get(0).getPoids() > ((float) j) / 10.0) {
                        items[0][j] = 0;
                    } else {
                        items[0][j] = this.listItemPossible.get(0).getPrix();
                    }
                }
            } else {
                for (int j = 0; j < items[0].length; j++) {
                    if (this.listItemPossible.get(i).getPoids() > ((float)j)/10.0)
                        items[i][j] = items[i-1][j];
                    else
                        items[i][j] = Math.max(items[i-1][j], items[i-1][(int) (( ( ( (float)j) /10)-this.listItemPossible.get(i).getPoids())*10)] + this.listItemPossible.get(i).getPrix());
                }
            }
        }

        int column = (int)this.poids_maximal * 10;
        int line = this.listItemPossible.size() - 1;
        while (items[line][column] == items[line][column-1]) {
            column--;
        }
        Item item;
        while (column > 0) {
            while (line > 0 && items[line][column] == items[line-1][column]) {
                line--;
            }
            column = (int) (( (((float)column)/10) - this.listItemPossible.get(line).getPoids()) * 10);
            if (column >= 0) {
                item = this.listItemPossible.get(line);
                this.listItemDansSac.add(item);
                this.valeurActuelSac += item.getPrix();
                this.poidsActuelSac += item.getPoids();
            }
            line--;
        }
        //afficheTab(items);
        System.out.println("Méthode dynamique : ");
    }

    /**
     * Affiche le tableau 2D
     * @param tab tableau 2D de float avec en ligne les objets i et en colonne les poids de 0 à j (contenance max du sac)
     */
    public void afficheTab(float[][] tab) {
        for (float[] floats : tab) {
            for (int poids = 0; poids < tab[0].length; poids++) {
                System.out.print(floats[poids] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Méthode 2 (version int) de la résolution du problème du Sac à dos
     * Utilise la programmation dynamique
     */
    public void resolutionDynamique() {
        float[][] items = new float[this.listItemPossible.size()][(int)this.poids_maximal+1];

        for (int i = 0; i < this.listItemPossible.size(); i++) {
            if (i == 0) {
                for (int j = 0; j< items[0].length; j++) {
                    if (this.listItemPossible.get(0).getPoids() > (float) j)
                        items[0][j] = 0;
                    else
                        items [0][j] = this.listItemPossible.get(0).getPrix();
                }
            } else {
                for (int j =0; j < items[0].length; j++) {
                    if (this.listItemPossible.get(i).getPoids() > (float) j)
                        items[i][j] = items[i-1][j];
                    else
                        items[i][j] = Math.max(items[i-1][j], items[i-1][j-(int)this.listItemPossible.get(i).getPoids()] + this.listItemPossible.get(i).getPrix());
                }
            }
        }

        int column = (int)this.poids_maximal;
        int line = this.listItemPossible.size() - 1;
        while (items[line][column] == items[line][column-1]) {
            column--;
        }
        Item item;
        while (column > 0) {
            while (line > 0 && items[line][column] == items[line-1][column]) {
                line--;
            }
            column = column - (int) this.listItemPossible.get(line).getPoids();
            if (column >= 0) {
                item = this.listItemPossible.get(line);
                this.listItemDansSac.add(item);
                this.valeurActuelSac += item.getPrix();
                this.poidsActuelSac += item.getPoids();
            }
            line--;
            if (line < 0) {
                break;
            }
        }
        //afficheTab(items);
    }

    /**
     * Méthode 3 de la résolution du problème du Sac à dos
     * Utilise la programmation par séparation et évaluation (PSE) ou branch and bound en anglais
     * Utilise l'algorithme de tri rapide permettant d'optimiser l'algorithme PSE
     * car permet de ne pas s'intéresser (dès le début) aux petites valeures ayant un fort poids
     */
    public void resolutionPSE() {
        QuickSort.triDesc(this.listItemPossible);
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
        this.listItemDansSac.addAll(nodePSE.returnOptimumItems());
        majValeurEtPoidsSac();
        System.out.println("Méthode PSE : ");
    }

    /**
     * Met à jour les variables d'instance poidsActuel et valeurActuelle du sac à dos
     */
    public void majValeurEtPoidsSac() {
        for (Item item : this.listItemDansSac) {
            this.valeurActuelSac += item.getPrix();
            this.poidsActuelSac += item.getPoids();
        }
    }

    /**
     * Affiche le contenu du sac
     * @return contenu du sac
     */
    @Override
    public String toString() {
        return "SacADos{ \n" + listItemDansSac +
                ", poidsDuSac =" + this.poidsActuelSac +
                "/" + this.poids_maximal +
                ", valeurDuSac= " + this.valeurActuelSac +
                '}';
    }
}
