import extensions.File;

/*placeholder pour les fichier => "Tour de <<NomJoueur>>"
remplacer(string s, string avant, string apres)
*/

class BipBoup extends Program{

// Création des joueurs
    final Joueur McCree = newJoueur("McCree", 100, 100, new boolean[]{true, true});
    final Joueur Cassidy = newJoueur("Cassidy", 100, 100, new boolean[]{true, true});

    Joueur newJoueur(String nom, int HP_max, int HP, boolean[] soin){
        Joueur j = new Joueur();
        j.nom = nom;
        j.HP_max = HP_max;
        j.HP = HP;
        j.soin = soin;
        return j;
    }

// endgroupe

    int nbLignes(String nomFichier){
        int lines = 0;
        File file = newFile(nomFichier);
        while (ready(file)){
            lines +=1;
            readLine(file);
        }
        return lines;
    }

    void dump(String file){
        File fichier = newFile(file);
        for (int i=0; i<nbLignes(file); i++){
            println(readLine(fichier));
        }
    }
    
    boolean partieFinie(){
        return (Cassidy.HP < 0 || McCree.HP < 0);
    }
     
    boolean ChoixDuPremierJoueur(){
        println("McCree, appuie sur entrée pour lancer le dé.");
        readString();
        int lancer1 = random(1,6);
        println("Vous avez obtenu un " + lancer1 + " !");
        println("Cassidy, c'est à ton tour d'appuyer sur entrée pour lancer le dé.");
        readString();
        int lancer2 = random(1,6);
        println("Vous avez obtenu un " + lancer2 + " !");
        if (lancer1 > lancer2){
            println("C'est donc à McCree de commencer");
            return true;
        } else if (lancer2 > lancer1){
            println("C'est donc à Cassidy de commencer");
            return false;
        } else {
            println("Egalité ! Vous avez tous les deux obtenus " + lancer2 + ", vous devez relancer le dé.");
            return ChoixDuPremierJoueur();
        }
    }

    void algorithm(){
        String choice;
        do {
            dump("menu.txt");

            choice = readString();
            if (equals(choice,"1")){
                dump("regles.txt");
                readString();
            }else if (equals(choice,"2")){
            }else if (equals(choice,"3")){
                System.exit(0);
            }else{
            println("Veuillez choisir un chiffre entre 1 et 3");
            sleep(1000);
                }
        } while (!equals(choice,"2"));

            println("Tout d'abord, le joueur 1 incarnera McCree et le joueur 2 incarnera Cassidy.");
            println("Maintenant, on va lancer des dés pour déterminer qui commencera.");
            boolean McCreeTurn = ChoixDuPremierJoueur();  
    }
}
