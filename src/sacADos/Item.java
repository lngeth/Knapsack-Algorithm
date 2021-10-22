package sacADos;

/**
 * Classe Item correspond à chaque objet à traiter
 * @author lngeth ybettayeb
 */
public class Item {
    private final String nom;
    private final float poids;
    private final float prix;
    private final float prixSurPoids;

    /**
     * Constructeur initial d'un Item
     * @param nom nom de l'item
     * @param poids le poids en float de l'item
     * @param prix le prix en float de l'item
     */
    public Item(String nom, float poids, float prix) {
        this.nom = nom;
        this.poids = poids;
        this.prix = prix;
        this.prixSurPoids = this.prix/this.poids;
    }

    /**
     * @return poids en flaot de l'item
     */
    public float getPoids() {
        return poids;
    }

    /**
     * @return prix en float de l'item
     */
    public float getPrix() {
        return prix;
    }

    /**
     * @return rapport du prix sur le poids en float de l'item
     */
    public float getPrixSurPoids() {
        return prixSurPoids;
    }

    /**
     * Affiche les caractéristiques d'un Item
     * @return informations en String de l'item
     */
    @Override
    public String toString() {
        return "{'" + nom + "', " +
                poids + " kg, " +
                prix + " euros} \n";
    }
}
