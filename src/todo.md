Choses à faire : 
* mettre les fichiers au propre
* ajouter l'authentification avec l'appel des controleurs 
* Gérer la session pour l'authentification
* Ajouter les try catch 
* Ajouter les explications de chaque fonction



Interface sort pour le tri !
findByLastNameContaigningIgnoredCase
Page

Validation coté serveur
@NotEmpty
@Valid
@inputResult


--- 

React = exprimé côté client => communication avec spring côté serveur 

Exemple = recup des listes de chats  
 
Utilisation de Node => react se connecte à spring par rest 
=> Controleur REST qui va chercher des infos dans la BDD 
=> la base renvoie des infos en java 
=> Spring va construire automatiquement un json à partir du jpa que la base renvoie 
=> manipulation du json sur réact pour renvoyer les infos en HTML renvoyées au client. 

Architecture WEBSERVICE != MVC 

Possible d'utiliser POSTMAN pour tester les requêtes http 

Sécurité => JWT => jeton (token) signé généré coté serveur => jeton généré au niveau de spring lors de l'authentification => renvoi à réact et stockage en local 
A chaque requete de react vers spring => header contenant le jetton pour vérifier si la requete est authorized ou non. 

