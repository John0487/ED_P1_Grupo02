package ecotrack;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayList<E> implements List<E>{
    private E[] elements = null;
    private int capacity = 100;
    private int effectiveSize;
    
    public ArrayList(){
        elements = (E[])(new Object[capacity]);
        effectiveSize = 0;
    }

    public int getEffectiveSize() {
        return effectiveSize;
    }

    private boolean isFull(){
        return effectiveSize == capacity;
    }

    public E[] getElements() {
        return elements;
    }

    @Override
    public boolean addFirst(E e) {
        if(e==null){
            return false;
        } else if (isEmpty()){
            elements[0] = e;
            effectiveSize++;
            return true;
        } else if (isFull()){
            addCapacity();
        }
        
        for (int i=effectiveSize-1; i >=0; i--){
            elements[i+1]=elements[i]; 
        }
        elements[0] = e;
        effectiveSize++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
       if (e == null) {
            return false;
        }
        if (isFull()) {
            addCapacity();
        }
        elements[effectiveSize] = e;
        effectiveSize++;
        return true;
    }
    
    public Object[] toArray() {
        Object[] result = new Object[this.effectiveSize];
    
        for (int i = 0; i < this.effectiveSize; i++) {
            result[i] = this.elements[i];
        }
        return result;
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
        return effectiveSize;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove(int index) {
        if (index>=effectiveSize || index<0){
            throw new UnsupportedOperationException("Indice invalido");
        }
        E element = get(index);
        for (int i=index+1;i<effectiveSize;i++){
            elements[i-1] = elements[i];
        }
        effectiveSize--;
        elements[effectiveSize] = null;
        return element;
    }

    @Override
    public E get(int index) {
       if (index>effectiveSize || index<0){
            throw new UnsupportedOperationException("Indice invalido");
        }
       return elements[index];
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void addCapacity() {
        E[] tmp = (E[]) new Object[capacity * 2];
        for (int i = 0; i < capacity; i++){
            tmp[i] = elements[i];
        }
        elements = tmp;
        capacity = capacity * 2;
    }
    
    @Override
    public boolean add(int index, E element){
        if(index < 0 || index > effectiveSize){
            return false;
        }
        
        if(isFull()){
            addCapacity();
        }
        for(int i = effectiveSize; i > index; i--){
            elements[i]=elements[i - 1];
        }
        elements[index] = element;
        effectiveSize++;
        return true;
    
    }
    
    @Override
    public String toString(){
        String text = "";
        for(E element: elements){
            if(element!=null){
                text = text + element.toString() + " ";
            }
        }
        return "["+text.strip()+"]";
    }
    
    public boolean contains(E element2){
        for(int i=0; i<effectiveSize; i++){
            if(elements[i].equals(element2)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Iterator<E> iterator() {
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
    public boolean add(E dato) {
        try {
            if (capacity == effectiveSize) {
                addCapacity();
            }
            elements[effectiveSize] = dato;
            effectiveSize++;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    public void sort(Comparator<E> c) {
        for (int i = 1; i < effectiveSize; i++) {
            E clave = elements[i];
            int j = i - 1;

            while (j >= 0 && c.compare(elements[j], clave) > 0) {
                elements[j + 1] = elements[j];
                j--;
            }
            elements[j + 1] = clave;
        }
    }

    @Override
    public void sort(List<E> list, String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

