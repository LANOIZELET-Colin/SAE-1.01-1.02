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

// Fonction qui permet de nettoyer l'affichage du terminal

    void nettoyageTerminal(){
        print("\033[H\033[2J");
    }


// Fonction extension.CSV

    
    
    CSVFile QuestionsFile = loadCSV("questions.csv");
    

// Fonction principal de la partie
   
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
        String j_Answer = toLowerCase(readString());
        long finCompt = getTime();
        double timeCompt = (finCompt - débutCompt)/(double)1000; 
        if(equals(j_Answer, answer)){
            damage = 20 - (int)timeCompt;
            j_autre.HP -= damage;
            println("Bravo, tu as mis "+ timeCompt +" secondes à répondre");
            println("Tu infliges "+ damage +" dégâts à ton adversaire !");
        }else{
            println("Oh non ! Ce n'était pas la bonne réponse ! \nTu aurais du répondre : " + answer);
        }
        sleep(5000);
        
    }
   

    // détermine quel joueur commence en fonction de la valeur des dés
   

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


    // affiche le menu des soin depuis le menu du joueur


    void menuSoin(Joueur j_actuelle, Joueur j_autre){

        String choice;
        
         do {
            nettoyageTerminal();;   
            println("===== TOUR DE "+j_actuelle.nom+" ====="); // appeler un fichier pour le menu mais sans forcément cette ligne
            println("Chaque soin est utilisable qu’une seule fois");
            println("1 : Limon D.Va (10 HP)");
            println("2 : La pocket-Mercy (30 HP)");
            println("3 : annuler");
            println("============================");

            choice = readString();
            if (equals(choice,"1")){
                if(j_actuelle.soin[0] == true){
                    j_actuelle.HP += 10;
                    if(j_actuelle.HP > j_actuelle.HP_max){
                        j_actuelle.HP = 100;
                    }
                    j_actuelle.soin[0] = false;
                    println("Vous prenez une bonne grosse gorgé de limon D.Va");
                    println("vous récupéré 10HP ("+j_actuelle.HP+" restants)");
                    sleep(3000);
                }else{
                    println("vous en avez plus, dommage");
                    sleep(2000);
                    menuSoin(j_actuelle,j_autre);
                }
            }else if (equals(choice,"2")){
                if(j_actuelle.soin[1] == true){
                    j_actuelle.HP += 30;
                    if(j_actuelle.HP > j_actuelle.HP_max){
                        j_actuelle.HP = 100;
                    }
                    j_actuelle.soin[1] = false;
                    println("La ange vous apprécie beaucoup (peut-être trop)");
                    println("vous récupéré 30HP ("+j_actuelle.HP+" restants)");
                    sleep(3000);
                }else{
                    println("la ange a quitté la partie, courage");
                    sleep(2000);
                    menuSoin(j_actuelle,j_autre);
                }
            }else if (equals(choice,"3")){
                menuJoueur(j_actuelle, j_autre);
            }else{
                println("Veuillez choisir un chiffre entre 1 et 2");
                sleep(1000);
                }
        } while ( (!equals(choice,"1")) && (!equals(choice,"2")) && (!equals(choice,"3")) );

    }

    // affiche le menu du joueur actuel


    void menuJoueur(Joueur j_actuelle, Joueur j_autre){
        String choice;
        
        
        do {
            nettoyageTerminal();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
            println("===== TOUR DE "+j_actuelle.nom+" ====="); // à mettre dans un fichier, dans forcément cette ligne
            println("Actions disponibles :");
            println("1. Attaquer");
            println("2. Se soigner");
            println("HP : "+j_actuelle.HP);
            println("============================");

            choice = readString();
            if (equals(choice,"1")){
                questionTime(j_actuelle, j_autre);
            }else if (equals(choice,"2")){
                menuSoin(j_actuelle, j_autre);
            }else{
            println("Veuillez choisir un chiffre entre 1 et 2");
            sleep(1000);
                }
        } while ( (!equals(choice,"2")) && (!equals(choice,"1"))  );
        
    }
    

    


// Programme principale     

    void algorithm(){
        while(true){
            Joueur McCree = newJoueur("McCree", 100, 100, new boolean[]{true, true});
            Joueur Cassidy = newJoueur("Cassidy", 100, 100, new boolean[]{true, true});
            String choice;
            do {
                nettoyageTerminal();
                dump("menu.txt");

                choice = readString();
                if (equals(choice,"1")){
                    print("\033[H\033[2J");   
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
            sleep(3000);            
            while(!(Cassidy.HP <= 0 || McCree.HP <= 0)){
                nettoyageTerminal();
                if(McCreeTurn == true){
                    println("C'est au tour de McCree");
                    menuJoueur(McCree, Cassidy);
                }else{
                    println("C'est au tour de Cassidy");
                    menuJoueur(Cassidy, McCree);
                } 
                McCreeTurn = !McCreeTurn;
            }
            
            println("partie finie !!!!!");
            
            if(Cassidy.HP <= 0 ){
                println("Bravo McCree, tu as repris ta vrai place du King du FarWest. Le jeune part comme si il n'était jamais venu");
            }else{
                println("Bravo Cassidy, tu garde la place du King. L'ancien retourne dans sa tombe");
            }
            sleep(7000);
        }
             
            


            
    }
}
