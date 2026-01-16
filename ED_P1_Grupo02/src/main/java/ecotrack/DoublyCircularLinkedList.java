package ecotrack;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 *
 * @author Grupo 02
 */

public class DoublyCircularLinkedList<E> implements List<E>,Serializable{
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

    public int getSize() {
        return size;
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
        if (isEmpty()) return;
        header.setPrevious(null);
        header.setNext(null);
        header = null; 
        size = 0;
    }

    @Override
    public boolean add(E e) {
        try {
            DoublyCircularNodeList<E> nuevo = new DoublyCircularNodeList<>(e);
            if (header == null) {
                header = nuevo;
                header.setNext(header);
                header.setPrevious(header);
            } else {
                DoublyCircularNodeList<E> ultimo = header.getPrevious();

                nuevo.setNext(header);
                nuevo.setPrevious(ultimo);

                ultimo.setNext(nuevo);
                header.setPrevious(nuevo);
            }
            size++;
            return true;
        } catch (Exception ex) {
            return false;
        }
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
    public void sort(List<E> list, String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        List.super.forEach(action); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private DoublyCircularNodeList<E> actual = header;
            private int contador = 0;

            @Override
            public boolean hasNext() {
                return contador < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                E dato = actual.getContent();
                actual = actual.getNext();
                contador++;
                return dato;
            }
        };
    }

    @Override
    public boolean add(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
