import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de l'application
 * Elle instancie l'ensemble des sources et des threads
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Supermarche {
	
	static final int RAYON_STOCK_INIT = 5;
	static final int RAYON_STOCK_MAX = 10;
	static final int TAILLE_TAPIS = 5;
	static final int NB_CHARIOTS = 2;
	static final int TPS_CR_REAPPROVISIONNEMENT = 500;
	static final int TPS_CR_DEPLACEMENT_ENTRE_RAYON = 200;
	static final int TPS_MARCHE_CLT = 300;
	static final int TPS_PLACEMENT_CLT = 20;
	
	static final int NB_CLIENT = 5;
	private static List<Client> listeClients;
	
	public static void main(String[] args) {
		//Création du file de chariot
		FileDeChariot fileDeChariot = new FileDeChariot(NB_CHARIOTS);
		
		//Création de la liste des rayons (contenant les rayons créés)
		List<Rayon> listeRayons = new ArrayList<>();
		for(Produits produit: Produits.values()){
			listeRayons.add(new Rayon(produit));
		}
		
		//Création et mise en activité du chef de rayon
		ChefDeRayon chefDeRayon = new ChefDeRayon(listeRayons);
		chefDeRayon.start();
		
		//Création des clients
		listeClients = new ArrayList<>();
		for(int i = 0; i < NB_CLIENT; i++){
			listeClients.add(new Client(fileDeChariot, listeRayons));
		}
		
		//Mise en activité des clients
		for(Client client: listeClients){
			client.start();
		}
		
	}

}
