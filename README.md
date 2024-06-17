# Projet Chat-App
Voltigeur Lilian et Chesnay Zoé
### Identifiants
sr03p002 bHy2L69nZuwL

## Backend
Implémentation en Java avec Springboot.
Gestion d'un ensemble d'utilisateurs et de chats. Gestion de leur ajout, modification, suppression et sauvegarde en base de données mySQL.
Le back contient deux controlleurs : 
* ``AdminController`` gère toute la partie admin, communique indirectement avec la bdd et redirige vers les bonnes vues thymeleaf. 
* ``UserController`` gère les requêtes du client (correspondant à l'application de chat). 

Le back contient la gestion des sockets pour la communication reliée aux chats, certains scripts utilisés dans la partie admin ainsi que les templates permettant de créer l'interface admin.
UML de la gestion du backend.
![schemas.png](images%2Fschemas.png)

### Thymeleaf
Thymeleaf permet d'effectuer des vues dynamiques qui utilisent le modèle pour afficher des informations issues du controlleur. 
Nous l'avons notamment utilisé pour afficher les informations d'un formulaire, afficher des informations relatives aux précédentes actions (comme la validation de l'ajout d'un utilisateur) ou récupérer un fragment thymeleaf issu d'un autre fichier. Nous avons aussi effectué des affichages conditionnels.
## Frontend 

### MUI 
Le framework MUI a été utilisé et a grandement simplifié la disposition et l'affichage des balises grâce aux balises prédéfinies évitant une utilisation trop importante du css pour le style des éléments.

### React et l'option des fonctions 
Réact a été utilisé dans sa forme "fonction" c'est-à-dire qu'il n'y a pas de classes créées dans le code, uniquement des fonctions retournant du jsx, permettant d'afficher des éléments HTML.
Cette méthode a été choisie car elle a été plus simple à assimiler pour Lilian qui n'avait jamais codé en React.
C'est pourquoi le code contient des hooks utilisant UseState et UseEffect. 

### CSS 
Le CSS a été utilisé pour placer les composant sur la page. Le style de l'application est trés rudimentaire, (surtout la partie admin). C'est par manque de temps que nous avons décidé de sacrifier cette partie. 
Des idées de conceptions en sont toutefois ressorties. L'idée était d'avoir une couleur pour l'admin (violet) et une couleur pour les chats (bleu). L'utilisation de MUI a cependant permis d'obtenir une interface propre et minimaliste pour la partie chat. 
Le css a aussi servi à designer le format des chats et les différents composants de l'application.

## Autres possibles implémentations :
Ces fonctionnalités demandées ou non dans le sujet auraient été un plus pour l'utilisation et la gestion du projet. 
### Nombre de tentatives de connexion et blocage du compte : 

Pour fixer un nombre de tentatives, il aurait fallu ajouter un compteur pour le nombre de tentatives avec la même adresse mail. Au bout d'un nombre fixé, un message aurait remplacé le "mauvais mot de passe" pour afficher "Compte temporairement désactivé". Cette désactivation aurait pu être un champ dateTime de la base de données qui aurait donné une date de fin de désactivation. Lorsque cette date aura été dépassée, la personne pourra à nouveau se connecter. 

### Passage de l'interface admin aux chats 

Nous avons essayé de nombreuses manières de rester authentifié lors du passage entre les deux parties du projet: 
* `HttpServletRequest`
* `WebRequest`
* `Session`
* Passage des informations en argument de requête

Ces méthodes ne fonctionnaient pas et généraient toujours des problèmes. En effet, les ``httpServletRequest`` et ``webRequest`` ne conservaient pas leurs attributs entre les deux controlleurs, se réinitialisant, comme si une nouvelle session s'ouvrait. Le passage d'arguments pas l'url, utilisé auparavant semblait créer une faille de sécurité. 
Nous sommes donc toujours à la recherche d'une solution pour éviter la reconnexion entre chaque passage d'admin à chat ! 

### Utilisation des token 
Une faille de sécurité se situe dans notre application. En effet, aprés authentification, l'id de l'utilisateur ainsi que la date de fin de validité sont stockés dans la session côté front. Ils sont utilisés pour vérifier la validité de la session et de la connexion. Toutefois, tout le monde pourrait créer un cookie avec ces attributs et ainsi se connecter à l'application. C'est un vol de session ! 
Pour fermer cette faille, il aurait été possible de faire chiffrer ces informations par le serveur. L'avantage de notre methode est la facilité qu'elle a apportée au développement des fonctionnalités, mais elle génère un problème qui aurait dû être corrigé.
Le client aurait communiqué son information chiffrée par le serveur et celui-ci l'aurait reconnu comme étant une information chiffrée par lui. Le contôle s'effectuant dans le back, la sécurité est garantie.

### Hachage du mot de passe

Encore une fois relié à la sécurité de l'application, le mot de passe n'est pas haché. Il aurait pu l'être pour améliorer sa difficulté de récupération par une personne tierce. 

## GreenIt
### Page d'accueil sans chat 
![OnlyHomePage.png](images%2FOnlyHomePage.png)

### Programmation d'un chat![ScheduleChat.png](images%2FScheduleChat.png)

### Ajouter un utilisateur (admin interface)![AddUserThymleaf.png](images%2FAddUserThymleaf.png)

### Page contenant des chats ![Chats.png](images%2FChats.png)

Merci pour la lecture ! 
Voltigeur Lilian et Chesnay Zoé pour SR03.

...
|  ^_^  |
...