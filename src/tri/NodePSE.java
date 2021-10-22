package tri;

import sacADos.Item;

import java.util.ArrayList;

/**
 * Classe NodePSE, noeuds d'un arbre binaire pour la PSE
 * @author lngeth ybettayeb
 */
public class NodePSE {
    private static ArrayList<Item> listeItemPossible;
    private static ArrayList<Item> optimumItems;
    private static float capaciteMaxSac;
    private static float borneInf;

    private ArrayList<Item> listeItemDedans;
    private NodePSE filsGauche;
    private NodePSE filsDroit;
    private float valeurActuelleDedans;
    private float poidsActuelDedans;
    private float borneSup;
    private boolean etat;

    /**
     * Constructeur initial d'un arbre de recherche pour la PSE
     * @param listeItemPossible liste d'Item à analyser
     * @param capaciteMaxSac capacité maximal du sac
     * @param borneInf borne inférieure de l'actuelle meilleure solution
     * @param optimumItems liste d'Item avec la meilleure solution actuelle
     */
    public NodePSE(ArrayList<Item> listeItemPossible, float capaciteMaxSac, float borneInf, ArrayList<Item> optimumItems) {
        NodePSE.listeItemPossible = new ArrayList<>(listeItemPossible);
        NodePSE.capaciteMaxSac = capaciteMaxSac;
        NodePSE.borneInf = borneInf;
        NodePSE.optimumItems = optimumItems;
        this.listeItemDedans = new ArrayList<>();
        this.valeurActuelleDedans = 0;
        this.poidsActuelDedans = 0;
        this.etat = true;
    }

    /**
     * Constructeur pour chaque noeud lors de l'insertion des Items
     * @param listeItemDedans liste d'Item à insérer
     * @param valeurActuelleDedans valeur de la liste d'item à insérer
     * @param poidsActuelDedans poids de la liste d'item à insérer
     */
    public NodePSE(ArrayList<Item> listeItemDedans, float valeurActuelleDedans, float poidsActuelDedans) {
        this.listeItemDedans = new ArrayList<>(listeItemDedans);
        this.valeurActuelleDedans = valeurActuelleDedans;
        this.poidsActuelDedans = poidsActuelDedans;
        this.etat = true;
    }

    /**
     * Ajoute un Item dans la liste actuelle du noeud et modifie le poids et la valeur actuelle
     * @param item Item à insérer
     */
    public void ajoutItem(Item item) {
        this.listeItemDedans.add(item);
        this.valeurActuelleDedans += item.getPrix();
        this.poidsActuelDedans += item.getPoids();
    }

    /**
     * Insertion de l'Item dans l'arbre binaire PSE en récursif.
     * Si le noeuds actuelle est mort (etat = false) alors on ne fait rien.
     * Sinon, si le filsDroit est null, on se trouve au bonne endroit pour insérer l'Item, sinon on insère l'Item dans le filsGauche et filsDroit en récursion.
     * Si on est au bonne endroit, on vérifie si l'insertion de l'Item ne conduit pas à un dépassement de la capaciteMaximale du Sac, sinon on tue le filsDroit (filsDroit.etat = false).
     * A chaque noeud, on calcule les bornes inférieur et supérieur (calcUpper) des noeuds et on vérifie s'ils correspondent à une meilleure solution.
     * @param item Item à insérer
     */
    public void insertion(Item item) {
        if (this.etat) {
            if (this.filsDroit == null) {
                if (item.getPoids() + this.poidsActuelDedans <= NodePSE.capaciteMaxSac) {
                    this.filsDroit = new NodePSE(this.listeItemDedans, this.valeurActuelleDedans, this.poidsActuelDedans);
                    this.filsDroit.ajoutItem(item);
                    this.filsGauche = new NodePSE(this.listeItemDedans, this.valeurActuelleDedans, this.poidsActuelDedans);
                } else {
                    this.filsDroit = new NodePSE(this.listeItemDedans, this.valeurActuelleDedans, this.poidsActuelDedans);
                    this.filsDroit.etat = false;
                    this.filsGauche = new NodePSE(this.listeItemDedans, this.valeurActuelleDedans, this.poidsActuelDedans);
                }
                NodePSE.listeItemPossible.remove(item);
                this.filsDroit.calcUpper();
                checkUpper(this.filsDroit);
                this.filsGauche.calcUpper();
                checkUpper(this.filsGauche);
            } else {
                this.filsDroit.insertion(item);
                this.filsGauche.insertion(item);
            }
        }
    }

    /**
     * Vérifie si la borne supérieure du noeud fils est supérieur à la borne inférieur de la meilleur solution.
     * Si oui, on ce noeud prend la place de la mémoire solution et sa borne supérieur devient la borne inférieur de la meilleure solution.
     * Si non, on le tue (etat = false).
     * @param son noeuds fils à analyser
     */
    public void checkUpper(NodePSE son) {
        if (son.borneSup > NodePSE.borneInf) {
            NodePSE.borneInf = son.borneSup;
        } else if (son.borneSup < NodePSE.borneInf) {
            son.etat = false;
        }
        if (son.valeurActuelleDedans > calcActuelOptimumItemsValeur()) {
            NodePSE.optimumItems = new ArrayList<>(son.listeItemDedans);
        }
    }

    /**
     * Affiche le contenu de tous les noeuds terminaux (filsDroit = null) en récursif.
     */
    public void recherche() {
        if (this.filsDroit == null) {
            System.out.println(this.listeItemDedans);
        } else {
            this.filsDroit.recherche();
            this.filsGauche.recherche();
        }
    }

    /**
     * Calcule la valeur de la meilleur solution
     * @return float valeur de la meilleur solution
     */
    public float calcActuelOptimumItemsValeur() {
        float value = 0;
        for (Item item : NodePSE.optimumItems) {
            value += item.getPrix();
        }
        return value;
    }

    /**
     * @return liste de la meilleure solution
     */
    public ArrayList<Item> returnOptimumItems() {
        return NodePSE.optimumItems;
    }

    public void calcUpper() {
        float value = this.valeurActuelleDedans;
        float weight = this.poidsActuelDedans;
        for (Item item : NodePSE.listeItemPossible) {
            if (item.getPoids() + weight <= NodePSE.capaciteMaxSac) {
                weight += item.getPoids();
                value += item.getPrix();
            }
        }
        this.borneSup = value;
    }

    /**
     * Affiche toutes les caractéristiques du noeud
     * @return informations du noeud
     */
    @Override
    public String toString() {
        return "tri.NodePSE{" +
                "listItemInside=" + listeItemDedans +
                ", leftSon=" + filsGauche +
                ", righSon=" + filsDroit +
                ", upper= " + borneSup +
                ", alive= " + etat +
                '}';
    }
}
