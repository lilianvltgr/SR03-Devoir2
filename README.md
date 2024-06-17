# Projet Chat-App

## Backend
Implémentation en Java avec Springboot.
Gestion d'un ensemble d'utilisateurs et de chats. Gestion de leur ajout, modification, suppression et sauvegarde en base de données mySQL.

// mettre identifiants

![uml-back](https://github.com/lilianvltgr/SR03-Devoir2/assets/105502477/a4b2994d-077f-4962-836f-256b88f82152)
UML de la gestion du backend.

## Frontend 

### MUI 

### React et l'option des fonctions 
* UseState 
* Useeffect

### CSS 
Des idées de conception et un style qui aurait pu aller plus loin 




## Reste à implémenter :
Ces fonctionnalités demandées ou non dans le sujet auraient été un plus pour l'utilisation et la gestion du projet. 
### Nombre de tentatives de connexion et blocage du compte : 

Pour fixer un nombre de tentatives, il aurait fallu ajouter un compteur pour le nombre de tentatives avec la même adresse mail. Au bout d'un nombre fixé, un message aurait remplacé le "mauvais mot de passe" pour afficher "Compte temporairement désactivé". Cette désactivation aurait pu être un champ dateTime de la base de données qui aurait donné une date de fin de désactivation. Lorsque cette date aura été dépassée, la personne pourra à nouveau se connecter. 

### Passage de l'interface admin aux chats 

Nous avons essayé de nombreuses manières de rester authentifié lors du passage entre les deux parties du projet: 
* HttpServletRequest
* WebRequest
* Session
* Passage des informations en argument de requête

Ces methodes ne fonctionnaient pas et généraient toujours des problèmes. En effet, les httpServletRequest et webRequest ne conservaient pas leurs attributs entre les deux controlleurs, se réinitialisant, comme si une nouvelle session s'ouvrait. Le passage d'arguments pas l'url, utilisé auparavant semblait créer une faille de sécurité. 
Nous sommes donc toujours à la recherche d'une solution pour éviter la reconnexion entre chaque passage d'admin à chat ! 

### Utilisation des token 
Une faille de sécurité se situe dans notre application. En effet, aprés authentification, l'id de l'utilisateur ainsi que la date de fin de validité sont stockés dans la session côté front. Ils sont utilisés pour vérifier la validité de la session et de la connexion. Toutefois, tout le monde pourrait créer un cookie avec ces attributs et ainsi se connecter à l'application. C'est un vol de session ! 
Pour fermer cette faille, il aurait été possible de faire chiffrer ces informations par le serveur. L'avantage de notre methode est la facilité qu'elle a apportée au développement des fonctionnalités, mais elle génère un problème qui aurait dû être corrigé.
Le client aurait communiqué son information chiffrée par le serveur et celui-ci l'aurait reconnu comme étant une information chiffrée par lui. Le contôle s'effectuant dans le back, la sécurité est garantie.

### Hachage du mot de passe

Encore une fois relié à la sécurité de l'application, le mot de passe n'est pas haché. Il aurait pu l'être pour améliorer sa difficulté de récupération par une personne tierce. 