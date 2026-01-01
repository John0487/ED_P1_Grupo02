package ecotrack;

/**
 *
 * @author Grupo 02
 */

public class GestorResiduos {
    
    private DoublyCircularLinkedList<Residuo> listaDeResiduos;

    public GestorResiduos() {
        this.listaDeResiduos = new DoublyCircularLinkedList<>();
    }

    public DoublyCircularLinkedList<Residuo> getListaResiduos() {
        return this.listaDeResiduos;
    }
    
    public boolean agregarResiduo(Residuo r){
        if(r==null){
            return false;
        }
        this.listaDeResiduos.addLast(r);
        return true;
    }
    
}


