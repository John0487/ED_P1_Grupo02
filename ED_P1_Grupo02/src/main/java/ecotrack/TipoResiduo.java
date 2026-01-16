package ecotrack;

/**
 *
 * @author Grupo 02
 */
public enum TipoResiduo {
    ORGANICO, PLASTICO, VIDRIO, ELECTRONICO, METAL;

    public int compareToIgnoreCase(TipoResiduo otro) {
        return this.toString().compareToIgnoreCase(otro.toString());
    }
}
