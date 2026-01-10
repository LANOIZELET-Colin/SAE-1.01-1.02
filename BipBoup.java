import extensions.File;
import extensions.CSVFile;


/*problèmes à régler (écris ici pour pas qu'on oublie) : 

- corriger les questions reloue (genre où faut des accents)
- Rajouter des tests !!
- essayer de "compresser" le code
*/

class BipBoup extends Program{


    boolean TourMcCree;
    CSVFile QuestionsFile = loadCSV("questions.csv");
    boolean[] dejaPosee;

// Création et paramètrage des joueurs
   


    Joueur nouvJoueur(String nom, int PV_max, int PV, boolean[] soin){
        Joueur j = new Joueur();
        j.nom = nom;
        j.PV_max = PV_max;
        j.PV = PV;
        j.soin = soin;
        j.degats_totaux = 0;
        j.nb_bonne_reponse = 0;
        j.nb_tir_rate = 0;
        return j;
    }


// Fonction extension.File
   
    
    int nbLignes(String nomFichier){
        int lignes = 0;
        File file = newFile(nomFichier);
        while (ready(file)){
            lignes +=1;
            readLine(file);
        }
        return lignes;
    }

    void afficher(String file, Joueur j_actuel){
        File fichier = newFile(file);
        String ligne;
        for (int i=0; i<nbLignes(file); i++){
            ligne = (readLine(fichier));
            ligne = remplace(ligne, "{NOM}", j_actuel.nom);
            ligne = remplace(ligne, "{PV}", "" + j_actuel.PV);
            println(ligne);
        }
    }
    
    void afficherTableauDesScores(Joueur McCree, Joueur Cassidy){
        nettoyageTerminal();
        File fichier = newFile("TableauDesScore.txt");
        String ligne;
        for (int i=0; i<nbLignes("TableauDesScore.txt"); i++){
            ligne = (readLine(fichier));
            ligne = remplace(ligne, "{M.nb_b}", "" + McCree.nb_bonne_reponse);
            ligne = remplace(ligne, "{M.nb_t}", "" + McCree.nb_tir_rate);
            ligne = remplace(ligne, "{M.dg}", "" + McCree.degats_totaux);
            ligne = remplace(ligne, "{C.nb_b}", "" + Cassidy.nb_bonne_reponse);
            ligne = remplace(ligne, "{C.nb_t}", "" + Cassidy.nb_tir_rate);
            ligne = remplace(ligne, "{C.dg}", "" + Cassidy.degats_totaux);
            println(ligne);
        }
        readString();
    }

// Fonction qui permet de faire fonctionner les placeholder

