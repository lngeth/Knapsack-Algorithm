public class Item {
    private final String nom;
    private final float poids;
    private final float prix;
    private final float prixSurPoids;

    public Item(String nom, float poids, float prix) {
        this.nom = nom;
        this.poids = poids;
        this.prix = prix;
        this.prixSurPoids = this.prix/this.poids;
    }

    public float getPoids() {
        return poids;
    }

    public float getPrix() {
        return prix;
    }

    public float getPrixSurPoids() {
        return prixSurPoids;
    }

    @Override
    public String toString() {
        return "{'" + nom + "', " +
                poids + " kg, " +
                prix + " euros} \n";
    }
}
