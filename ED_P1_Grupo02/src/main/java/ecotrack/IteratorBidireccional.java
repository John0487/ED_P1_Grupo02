
package ecotrack;

/**
 *
 * @author Grupo02
 */

public class IteratorBidireccional<T> {
    private DoublyCircularNodeList<T> actual; 
    private int posicion;

    public IteratorBidireccional(DoublyCircularNodeList<T> inicio) {
        this.actual = inicio;
        if (inicio != null){
            this.posicion = 1;
        } else {
            this.posicion = 0;
        }
    }

    public T obtenerActual() {
        if(actual!=null){
            return actual.getContent();
        } else{
            return null;
        }

    }

    public T siguiente() {
        if (actual != null) {
            actual = actual.getNext();
            posicion++;
        }
        return obtenerActual();
    }

    public T anterior() {
        if (actual != null) {
            actual = actual.getPrevious();
            posicion--;
        }
        return obtenerActual();
    }

    public int getPosicion() {
        return posicion;
    }
}

