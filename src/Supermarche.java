/**
 * Classe principale de l'application
 * Elle instanciera l'ensemble des sources et des threads
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Supermarche {
	
	static final int RAYON_STOCK_INIT = 5;
	static final int RAYON_STOCK_MAX = 10;
	static final int TAILLE_TAPIS = 5;
	static final int NB_CHARIOTS = 4;
	static final int TPS_CR_PLEIN = 500;
	static final int TPS_CR_DEPLACEMENT = 200;
	static final int TPS_MARCHE_CLT = 300;
	static final int TPS_PLACEMENT_CLT = 20;
	
	public static void main(String[] args) {
		FileDeChariot fileDeChariot = new FileDeChariot(NB_CHARIOTS);
		
		Client client1 = new Client(fileDeChariot);
		Client client2 = new Client(fileDeChariot);
		Client client3 = new Client(fileDeChariot);
		Client client4 = new Client(fileDeChariot);
		Client client5 = new Client(fileDeChariot);
		
		client1.start();
		client2.start();
		client3.start();
		client4.start();
		client5.start();

	}

}
