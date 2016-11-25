/**
 * Classe Client
 * 
 * Permet à un client de faire ses courses en magasin en lui permettant
 * d'emprunter et restituer un chariot dans une file de chariots
 * 
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Client extends Thread {

	FileDeChariot fileDeChariot;

	public Client(FileDeChariot fileDeChariot) {
		this.fileDeChariot = fileDeChariot;
	}

	public void run() {
		try {
			this.emprunterChariot();
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Emprunte un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void emprunterChariot() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "_Client sur le point d'emprunter un chariot ");
		fileDeChariot.deStocker();
		Thread.sleep(5000); // Simule les courses
	}

	/**
	 * Restitue un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void restituerChariot() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "_Client sur le point de restituer un chariot ");
		fileDeChariot.stocker();
	}

}
