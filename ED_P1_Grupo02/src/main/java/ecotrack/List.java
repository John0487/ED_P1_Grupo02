package ecotrack;

/**
 *
 * @author Grupo 02
 */

public interface List<E> extends Iterable<E>{

    public boolean addFirst(E e);
            
    public boolean addLast(E e);
    
    public boolean add(E e);
            
    public E removeFirst();
    
    public E removeLast();
    
    public int size();
    
    public boolean isEmpty();
    
    public void clear();
            
    public boolean add(int index, E element); 
            
    public E remove(int index);
    
    public E get(int index); 
    
    public E set(int index, E element);
    
    public void sort(List<E> list, String criterio); 
}



