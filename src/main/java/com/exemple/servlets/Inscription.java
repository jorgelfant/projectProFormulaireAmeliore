package com.exemple.servlets;

import com.exemple.beans.Utilisateur;
import com.exemple.forms.InscriptionForm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Inscription extends HttpServlet {
    /*

    Après initialisation de notre objet métier, la seule chose que notre servlet effectue est un appel à la méthode
    inscrireUtilisateur() qui lui retourne alors un bean Utilisateur. Elle stocke finalement ces deux objets dans
    l'objet requête afin de rendre accessibles à la JSP les données validées et les messages d'erreur retournés.

    Dorénavant, notre servlet joue bien uniquement un rôle d'aiguilleur : elle contrôle les données, en se contentant
    d'appeler les traitements présents dans le modèle. Elle ne fait que transmettre la requête à un objet métier :
    à aucun moment elle n'agit directement sur ses données.

    *******************************************
    doGet() n'est pas doPost(), et vice-versa !
    *******************************************

    Avant de passer à la suite, je tiens à vous signaler une mauvaise pratique, malheureusement très courante sur le web.
    Dans énormément d'exemples de servlets, vous pourrez trouver ce genre de code :

        public void doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {

                           * Ici éventuellement des traitements divers, puis au lieu
                           * d'appeler tout simplement un forwarding...

                           doGet(request, response);
         }

    Vous comprenez ce qui a été réalisé dans cet exemple ? Puisque la méthode doGet() ne fait rien d'autre
    qu'appeler la vue, le développeur n'a rien trouvé de mieux que d'appeler doGet() depuis la méthode doPost()
    pour réaliser le forwarding vers la vue... Eh bien cette manière de faire, dans une application qui respecte MVC,
    est totalement dénuée de sens ! Si vous souhaitez que votre servlet réalise la même chose, quel que soit le type
    de la requête HTTP reçue, alors :

             * soit vous surchargez directement la méthode service() de la classe mère HttpServlet, afin qu'elle ne
               redirige plus les requêtes entrantes vers les différentes méthodes doXXX() de votre servlet. Vous n'aurez
               ainsi plus à implémenter doPost() et doGet() dans votre servlet, et pourrez directement implémenter un
               traitement unique dans la méthode service() ;

             * soit vous faites en sorte que vos méthodes doGet() et doPost() appellent une troisième et même méthode,
               qui effectuera un traitement commun à toutes les requêtes entrantes.

    Quel que soit votre choix parmi ces solutions, ce sera toujours mieux que d'écrire que doGet() appelle doPost(),
    ou vice-versa !
    */
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/WEB-INF/inscription.jsp";


    //------------------------------------------------------------------------------------------------------------------
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
    //------------------------------------------------------------------------------------------------------------------

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm();

        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur(request);

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_USER, utilisateur);

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
