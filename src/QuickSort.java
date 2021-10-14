import java.util.ArrayList;

public class QuickSort {
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

    public static void echanger(ArrayList<Item> tab, int pivot, int dernier) {
        Item tmp = tab.get(pivot);
        tab.set(pivot, tab.get(dernier));
        tab.set(dernier, tmp);
    }

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

    public static void afficheTab(ArrayList<Integer> tab){
        for (int i = 0; i < tab.size()-1; i++) {
            System.out.print(tab.get(i)+";");
        }
        System.out.println(tab.size());
    }
}
