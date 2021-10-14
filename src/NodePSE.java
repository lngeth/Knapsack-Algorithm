import java.util.ArrayList;

public class NodePSE {
    private static float lowerBound;
    private static ArrayList<Item> listItemPossible;
    private static float maxWeightCapacity;
    private static ArrayList<Item> bestSolution;
    private SacADos sacADos;
    private ArrayList<Item> listItemInside;
    private NodePSE leftSon;
    private NodePSE righSon;
    private boolean alive;
    private float upper;

    public NodePSE(ArrayList<Item> listItemPossible, float maxWeightCapacity, float borneInf, ArrayList<Item> bestSolution) {
        NodePSE.listItemPossible = new ArrayList<>(listItemPossible);
        NodePSE.maxWeightCapacity = maxWeightCapacity;
        NodePSE.lowerBound = borneInf;
        NodePSE.bestSolution = bestSolution;
        this.listItemInside = new ArrayList<>();
        this.alive = true;
    }

    public NodePSE(ArrayList<Item> listItemInside) {
        this.listItemInside = new ArrayList<>(listItemInside);
        this.alive = true;
    }

    public void addItem(Item item) {
        this.listItemInside.add(item);
    }

    public void insertion(Item item) {
        if (this.alive) {
            if (this.righSon == null) {
                if (item.getPoids() + this.weightListInside() <= NodePSE.maxWeightCapacity) {
                    this.righSon = new NodePSE(this.listItemInside);
                    this.righSon.addItem(item);
                    this.leftSon = new NodePSE(this.listItemInside);
                } else {
                    this.righSon = new NodePSE(this.listItemInside);
                    this.righSon.alive = false;
                    this.leftSon = new NodePSE(this.listItemInside);
                }
                NodePSE.listItemPossible.remove(item);
                this.righSon.calcUpper();
                if (this.righSon.upper > NodePSE.lowerBound) {
                    NodePSE.lowerBound = this.righSon.upper;
                } else if (this.righSon.upper < NodePSE.lowerBound) {
                    this.righSon.alive = false;
                }
                this.leftSon.calcUpper();
                if (this.leftSon.upper > NodePSE.lowerBound) {
                    NodePSE.lowerBound = this.leftSon.upper;
                } else if (this.leftSon.upper < NodePSE.lowerBound) {
                    this.leftSon.alive = false;
                }

                if (this.righSon.valueListInside() > calcActuelBestSolutionValue()) {
                    NodePSE.bestSolution = new ArrayList<>(this.righSon.listItemInside);
                }
                if (this.leftSon.valueListInside() > calcActuelBestSolutionValue()) {
                    NodePSE.bestSolution = new ArrayList<>(this.leftSon.listItemInside);
                }

            } else {
                this.righSon.insertion(item);
                this.leftSon.insertion(item);
            }
        }
    }

    public void recherche() {
        if (this.righSon == null) {
            System.out.println(this.listItemInside);
        } else {
            this.righSon.recherche();
            this.leftSon.recherche();
        }
    }

    public float calcActuelBestSolutionValue() {
        float value = 0;
        for (Item item : NodePSE.bestSolution) {
            value += item.getPrix();
        }
        return value;
    }

    public ArrayList<Item> returnBestSolution() {
        return NodePSE.bestSolution;
    }

    public float weightListInside() {
        float weight = 0;
        for (Item item : this.listItemInside) {
            weight += item.getPoids();
        }
        return weight;
    }

    public float valueListInside() {
        float value = 0;
        for (Item item : this.listItemInside) {
            value += item.getPrix();
        }
        return value;
    }

    public void calcUpper() {
        float value = this.valueListInside();
        float weight = this.weightListInside();
        for (Item item : NodePSE.listItemPossible) {
            if (item.getPoids() + weight <= NodePSE.maxWeightCapacity) {
                weight += item.getPoids();
                value += item.getPrix();
            }
        }
        this.upper = value;
    }

    @Override
    public String toString() {
        return "NodePSE{" +
                "listItemInside=" + listItemInside +
                ", leftSon=" + leftSon +
                ", righSon=" + righSon +
                ", upper= " + upper +
                ", alive= " + alive +
                '}';
    }
}
