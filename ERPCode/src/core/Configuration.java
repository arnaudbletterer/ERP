package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Configuration {


    private static Configuration instance;

    private ArrayList<Integer> taches;

    private int nbBoulonsBobine = 1000;

    private int prixBobine = 10000;

    private int travailHeureJour = 7;

    private int travailJourSemaine = 5;

    private int stockMinBobine = 1;

    private int stockMaxBobine = 2;

    private int enCoursBobine = 1;

    private int tempsLivraisonBobine = 10;

    private double augmentationPrixAcierMois = 2.0;

    private int coutUsineHeure = 1000;

    private double augmentationQuantiteCommande = 10.0;

    private double margeSouhaite = 70.0;

    private ArrayList<Echeance> echeances;

    private Configuration () {
        this.taches = new ArrayList<>();
    }

    public static Configuration getInstance () 
    {
        if(Configuration.instance == null)
            Configuration.instance = new Configuration();
        
        return instance;
    }

    public double getAugmentationPrixAcierMois () {
        return augmentationPrixAcierMois;
    }

    public void setAugmentationPrixAcierMois (double val) {
        this.augmentationPrixAcierMois = val;
    }

    public double getAugmentationQuantiteCommande () {
        return augmentationQuantiteCommande;
    }

    public void setAugmentationQuantiteCommande (double val) {
        this.augmentationQuantiteCommande = val;
    }

    public int getCoutUsineHeure () {
        return coutUsineHeure;
    }

    public void setCoutUsineHeure (int val) {
        this.coutUsineHeure = val;
    }

    public ArrayList<Echeance> getEcheances () {
        return echeances;
    }

    public void setEcheances (ArrayList<Echeance> val) {
        this.echeances = val;
    }

    public int getEnCoursBobine () {
        return enCoursBobine;
    }

    public void setEnCoursBobine (int val) {
        this.enCoursBobine = val;
    }

    public void setInstance (Configuration val) {
        this.instance = val;
    }

    public double getMargeSouhaite () {
        return margeSouhaite;
    }

    public void setMargeSouhaite (double val) {
        this.margeSouhaite = val;
    }

    public int getNbBoulonsBobine () {
        return nbBoulonsBobine;
    }

    public void setNbBoulonsBobine (int val) {
        this.nbBoulonsBobine = val;
    }

    public int getPrixBobine () {
        return prixBobine;
    }

    public void setPrixBobine (int val) {
        this.prixBobine = val;
    }

    public int getStockMaxBobine () {
        return stockMaxBobine;
    }

    public void setStockMaxBobine (int val) {
        this.stockMaxBobine = val;
    }

    public int getStockMinBobine () {
        return stockMinBobine;
    }

    public void setStockMinBobine (int val) {
        this.stockMinBobine = val;
    }

    public ArrayList<Integer> getTaches () {
        return taches;
    }

    public void setTaches (ArrayList<Integer> val) {
        this.taches = val;
    }

    /*
    * Pour calculer le temps maximum on regarde quelle est la tâche critique
    * Et on ajoute le temps unitaire de production de toutes les autres tâches
    * au temps total de production de la tâche critique
    * 
    * @return 
    */
    public double getTempsConstruction () {
        double res = -1.0;
        
        double max = (double)this.taches.get(0), index_max = 0;
        
        
        for(int i=1; i< this.taches.size(); ++i) {
            //Recherche de la tâche ayant la durée maximum
            if(this.taches.get(i)>max) {
                max = (double) this.taches.get(i);
                index_max = i;
            }
        }
        
        res = max;
        
        for(int i=0; i < this.taches.size(); ++i) {
            //Pour chaque autre tâche on ajoute le temps unitaire au temps de la tâche critique
            if(i!=index_max) {
                res+=this.taches.get(i)/(double)this.nbBoulonsBobine;
            }
        }
        
        return res;
    }

    public int getTempsLivraisonBobine () {
        return tempsLivraisonBobine;
    }

    public void setTempsLivraisonBobine (int val) {
        this.tempsLivraisonBobine = val;
    }

    public int getTravailHeureJour () {
        return travailHeureJour;
    }

    public void setTravailHeureJour (int val) {
        this.travailHeureJour = val;
    }


    public int getTravailJourSemaine () {
        return travailJourSemaine;
    }

    
    public void setTravailJourSemaine (int val) {
        this.travailJourSemaine = val;
    }
    
    public static boolean parse(String filename) 
    {
        try 
        {  
            SAXParserFactory fabrique = SAXParserFactory.newInstance();
            SAXParser parseur;
            parseur = fabrique.newSAXParser();
            
            File fichier = new File(filename);
            DefaultHandler gestionnaire = new ConfigurationHandler();
            parseur.parse(fichier, gestionnaire);
            
            return true;
            
        } 
        catch (ParserConfigurationException ex) 
        {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SAXException ex) 
        {
            System.err.println("ERREUR SEVERE: Votre fichier de configuration ne respecte pas la norme XML !");
            //Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
         catch (IOException ex) 
        {
            System.err.println("ERREUR SEVERE: Impossible d'ouvrir votre fichier de configuration !");
            //Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return false;
    }

    public String toString () {
        String res = "";
        res += "#################\n";
        res += "##CONFIGURATION##\n";
        res += "#################\n";
        res += this.nbBoulonsBobine + " boulons réalisés avec une bobine.\n";
        res += this.prixBobine + " euros par bobine.\n";
        res += this.travailHeureJour + " heure(s) de travail par jour.\n";
        res += this.travailJourSemaine + " jour(s) de travail par semaine.\n";
        res += this.enCoursBobine + " boulons réalisés avec une bobine.\n";
        res += this.tempsLivraisonBobine + " boulons réalisés avec une bobine.\n";
        res += this.augmentationPrixAcierMois + "% d'augmentation du prix de l'acier par mois.\n";
        res += "Cout horaire de l'usine de " + this.coutUsineHeure + " euros par heure.\n";
        res += this.augmentationQuantiteCommande + "% d'augmentation de la quantité commandée.\n";
        res += this.margeSouhaite + "% de marge souhaitée.\n";
        
        res += "####TACHES####\n";
        for(int i=0; i<this.taches.size(); ++i) {
            //Affichage des tâches de production
            res += "Durée de la ";
            
            if(i==0) res += "1ère tâche : ";
            else  res+= i+1+"ème tâche : ";
            
            res += this.taches.get(i) + " heure(s)\n";
        }
        
        for(int i=0; i<this.echeances.size(); ++i) {
            res += this.echeances.get(i).toString();
        }
                
        return res;
    }

}

