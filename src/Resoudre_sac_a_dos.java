public class Resoudre_sac_a_dos {
    public static void main(String[] args) {
        try {
            SacADos sacADos = new SacADos(args[0], Float.parseFloat(args[1]));
            switch (args[2]) {
                case "gloutonne":
                    sacADos.resolutionGloutonne();
                    break;
                case "dynamique":
                    sacADos.resolutionDynamique2();
                    break;
                case "pse":
                    sacADos.resolutionPSE();
                    break;
            }
            System.out.println(sacADos);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " : Il doit y avoir 3 arguments => chemin(path du fichier txt)   poids-maximal(réel)   methode(gloutonne, dynamique ou pse)");
            System.exit(1);
        }
    }
}
