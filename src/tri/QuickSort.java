package tri;

import sacADos.Item;

import java.util.ArrayList;

/**
 * Classe Quicksort, algorithme de tri adapté pour le problème du sac à dos. Toutes les méthodes sont en static, et utilisé en tant qu'outils.
 * @author lngeth ybettayeb
 * avec le cours du professeur M. Kurtz
 */
public class QuickSort {
    /**
     * @param tab tableau d'Item à trier
     * @param premier l'indice du premier élément à partir duquel trié
     * @param dernier l'indice du dernier élément à trier
     * @param pivot indice du pivot
     * @return entier
     */
    public static int partitionner(ArrayList<Item> tab, int premier, int dernier, int pivot) {
        echanger(tab, pivot, dernier);
        int j = premier;
        for (int i = premier; i <= dernier - 1; i++) {
            if (tab.get(i).getPrixSurPoids() <= tab.get(dernier).getPrixSurPoids()) {
                echanger(tab, i, j);
                j++;
            }
        }
        echanger(tab, dernier, j);
        return j;
    }

    /**
     * @param tab liste d'Item à trier
     * @param premier indice du premier élément à trier
     * @param dernier indice du dernier élément à trier
     * @param pivot indice du pivot
     * @return entier
     */
    public static int partitionnerDec(ArrayList<Item> tab, int premier, int dernier, int pivot) {
        echanger(tab, pivot, dernier);
        int j = premier;
        for (int i = premier; i <= dernier - 1; i++) {
            if (tab.get(i).getPrixSurPoids() > tab.get(dernier).getPrixSurPoids()) {
                echanger(tab, i, j);
                j++;
            }
        }
        echanger(tab, dernier, j);
        return j;
    }

    /**
     * @param tab liste d'Item à trier
     * @param pivot indice du pivot
     * @param dernier indice du dernier élément
     */
    public static void echanger(ArrayList<Item> tab, int pivot, int dernier) {
        Item tmp = tab.get(pivot);
        tab.set(pivot, tab.get(dernier));
        tab.set(dernier, tmp);
    }

    /**
     * @param tab liste d'Item à trier
     * @param premier indice du premier élément à trier
     * @param dernier indice du dernier élément à trier
     * @param croissant true si croissant, false si décroissant
     */
    public static void tri_rapide(ArrayList<Item> tab, int premier, int dernier, boolean croissant) {
        if (premier < dernier) {
            int pivot = (premier+dernier)/2;
            if (croissant)
                pivot = partitionner(tab, premier, dernier, pivot);
            else
                pivot = partitionnerDec(tab, premier, dernier, pivot);
            tri_rapide(tab, premier, pivot-1, croissant);
            tri_rapide(tab, pivot+1, dernier, croissant);
        }
    }

    /**
     * @param tab liste d'Item à trier
     */
    public static void triDesc(ArrayList<Item> tab) {
        tri_rapide(tab, 0, tab.size()-1, false);
    }
}
