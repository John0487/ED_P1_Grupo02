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

    public void registrarResiduo(Residuo r) {
        this.listaDeResiduos.addLast(r);
        System.out.println("Residuo " + r.getId() + " registrado.");
    }

    public DoublyCircularLinkedList<Residuo> getListaResiduos() {
        return this.listaDeResiduos;
    }
    
}


