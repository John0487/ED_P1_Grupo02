package ecotrack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Grupo 02
 */

public class DoublyCircularLinkedList<E> implements List<E>{
    private DoublyCircularNodeList header;
    private int size;
    
    public DoublyCircularNodeList<E> getLast(){
        return header.getPrevious();
    }
    
     public DoublyCircularNodeList<E> getHeader(){
        return header.getPrevious().getNext();
    }
    
    public void setLast(DoublyCircularNodeList<E> last){
        this.header.setPrevious(last);
    }
    
    public void setHeader(DoublyCircularNodeList<E> header){
        this.header=header;
        
    }
    
    @Override
    public boolean addFirst(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addLast(E content) {
        DoublyCircularNodeList<E> n = new DoublyCircularNodeList<>(content);

        if (isEmpty()) {
            header = n;
            setLast(n);
            header.setNext(header);
            header.setPrevious(header);
        } else {
            n.setNext(header);
            n.setPrevious(header.getPrevious());
            header.getPrevious().setNext(n);
            header.setPrevious(n);
        }
        size++;
        return true;
    }

    @Override
    public E removeFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E removeLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Iterator<E> iterator(){
        return new Iterator<E>() {
            private DoublyCircularNodeList<E> current = header;
            private int count = 0;
        
            @Override
            public boolean hasNext() {
                return this.count < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay más elementos en la lista.");
                }
                E data = current.getContent();
                current = current.getNext();
                this.count++;
                return data;
            }
        }; 
    }
    
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private DoublyCircularNodeList<E> current = (header == null) ? null : header.getPrevious();
            private int count = 0;
            
            @Override
            public boolean hasNext() {
                return this.count < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay más elementos en la lista.");
                }
                E data = current.getContent();
                current = current.getPrevious();
                this.count++;
                return data;
            }
        }; 
    }
}
