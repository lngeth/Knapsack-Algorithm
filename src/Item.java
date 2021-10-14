public class Item {
    private String nom;
    private float poids;
    private float prix;
    private float prixSurPoids;

    public Item(String nom, float poids, float prix) {
        this.nom = nom;
        this.poids = poids;
        this.prix = prix;
        this.prixSurPoids = this.prix/this.poids;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixSurPoids() {
        return prixSurPoids;
    }

    public void setPrixSurPoids(float prixSurPoids) {
        this.prixSurPoids = prixSurPoids;
    }

    @Override
    public String toString() {
        return "{'" + nom + "', " +
                poids + " kg, " +
                prix + " euros} \n";
    }
}
