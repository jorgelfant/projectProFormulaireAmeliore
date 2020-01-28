package com.exemple.forms;

import com.exemple.beans.Utilisateur;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class InscriptionForm {
    /*------------------------------------------------------------------------------------------------------------------
                       1. Pour commencer, nous allons nommer ce nouvel objet InscriptionForm, le placer dans un nouveau
                          package com.sdzee.forms, et y inclure les constantes dont nous allons avoir besoin :
   ---------------------------------------------------------------------------------------------------------------------
   ---------------------------------------------------------------------------------------------------------------------
                       2. Nous devons ensuite y ajouter la chaîne resultat et la Map erreurs :
   -------------------------------------------------------------------------------------------------------------------*/

    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "motdepasse";
    private static final String CHAMP_CONF = "confirmation";
    private static final String CHAMP_NOM = "nom";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    //------------------------------------------------------------------------------------------------------------------
    //              3) Nous ajoutons alors la méthode principale, contenant la logique de validation :
    //------------------------------------------------------------------------------------------------------------------
    public Utilisateur inscrireUtilisateur(HttpServletRequest request) {
        String email = getValeurChamp(request, CHAMP_EMAIL);
        String motDePasse = getValeurChamp(request, CHAMP_PASS);
        String confirmation = getValeurChamp(request, CHAMP_CONF);
        String nom = getValeurChamp(request, CHAMP_NOM);

        Utilisateur utilisateur = new Utilisateur();

        try {
            validationEmail(email);
        } catch (Exception e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }
        utilisateur.setEmail(email);
        //--------------------------------------------------------------------------------------------------------------

        try {
            validationMotsDePasse(motDePasse, confirmation);
        } catch (Exception e) {
            setErreur(CHAMP_PASS, e.getMessage());
            setErreur(CHAMP_CONF, null);
        }
        utilisateur.setMotDePasse(motDePasse);
        //--------------------------------------------------------------------------------------------------------------
        try {
            validationNom(nom);
        } catch (Exception e) {
            setErreur(CHAMP_NOM, e.getMessage());
        }
        utilisateur.setNom(nom);
        //--------------------------------------------------------------------------------------------------------------

        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }
        return utilisateur;
    }
    //------------------------------------------------------------------------------------------------------------------
    //                4) Pour terminer, nous mettons en place les méthodes de validation et les deux méthodes
    //                   utilitaires nécessaires au bon fonctionnement de la logique que nous venons d'écrire :
    //------------------------------------------------------------------------------------------------------------------

    private void validationEmail(String email) throws Exception {
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new Exception("Merci de saisir une adresse mail valide.");
            }
        } else {
            throw new Exception("Merci de saisir une adresse mail.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void validationMotsDePasse(String motDePasse, String confirmation) throws Exception {
        if (motDePasse != null && confirmation != null) {
            if (!motDePasse.equals(confirmation)) {
                throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if (motDePasse.length() < 3) {
                throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
            }
        } else {
            throw new Exception("Merci de saisir et confirmer votre mot de passe.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void validationNom(String nom) throws Exception {
        if (nom != null && nom.length() < 3) {
            throw new Exception("Le nom d'utilisateur doit contenir au moins 3 caractères.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //                Ajoute un message correspondant au champ spécifié à la map des erreurs.
    //------------------------------------------------------------------------------------------------------------------
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

    //------------------------------------------------------------------------------------------------------------------
    //           Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon.
    //------------------------------------------------------------------------------------------------------------------

    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}
/*
Encore une fois, prenez bien le temps d'analyser les ajouts qui ont été effectués. Vous remarquerez qu'au final,
il y a très peu de changements :

    * ajout de getters publics pour les attributs privés resultat et erreurs, afin de les rendre accessibles depuis
      notre JSP via des expressions EL ;

    * la logique de validation a été regroupée dans une méthode inscrireUtilisateur(), qui retourne un bean Utilisateur ;

    * la méthode utilitaire getValeurChamp() se charge désormais de vérifier si le contenu d'un champ est vide ou non,
      ce qui nous permet aux lignes 4, 14 et 26 du dernier code de ne plus avoir à effectuer la vérification sur la
      longueur des chaînes, et de simplement vérifier si elles sont à null ;

    * dans les blocs catch du troisième code, englobant la validation de chaque champ du formulaire, nous utilisons
      désormais une méthode setErreur() qui se charge de mettre à jour la Maperreurs en cas d'envoi d'une exception ;

    * toujours dans le troisième code, après la validation de chaque champ du formulaire, nous procédons dorénavant
      à l'initialisation de la propriété correspondante dans le bean Utilisateur, peu importe le résultat de la
      validation (lignes 16, 24 et 31).

Voilà tout ce qu'il est nécessaire de mettre en place dans notre modèle. Prochaine étape : il nous faut nettoyer
notre servlet !

Le découpage en méthodes via setErreur() et getValeurChamp() n'est pas une obligation, mais puisque nous avons déplacé
notre code dans un objet métier, autant en profiter pour coder un peu plus proprement. :)





*/
