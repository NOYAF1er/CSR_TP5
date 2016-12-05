import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe Client
 * <p>
 * Recupère un chariot dans une file de chariot dès que possible puis parcours
 * l'ensemble des rayons afin de recupérer les produits correspondants à sa liste
 * de courses enfin passe à la caisse afin de faire enregistrer ses produits et
 * effectuer le reglement
 * </p>
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 * 
 *@see Thread
 *
 */
public class Client extends Thread {
	/** 
	 * Liste de courses contenant les produits et leurs quantités 
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 * @see Client#setListeDeCourses()
	 * @see Client#setListeDeCourses(Map)
	 * @see Client#prendreProduits(Rayon)
	 * @see Client#passerAlaCaisse()
	 */
	Map<Produits, Integer> listeDeCourses;
	
	/** 
	 * File de chariot du supermarché
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 * @see Client#emprunterChariot()
	 * @see Client#restituerChariot()
	 *  
	 */
	FileDeChariot fileDeChariot;
	
	/** 
	 * Liste des rayons du supermarché
	 * 
	 *  @see Client#Client(FileDeChariot, List, Caisse)
	 *  @see Client#run()
	 */
	List<Rayon> listeRayons;
	
	/** 
	 * Caisse du supermarché 
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 * @see Client#passerAlaCaisse()
	 */
	Caisse caisse;
	
	/**
	 * Constructeur
	 * 
	 * <ul>
	 * Fixe:
	 * 	<li>la liste de course</li>
	 * 	<li>la file de chariot</li>
	 * 	<li>la liste de rayon</li>
	 * 	<li>la caisse</li>
	 * </ul>
	 * 
	 * @param fileDeChariot
	 * @param listeRayons
	 * @param caisse
	 * 
	 * @see FileDeChariot
	 * @see List
	 * @see Rayon
	 * @see Caisse
	 */
	public Client(FileDeChariot fileDeChariot, List<Rayon> listeRayon, Caisse caisse) {
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
		this.caisse = caisse;
		
		this.listeDeCourses = new HashMap<>();
		this.setListeDeCourses();
	}
	
	/** 
	 * File de déroulement du thread
	 * <ol>
		 * <li>Emprunte un chariot</li>
		 * <li>Parcours tous les rayons pour recupérer les produits de sa liste de courses</li>
		 * <li>Passe à la caisse afin de faire enregistrer ses produits et effectuer le règlement</li>
		 * <li>Restitue le chariot</li>
	 * </ol>
	 */
	public void run() {
		try {
			//Emprunt d'un chariot
			this.emprunterChariot();
			
			//Parcours des rayons
			for(Rayon rayon: listeRayons){
				prendreProduits(rayon);
				Thread.sleep(Supermarche.TPS_MARCHE_CLT); // Simule le temps de marche entre les rayons
			}
			
			//Passage en caisse
			passerAlaCaisse();
			
			//Restitution du chariot
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Définit une liste de courses de façon aléatoire
	 * 
	 * @see Client#Client(FileDeChariot, List, Caisse)
	 */
	public void setListeDeCourses(){
		Random rd = new Random();
		
		this.listeDeCourses.put(Produits.SUCRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.FARINE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.BEURRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.LAIT, rd.nextInt(10));
	}
	
	/**
	 * Définit une liste de courses à partir de celle indiqué
	 * 
	 * @param listeDeCourses Liste de courses
	 * @see Map
	 * @see Produits
	 * @see Integer
	 */
	public void setListeDeCourses(Map<Produits, Integer> listeDeCourses){
		this.listeDeCourses = listeDeCourses;
	}	

	/**
	 * Emprunte un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 * @see Client#run()
	 */
	public void emprunterChariot() throws InterruptedException {
		fileDeChariot.deStocker();
	}

	/**
	 * Restitue un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void restituerChariot() throws InterruptedException {
		fileDeChariot.stocker();
	}
	
	/**
	 * Retire dans un rayon donnï¿½, la quantitï¿½ de produit figurant sur 
	 * la liste de courses
	 * 
	 * @param rayon Rayon sur lequel sera retirï¿½ le produit
	 * @throws InterruptedException
	 */
	public void prendreProduits(Rayon rayon) throws InterruptedException{
		Produits produitRayon = rayon.getProduit();
		int qteProduit = listeDeCourses.get(produitRayon);
		//System.out.println(Thread.currentThread().getName() + " Liste Courses: " + produitRayon + " = " + qteProduit);
		
		while(qteProduit > 0){
			rayon.deStocker();
			qteProduit--;
		}	
	}
	
	/**
	 * Poser ses produits les uns aprÃ¨s les autres sur le tapis de la caisse (dans les limites du tapis)
	 * aprÃ¨s avoir vÃ©rifiÃ© que la caisse soit libre
	 * puis liberer la caisse
	 * @throws InterruptedException
	 */
	public void passerAlaCaisse() throws InterruptedException{
		caisse.clientSuivant(); //VÃ©rifie que la caisse est libre
		for(Produits produit: listeDeCourses.keySet()){
			for(int i=0, qtProduit = listeDeCourses.get(produit); i < qtProduit; i++){
				caisse.charger(produit);
			}
		}
		caisse.reglementClient();
	}

}
