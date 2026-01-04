import extensions.File;
import extensions.CSVFile;


/*placeholder pour les fichier => "Tour de <<NomJoueur>>"
remplacer(string s, string avant, string apres)
*/

class BipBoup extends Program{


// Création et paramètrage des joueurs
   


    Joueur nouvJoueur(String nom, int HP_max, int HP, boolean[] soin){
        Joueur j = new Joueur();
        j.nom = nom;
        j.HP_max = HP_max;
        j.HP = HP;
        j.soin = soin;
        return j;
    }


// Fonction extenstion.File
   
    
    int nbLignes(String nomFichier){
        int lignes = 0;
        File file = newFile(nomFichier);
        while (ready(file)){
            lignes +=1;
            readLine(file);
        }
        return lignes;
    }

    void afficher(String file){
        File fichier = newFile(file);
        String ligne;
        for (int i=0; i<nbLignes(file); i++){
            ligne = (readLine(fichier));
            ligne = remplace(ligne, "{NOM}", j_actuel.nom);
            ligne = remplace(ligne, "{HP}", "" + j_actuel.HP);
            println(ligne);
        }
    }

// Fonction qui permet de faire fonctionner les placeholder

    String remplace(String texte, String ancien, String nouveau){
        String resultat = "";
        int i = 0;
        while (i < length(texte)){
            if (i + length(ancien) <= length(texte) && substring(texte, i, i + length(ancien)) == ancien){
                resultat = resultat + nouveau;
                i = i + length(ancien);
            }
            else{
                resultat = resultat + charAt(texte, i);
                i = i + 1;
            }
        }
        return resultat;
    }


// Fonction qui permet de nettoyer l'affichage du terminal

    void nettoyageTerminal(){
        print("\033[H\033[2J");
    }


// Fonction extension.CSV

    
    
    CSVFile QuestionsFile = loadCSV("questions.csv");
    

// Fonction principal de la partie
   
    void poserQuestion(Joueur j_actuel, Joueur j_autre){
        int choix;
        int degats;

        do {
            afficher("menuQuestion");
            choix = readInt()
        } while {choix < 1 && choix > 3}

        String difficulte = "";
        if (equals(choix, "1")) {difficulte = "facile"; degats = 10;}
        if (equals(choix, "2")) {difficulte = "moyen"; degats = 20;}
        if (equals(choix, "3")) {difficulte = "difficile"; degats = 30;}

        int[] indices = new int[rowCount(QuestionsFile)];
        int nbQuestions = 0;
        for (int i = 0; i < rowCount(QuestionsFile); i++){
            if (equals(getCell(QuestionsFile, i, 2), difficulte) && !dejaPosee[i]){
                indices[nbQuestions] = i;
                nbQuestions++;
            }
        }
        
        if (nbQuestions == 0){
            println("Il n'y a plus de questions disponibles pour cette difficulté !");
            return poserQuestion(j_actuel, j_autre);
        }

        int numQuestion = random(0,nbQuestions-1);
        dejaPosee[numQuestion] = true;
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


    void menuSoin(Joueur j_actuel, Joueur j_autre){

        String choix;
        
         do {
            nettoyageTerminal();   
            afficher("MenuSoin", j_actuel);
            choix = readString();
            if (equals(choix,"1")){
                if(j_actuel.soin[0] == true){
                    j_actuel.HP += 10;
                    if(j_actuel.HP > j_actuel.HP_max){
                        j_actuel.HP = 100;
                    }
                    j_actuel.soin[0] = false;
                    println("Vous prenez une bonne grosse gorgé de limon D.Va");
                    println("vous récupéré 10HP ("+j_ACTUEL.HP+" restants)");
                    sleep(3000);
                }else{
                    println("vous en avez plus, dommage");
                    sleep(2000);
                    menuSoin(j_ACTUEL,j_autre);
                }
            } else if (equals(choix,"2")){
                if(j_actuel.soin[1] == true){
                    j_actuel.HP += 30;
                    if(j_actuel.HP > j_actuel.HP_max){
                        j_actuel.HP = 100;
                    }
                    j_actuel.soin[1] = false;
                    println("La ange vous apprécie beaucoup (peut-être trop)");
                    println("vous récupéré 30HP ("+j_actuel.HP+" restants)");
                    sleep(3000);
                }else{
                    println("la ange a quitté la partie, courage");
                    sleep(2000);
                    menuSoin(j_actuel,j_autre);
                }
            } else if (equals(choix, "3")){
                if(j_actuel.soin[2] == true){
                    j_actuel.HP += 50;
                    if(j_actuel.HP > j_actuel.HP_max){
                        j_actuel.HP = 100;
                    }
                    j_actuel.soin[2] = false;
                    println("Vous vous endormez comme Ronflex. J'espère que vous pourrez vous réveiller sans l'aide de la pokeflute !");
                    println("vous récupéré 50HP, mais vous ne pourrez pas jouer au prochain tour. ("+j_actuel.HP+" restants)");

                    sleep(3000);
                }else{
                    println("Vous êtes insomniaque, impossible de vous reposer.");
                    sleep(2000);
                    menuSoin(j_actuel,j_autre);
                }
                McCreeTurn = !McCreeTurn;
            }
            else if (equals(choix,"4")){
                menuJoueur(j_actuel, j_autre);
            }else{
                println("Veuillez choisir un chiffre entre 1 et 2");
                sleep(1000);
                }
        } while ( (!equals(choix,"1")) && (!equals(choix,"2")) && (!equals(choix,"3")) && (!equals(choix,"4")) );

    }

    // affiche le menu du joueur actuel


    void menuJoueur(Joueur j_actuel, Joueur j_autre){
        String choix;
        
        
        do {
            nettoyageTerminal();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
            afficher("menuTour")

            choix = readString();
            if (equals(choix,"1")){
                poserQuestion(j_actuel, j_autre);
            }else if (equals(choix,"2")){
                menuSoin(j_actuel, j_autre);
            }else{
            println("Veuillez choisir un chiffre entre 1 et 2");
            sleep(1000);
                }
        } while ( (!equals(choix,"2")) && (!equals(choix,"1"))  );
        
    }
    

    


// Programme principale     

    void algorithm(){
        boolean[] dejaPosee = new boolean[rowCount(QuestionsFile)];
        while(true){
            Joueur McCree = nouvJoueur("McCree", 100, 100, new boolean[]{true, true});
            Joueur Cassidy = nouvJoueur("Cassidy", 100, 100, new boolean[]{true, true});
            String choix;
            do {
                nettoyageTerminal();
                afficher("menu.txt");

                choix = readString();
                if (equals(choix,"1")){
                    print("\033[H\033[2J");   
                    afficher("regles.txt");               
                    readString();
                }else if (equals(choix,"2")){
                }else if (equals(choix,"3")){
                    System.exit(0);
                }else{
                println("Veuillez choisir un chiffre entre 1 et 3");
                sleep(1000);
                    }
            } while (!equals(choix,"2"));

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
