
import extensions.File;
import extensions.CSVFile;


/*placeholder pour les fichier => "Tour de <<NomJoueur>>"
remplacer(string s, string avant, string apres)
*/

class BipBoup extends Program{

   
// Création et paramètrage des joueurs

    Joueur newJoueur(String nom, int HP_max, int HP, boolean[] soin){
        Joueur j = new Joueur();
        j.nom = nom;
        j.HP_max = HP_max;
        j.HP = HP;
        j.soin = soin;
        return j;
    }


// Fonction extenstion.File
   
    
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


// Fonction extension.CSV
    
    
    
    CSVFile QuestionsFile = loadCSV("questions.csv");
   
    void print(CSVFile csv) {
        for (int line=0; line < rowCount(csv); line++) {
            for (int column=0; column < columnCount(csv, line); column++) {
                print(getCell(csv, line, column)+"|");
                }
            println();
            }
        } 

   /* 
   
    int questionTime(){
         print(QuestionsFile);
        println(columnCount(QuestionsFile));
        println(rowC);
    }
    */
    void questionTime(Joueur j_actuelle, Joueur j_autre){
        int damage = 20;
        int numQuestion = random(0,rowCount(QuestionsFile)-1);
        String question = getCell(QuestionsFile, numQuestion, 0);
        String answer = getCell(QuestionsFile, numQuestion, 1);
        println("PRET ?");
        sleep(3000);
        println("GOOOOOOO !");
        println(question);
        long débutCompt = getTime(); 
        String j_Answer = readString();
        long finCompt = getTime();
        double timeCompt = (finCompt - débutCompt)/(double)1000; 
        if(equals(j_Answer, answer)){
            damage = 20 - (int)timeCompt;
            j_autre.HP -= damage;
            println(damage);
            println("hp = "+j_autre.HP);
            println("Bravo, tu as mis "+ timeCompt +" secondes à répondre");
            println("Tu infliges "+ damage +" dégâts à ton adversaire !");

        }else{
            println("Bravo, tu as mis "+ timeCompt +" secondes à répondre de la merde");
            println("la réponse était : "+answer);
        }
        
    }

// Fonction principal de la partie
   
   
   
   
    boolean partieFinie(){
        return (Cassidy.HP <= 0 || McCree.HP <= 0);
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

    void menuJoueur(Joueur j_actuelle, joueurs j_actuelle){
        String choice;
        
        
        do {
            println("===== TOUR DE "+j_actuelle.nom+" =====");
            println("Actions disponibles :");
            println("1. Attaquer");
            println("2. Se soigner");
            println("HP : "+j_actuelle.HP);
            println("============================");

            choice = readString();
            if (equals(choice,"1")){
                questionTime(j_actuelle, j_autre);
                println("question time !!!");
            }else if (equals(choice,"2")){
                println("on se soigne !!!");
            }else{
            println("Veuillez choisir un chiffre entre 1 et 2");
            sleep(1000);
                }
        } while ( (!equals(choice,"2")) && (!equals(choice,"1"))  );
        
    }
    

    


// Programme principale     

    void algorithm(){
        Joueur McCree = newJoueur("McCree", 100, 100, new boolean[]{true, true});
        Joueur Cassidy = newJoueur("Cassidy", 100, 100, new boolean[]{true, true});
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

            println("Tout d’abord, décidez vous qui incarnera McCree ou Cassidy (Pas de bagarre, ce n’est qu’un nom provisoire)");
            println("Maintenant, on va lancer des dés pour déterminer qui commencera.");
            boolean McCreeTurn = ChoixDuPremierJoueur();
            if(McCreeTurn == true){
                menuJoueur(McCree, Cassidy);
            }else{
                menuJoueur(Cassidy);
            }
            questionTime(McCree, Cassidy);
            while(!partieFinie()){
            }
             
            


            
    }
}
