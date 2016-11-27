import java.util.ArrayList;

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
	
	public static void main(String[] args) {
		FileDeChariot fileDeChariot = new FileDeChariot(NB_CHARIOTS);
		
		Rayon rayonSucre = new Rayon(Produits.SUCRE, RAYON_STOCK_INIT, RAYON_STOCK_MAX);
		Rayon rayonFarine = new Rayon(Produits.FARINE, RAYON_STOCK_INIT, RAYON_STOCK_MAX);
		Rayon rayonBeurre = new Rayon(Produits.BEURRE, RAYON_STOCK_INIT, RAYON_STOCK_MAX);
		Rayon rayonLait = new Rayon(Produits.LAIT, RAYON_STOCK_INIT, RAYON_STOCK_MAX);
		
		ArrayList<Rayon> listeRayons = new ArrayList<>();
		listeRayons.add(rayonLait);
		listeRayons.add(rayonBeurre);
		listeRayons.add(rayonFarine);
		listeRayons.add(rayonSucre);
		
		ChefDeRayon chefDeRayon = new ChefDeRayon(listeRayons);
		chefDeRayon.start();
		
		Client client1 = new Client(fileDeChariot, listeRayons);
		Client client2 = new Client(fileDeChariot, listeRayons);
		Client client3 = new Client(fileDeChariot, listeRayons);
		Client client4 = new Client(fileDeChariot, listeRayons);
		Client client5 = new Client(fileDeChariot, listeRayons);
		
		client1.start();
		client2.start();
		client3.start();
		client4.start();
		client5.start();

	}

}
