package entitats;

/**
 * Llògica de negoci de clients amb les comprovacions pertinents
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació (RF34 i RF44)
 */
public class ClientLogic {

    public ClientLogic() {
    }
    
    /**
     * Comprovar que el client no té cap comanda en curs. 
     * 
     */
    public boolean hasOrders(){
        boolean res = false;        
        return res;
    }
    
    public boolean checkDefaultCreditLimit(){
        boolean ret = false;        
        return ret;
    }
    
    public boolean checkMinCustomerAge(){
        boolean ret = false;        
        return ret;
    }
}
