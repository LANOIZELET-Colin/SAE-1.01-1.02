## Présentation de BipBoup ##

BipBoup est un jeu à but éducatif, tour par tour pour deux joueurs où le but est d’éliminer votre adversaire à l’aide d’un pistolet.
Pour tirer, vous devez répondre correctement à des questions basés sur plusieurs matières différentes.
Au début de la partie, chaque joueur commence avec 100 points de vie.

Des captures d'écran illustrant le fonctionnement du logiciel sont proposées dans le répertoire shots

## Comment utiliser BipBoup ?

Afin d'utiliser le logiciel, ijava doit être installé sur votre machine.
il doit être suffisant de taper les commandes suivantes depuis le répertoire src dans votre terminal:

ijava compile BipBoup.java
//compilation des fichiers présents dans 'src' et création des fichiers '.class' dans 'classes'

ijava execute BipBoup
//lancement du jeu

## Vous voulez modifier à votre sauce BipBoup ?

Vous pouvez ajouter/modifier/supprimer des questions à votre guise en respecter la syntaxe dans le fichier "questions.csv" dans le dossier "ressources"

Syntaxe: Question,Réponse(en un mot),difficulté(facile,moyen ou difficile)

Exemple: Quel est le symbole chimique de l'oxygène ?,o,moyen
