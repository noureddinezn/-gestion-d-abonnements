package main;
import dao.AbonnementDAO;
import dao.PaiementDAO;
import model

public class Main {
    public static void main(String[] args) {
        String version = System.getProperty("java.version");
        System.out.println("Version de Java exécutée : " + version);

        if (version.startsWith("1.8")) {
            System.out.println("-> Vous utilisez bien Java 8 !");
        } else {
            System.out.println("-> Attention : Vous n'utilisez pas Java 8.");
        }
    }
}
