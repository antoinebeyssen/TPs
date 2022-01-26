package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	public void notPrintingTicketInsufficientAmount() {
		machine.insertMoney(20);
                // L'impression n'est pas effectuée car le montant disponible est inférieur au prix du ticket 
		assertFalse(machine.printTicket(), "L'impression du ticket est incorrecte (n'est pas censé imprimer si montant insuffisant");              
	}

	@Test
	// S4 : on imprime pas le ticket si le montant inséré est suffisant
	public void printingTicketSufficientAmount() {
		machine.insertMoney(50);
                // L'impression est effectuée car le montant disponible est supérieur ou égal au prix du ticket
		assertTrue(machine.printTicket(), "L'impression du ticket est incorrecte (est pas censé imprimer si montant suffisant");              
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	public void lowerBalanceAfterPrinting() {
		machine.insertMoney(60);
		machine.printTicket();
                // Les montants ont été correctement additionnés  
		assertEquals(10, machine.getBalance(), "Erreur lors de l'actualisation de la balance après impression du ticket");              
	}

	@Test
	// S6 : Le montant collecté est mis à jour lorsqu'on imprime un ticket
	public void updateTotalAfterPrinting() {
		machine.insertMoney(60);
		machine.printTicket();
                // Les montants ont été correctement additionnés  
		assertEquals(50, machine.getTotal(), "Erreur lors de l'actualisation du montant collecté après impression du ticket");              
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	public void refundWorking(){
		machine.insertMoney(75);
		machine.printTicket();
				// Le remboursement a été effectué correctement
		assertEquals(25, machine.refund(), "Erreur lors du remboursement");
	}

	@Test
	// S8 : refund() remet la balance à 0
	public void refundResetBalance(){
		machine.insertMoney(75);
		machine.printTicket();
		machine.refund();
				// Le remboursement n'a pas remis la balance à zéro
		assertEquals(0, machine.getBalance(), "Montant non remis à zéro après refund");
	}

	@Test
	// S9 : impossibler d'insérer un motant négatif
	public void negativeAmountInsertionLocked(){
			// L'insertion d'un montant négatif entraine une erreur
		assertThrows(IllegalArgumentException.class, () -> { machine.insertMoney(-5); }, "Exception non levée lors de l'ajout d'un montant négatif");
	}

	@Test
	// S10 : impossibler d'insérer un motant négatif
	public void negativeTcketPriceCreationLocked(){
			// La création d'un ticket à montant négatif entraine une erreur
		assertThrows(IllegalArgumentException.class, () -> { machine.insertMoney(-5); }, "Exception non levée lors de la création d'un ticket avec un montant négatif");
	}
}
