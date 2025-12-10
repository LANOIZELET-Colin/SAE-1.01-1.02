import extensions.File;

/*placeholder pour les fichier => "Tour de <<NomJoueur>>"
remplacer(string s, string avant, string apres)
*/

class BipBoup extends Program{

    /*final Joueur McCree = new Joueur();
    final Joueur Cassidy = new Joueur();
    */

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
        
    

   /* boolean partieFinie(){
        return (Cassidy.HP < 0 || McCree.HP < 0);
    }
   
    
   Joueur newJoueur(int HP_max, int HP, boolean[] soin){
        Joueur j = new Joueur();
        j.HP_max = HP_max;
        j.HP = HP;
        j.soin = soin;
        return j;
    }
     */
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
            return true;
        } else if (lancer2 > lancer1){
            return false;
        } else {
            println("Egalité ! Vous avez tous les deux obtenus " + lancer2 + ", vous devez relancer le dé.");
            return ChoixDuPremierJoueur();
        }
    }

    void algorithm(){
        char choice;
        do {
            dump("menu.txt");
            choice = readChar();
            if (choice == '1'){
                dump("regles.txt");
                readString();
            } else if (choice == '2'){
                println("Tout d'abord, le joueur 1 incarnera McCree et le joueur 2 incarnera Cassidy.");
                println("Maintenant, on va lancer des dés pour déterminer qui commencera.");
                boolean McCreeTurn = ChoixDuPremierJoueur();   
            } else if (choice == '3'){
                System.exit(0);
            } else {
                println("choisissez une nombre entre 1 et 3");
                readChar();
            }
        } while (choice != '2');
        
    }
}