    String remplace(String texte, String ancien, String nouveau){
        String resultat = "";
        int i = 0;
        while (i < length(texte)){
            if (i + length(ancien) <= length(texte)
                && equals(substring(texte, i, i + length(ancien)), ancien)) {
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



// Animations

    void animationRepos(String nom){
        nettoyageTerminal();
        println(nom + " s'endort...");
        sleep(600);
        println("Z");
        sleep(500);
        println("Z Z");
        sleep(500);
        println("Z Z Z");
        sleep(800);
    }

// Fonctions principales d'affichage de la partie
    
    // Choix de menu principal, où l'utilisateur choisit soit les règles, la partie ou la fin du programme.

    void ChoixMenuPrincipal(Joueur McCree){
            String choix;
        do {
                nettoyageTerminal();
                afficher("menu.txt", McCree);
                choix = readString();
                if (equals(choix,"1")){
                    nettoyageTerminal();   
                    afficher("regles.txt", McCree);               
                    readString();
                }else if (equals(choix,"2")){
                }else if (equals(choix,"3")){
                    System.exit(0);
                }else{
                println("Veuillez choisir un chiffre entre 1 et 3");
                sleep(1000);
                }
            } while (!equals(choix,"2"));
    }


    // Détermine qui est le joueur qui va jouer son tour


    void JoueurActuel(Joueur McCree, Joueur Cassidy){
        nettoyageTerminal();
        if(TourMcCree == true){
            if (McCree.sommeil){
                println("McCree se repose et passe son tour !");
                McCree.sommeil = false;
                sleep(2000);
            } else{
                menuJoueur(McCree, Cassidy);
            }
        }else{
            if (Cassidy.sommeil){
                println("Cassidy se repose et passe son tour !");
                Cassidy.sommeil = false;
                sleep(2000);
            } else{
                menuJoueur(Cassidy, McCree);
            }
        } 
    }


    // affiche le menu du joueur actuel, ses actions disponibles et ses points de vie


    void menuJoueur(Joueur j_actuel, Joueur j_autre){
        String choix;
        do {
            nettoyageTerminal();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
            afficher("menuTour.txt", j_actuel);

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


    // affiche le menu des soin depuis le menu du joueur


    void menuSoin(Joueur j_actuel, Joueur j_autre){
        boolean utilisé = false;
        String choix;
        
         do {
            utilisé = false;
            nettoyageTerminal();   
            afficher("MenuSoin.txt", j_actuel);
            choix = readString();
            if (equals(choix,"1")){
                if(j_actuel.soin[0] == true){
                    j_actuel.PV += 10;
                    if(j_actuel.PV > j_actuel.PV_max){
                        j_actuel.PV = 100;
                    }
                    j_actuel.soin[0] = false;
                    println("Vous prenez une bonne grosse gorgé de limon D.Va");
                    println("vous récupéré 10PV ("+j_actuel.PV+" restants)");
                    sleep(3000);
                }else{
                    println("vous en avez plus, dommage");
                    sleep(2000);
                    utilisé = true;
                }
            } else if (equals(choix,"2")){
                if(j_actuel.soin[1] == true){
                    j_actuel.PV += 30;
                    if(j_actuel.PV > j_actuel.PV_max){
                        j_actuel.PV = 100;
                    }
                    j_actuel.soin[1] = false;
                    println("Ange vous apprécie beaucoup (peut-être trop)");
                    println("vous récupérez 30PV ("+j_actuel.PV+" restants)");
                    sleep(3000);
                }else{
                    println("Ange a quitté la partie, courage");
                    sleep(2000);
                    utilisé = true;
                }
            } else if (equals(choix, "3")){
                if(j_actuel.soin[2] == true){
                    j_actuel.PV += 50;
                    if(j_actuel.PV > j_actuel.PV_max){
                        j_actuel.PV = 100;
                    }
                    j_actuel.soin[2] = false;
                    animationRepos(j_actuel.nom);
                    println("Vous vous endormez comme Ronflex. J'espère que vous pourrez vous réveiller sans l'aide de la pokeflute !");
                    println("vous récupérez 50PV, mais vous ne pourrez pas jouer au prochain tour. ("+j_actuel.PV+" restants)");
                    j_actuel.sommeil = true;
                    sleep(3000);
                }else{
                    println("Vous êtes insomniaque, impossible de vous reposer.");
                    sleep(2000);
                    utilisé = true;
                }
            } else if (equals(choix,"4")){
                menuJoueur(j_actuel, j_autre);
            } else {
                println("Veuillez choisir un chiffre entre 1 et 4");
                sleep(1000);
            }                
        } while ((!equals(choix,"1")) && (!equals(choix,"2")) && (!equals(choix,"3")) && (!equals(choix,"4")) || utilisé);
    }


    // Fonction qui permet de trier les questions et de poser la bonne question en fonction de la difficulté + qui calcule et inflige les dégats à l'autre joueur

    void poserQuestion(Joueur j_actuel, Joueur j_autre) {

        String choix;
        boolean questionTrouvee = false;

        while (!questionTrouvee) {

            do {
                nettoyageTerminal();
                afficher("menuQuestion.txt", j_actuel);
                choix = readString();
                if (!equals(choix,"1") && !equals(choix,"2") && !equals(choix,"3") && !equals(choix,"4")){ 
                    println("Choisissez un chiffre entre 1 et 4.");
                    sleep(1000);
                }
            } while (!equals(choix,"1") && !equals(choix,"2") && !equals(choix,"3") && !equals(choix,"4"));

            String difficulte = "";
            int degats = 0;

            if (equals(choix,"1")) { difficulte = "facile"; degats = 15; }
            else if (equals(choix,"2")) { difficulte = "moyen"; degats = 20; }
            else if (equals(choix,"3")) { difficulte = "difficile"; degats = 30; }
            else{ menuJoueur(j_actuel, j_autre);}

            int[] indices = new int[rowCount(QuestionsFile)];
            int nbQuestions = 0;

            for (int i = 0; i < rowCount(QuestionsFile); i++) {
                if (equals(getCell(QuestionsFile, i, 2), difficulte) && !dejaPosee[i]) {
                    indices[nbQuestions] = i;
                    nbQuestions++;
                }
            }

            if (nbQuestions == 0) {
                println("Il n'y a plus de questions disponibles pour cette difficulté, choisissez-en une autre !");
                sleep(3000);
            } else {
                int numQuestion = indices[random(0, nbQuestions - 1)];
                dejaPosee[numQuestion] = true;

                println("PRET ? APPUYEZ SUR ENTREE QUAND VOUS VOULEZ !");
                readString();
                println("GOOOOOOO !");
                println(getCell(QuestionsFile, numQuestion, 0));

                long debutCompt = getTime();
                String j_reponse = toLowerCase(readString());
                long finCompt = getTime();

                double tempsCompt = (finCompt - debutCompt) / 1000.0;

                if (equals(j_reponse, getCell(QuestionsFile, numQuestion, 1))) {
                    j_actuel.nb_bonne_reponse += 1;
                    degats -= (int) tempsCompt;
                    if (degats < 0) {
                        j_actuel.nb_tir_rate += 1;
                        degats = 0;                         
                        println("Vous avez mis trop de temps à répondre, votre tir est raté !");
                    } else{
                        println("Bravo, tu as mis " + tempsCompt + " secondes à répondre");
                        println("Tu infliges " + degats + " dégâts à ton adversaire !");
                    }
                    j_autre.PV -= degats;
                    j_actuel.degats_totaux += degats;
                } else {
                    j_actuel.nb_tir_rate += 1;
                    println("Oh non ! Ce n'était pas la bonne réponse !, votre tir est raté ");
                    println("Tu aurais du répondre : " + getCell(QuestionsFile, numQuestion, 1));
                }

                sleep(3000);
                questionTrouvee = true;
            }
        }
    }

   
// Permet de changer de joueur actuel

    void ChangementJoueur(){
        TourMcCree = !TourMcCree;
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


// Programme principale     

    void algorithm(){
        while(true){
            dejaPosee = new boolean[nbLignes("questions.csv")];
            Joueur McCree = nouvJoueur("McCree", 100, 100, new boolean[]{true, true, true});
            Joueur Cassidy = nouvJoueur("Cassidy", 100, 100, new boolean[]{true, true, true});
            ChoixMenuPrincipal(McCree);
            nettoyageTerminal();
            println("Tout d’abord, décidez vous qui incarnera McCree ou Cassidy (Pas de bagarre, ce n’est qu’un nom provisoire)");
            println("Maintenant, on va lancer des dés pour déterminer qui commencera.");
            TourMcCree = ChoixDuPremierJoueur();
            sleep(2000);

            while(!(Cassidy.PV <= 0 || McCree.PV <= 0)){
                nettoyageTerminal();
                JoueurActuel(McCree, Cassidy);
                ChangementJoueur();
            }
            
            println("La partie est terminée !!!!!");
            
            if(Cassidy.PV <= 0 ){
                println("Bravo McCree, tu as repris ta vrai place du King du FarWest. Le jeune part comme si il n'était jamais venu");
            }else{
                println("Bravo Cassidy, tu garde la place du King. L'ancien retourne dans sa tombe");
            }
                sleep(2000);
            
            afficherTableauDesScores(McCree, Cassidy);
        }             
    }
}