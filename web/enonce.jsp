<%--
  Created by IntelliJ IDEA.
  User: jorge.carrillo
  Date: 1/28/2020
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                         Formulaires : à la mode MVC
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Le modèle MVC est très clair sur ce point : c'est le modèle qui doit s'occuper de traiter les données. Le contrôleur
doit avoir pour unique but d'aiguiller les requêtes entrantes et d'appeler les éventuels traitements correspondants.
Nous devons donc nous pencher sur la conception que nous venons de mettre en place afin d'en identifier les défauts,
et de la rectifier dans le but de suivre les recommandations MVC.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                         Analyse de notre conception
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

La base que nous avons réalisée souffre de plusieurs maux :

    * la récupération et le traitement des données sont effectués directement au sein de la servlet. Or nous savons que
      d'après MVC, la servlet est un contrôleur, et n'est donc pas censée intervenir directement sur les données, elle
      doit uniquement aiguiller les requêtes entrantes vers les traitements correspondants ;

    * aucun modèle (bean ou objet métier) n'intervient dans le système mis en place ! Pourtant, nous savons que d'après MVC,
      les données sont représentées dans le modèle par des objets...

Voici à la figure suivante le schéma représentant ce à quoi nous souhaitons parvenir.

Nous allons donc reprendre notre système d'inscription pour y mettre en place un modèle :

   1) création d'un bean qui enregistre les données saisies et validées ;

   2) création d'un objet métier comportant les méthodes de récupération/conversion/validation des contenus des champs
      du formulaire ;

   3) modification de la servlet pour qu'elle n'intervienne plus directement sur les données de la requête, mais
      aiguille simplement la requête entrante ;

   4) modification de la JSP pour qu'elle s'adapte au modèle fraîchement créé.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                          Creation du modele
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

L'utilisateur
*************

Pour représenter un utilisateur dans notre modèle, nous allons naturellement créer un bean nommé Utilisateur et placé
dans le package com.sdzee.beans, contenant trois champs de type String : email, motDePasse et nom. Si vous ne vous
souvenez plus des règles à respecter lors de la création d'un bean, n'hésitez pas à relire le chapitre qui y est dédié.
Voici le résultat attendu :

C'est tout ce dont nous avons besoin pour représenter les données d'un utilisateur dans notre application.

Dans notre formulaire, il y a un quatrième champ : la confirmation du mot de passe. Pourquoi ne stockons-nous pas cette
information dans notre bean ?

Tout simplement parce que ce bean ne représente pas le formulaire, il représente un utilisateur. Un utilisateur final
possède un mot de passe et point barre : la confirmation est une information temporaire propre à l'étape d'inscription
uniquement ; il n'y a par conséquent aucun intérêt à la stocker dans le modèle.


Le formulaire
*************

Maintenant, il nous faut créer dans notre modèle un objet "métier", c'est-à-dire un objet chargé de traiter les
données envoyées par le client via le formulaire. Dans notre cas, cet objet va contenir :

         1) les constantes identifiant les champs du formulaire ;

         2) la chaîne resultat et la Map erreurs que nous avions mises en place dans la servlet ;

         3) la logique de validation que nous avions utilisée dans la méthode doPost() de la servlet ;

         4) les trois méthodes de validation que nous avions créées dans la servlet.

Nous allons donc déporter la majorité du code que nous avions écrit dans notre servlet dans cet objet métier, en
l'adaptant afin de le faire interagir avec notre bean fraîchement créé.




--%>

</body>
</html>
