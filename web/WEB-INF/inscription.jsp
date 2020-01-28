<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jorge.carrillo
  Date: 1/27/2020
  Time: 8:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Inscription</title>
    <link type="text/css" rel="stylesheet" href="form.css"/>
</head>
<body>
<%--
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                 Reprise de la JSP
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

La dernière étape de notre mise à niveau est la modification des appels aux différents attributs au sein de notre
page JSP. En effet, auparavant notre servlet transmettait directement la chaîne resultat et la Map erreurs à notre page,
ce qui impliquait que :

                   * nous accédions directement à ces attributs via nos expressions EL ;

                   * nous accédions aux données saisies par l’utilisateur via l'objet implicite param.

Maintenant, la servlet transmet le bean et l'objet métier à notre page, objets qui à leur tour contiennent les données
saisies, le résultat et les erreurs. Ainsi, nous allons devoir modifier nos expressions EL afin qu'elles accèdent
aux informations à travers nos deux nouveaux objets :

Les modifications apportées semblent donc mineures :

                   * l'accès aux erreurs et au résultat se fait à travers l'objet form ;

                   * l'accès aux données se fait à travers le bean utilisateur.

Mais en réalité, elles reflètent un changement fondamental dans le principe : notre JSP lit désormais directement
les données depuis le modèle !

Voici à la figure suivante un schéma de ce que nous avons réalisé.

------------------------------------------------------------------------------------------------------------------------

Nous avons ainsi avec succès mis en place une architecture MVC pour le traitement de notre formulaire :

              1) les données saisies et envoyées par le client arrivent à la méthode doPost() de la servlet ;

              2) celle-ci ordonne alors le contrôle des données reçues en appelant la méthode inscrireUtilisateur()
                 de l'objet métier InscriptionForm ;

              3) l'objet InscriptionForm effectue les traitements de validation de chacune des données reçues ;

              4) il les enregistre par la même occasion dans le bean Utilisateur ;

              5) la méthode doPost() récupère enfin les deux objets du modèle, et les transmet à la JSP via la portée
                 requête ;

              6) la JSP va piocher les données dont elle a besoin grâce aux différentes expressions EL mises en place,
                 qui lui donnent un accès direct aux objets du modèle transmis par la servlet ;

              7) pour finir, la JSP met à jour l'affichage du formulaire en se basant sur les nouvelles données.

* Il faut utiliser un bean pour stocker les données du formulaire.

* Il faut déplacer la validation et le traitement des données dans un objet métier.

* La servlet ne fait alors plus qu'aiguiller les données : contrôle > appels aux divers traitements > renvoi à la JSP.

* La méthode doGet() s'occupe des requêtes GET, la méthode doPost() des requêtes POST. Tout autre usage est fortement
  déconseillé.

* La page JSP accède dorénavant aux données directement à travers les objets du modèle mis en place, et non plus depuis
  la requête.

--%>

<form method="post" action="inscription">
    <fieldset>
        <legend>Inscription</legend>
        <p>Vous pouvez vous inscrire via ce formulaire.</p>

        <label for="email">Adresse email <span class="requis">*</span></label>
        <input type="email" id="email" name="email" value="<c:out value="${requestScope.utilisateur.email}"/>" size="20" maxlength="60" />
        <span class="erreur">${requestScope.form.erreurs['email']}</span>
        <br />

        <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
        <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
        <span class="erreur">${requestScope.form.erreurs['motdepasse']}</span>
        <br />

        <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
        <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
        <span class="erreur">${requestScope.form.erreurs['confirmation']}</span>
        <br />

        <label for="nom">Nom d'utilisateur</label>
        <input type="text" id="nom" name="nom" value="<c:out value="${requestScope.utilisateur.nom}"/>" size="20" maxlength="20" />
        <span class="erreur">${requestScope.form.erreurs['nom']}</span>
        <br />

        <input type="submit" value="Inscription" class="sansLabel" />
        <br />

        <p class="${empty requestScope.form.erreurs ? 'succes' : 'erreur'}">${requestScope.form.resultat}</p>
    </fieldset>
</form>

</body>
</html>
