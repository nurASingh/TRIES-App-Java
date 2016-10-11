package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		
		if(element == null)
			throw(new NullPointerException());
		
		if(element != null){
			LLNode<E> node = new LLNode<E>(element);
			tail.prev.next = node;
			node.prev = tail.prev;
			
			tail.prev = node;
			node.next = tail;
			
			size++;
			return true;
		}

		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		

		
		if(index <0 || index > size)
			throw(new IndexOutOfBoundsException());
		
		if(size == 0)
			throw(new IndexOutOfBoundsException());
		
		if(size <= index)
			throw(new IndexOutOfBoundsException());
		
		int n = 0;
		
		LLNode<E> node = head;
		
		while(n <= index){
			n++;
			if(node.next == null)
				return null;
			node = node.next;
		}
		
		return node.getData();
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		
		
		if(element == null)
			throw(new NullPointerException());
		
		if(index <0 || index > size)
			throw(new IndexOutOfBoundsException());
		
		
		if(index == 0){
			
			LLNode<E> newNode = new  LLNode<E>(element);
			
			newNode.next = head.next;
			head.next.prev = newNode;
			
			head.next = newNode;
			newNode.prev = head;
			size++;
			
			
		}else{
			int n = 0;
			LLNode<E> node = head;
			while(n <= index){
				n++;
				if(node.next == null)
					break;
				else
					node = node.next;
			}
			
			if(node == null){
				// add to head
			}else{
				if(element != null){
					LLNode<E> newNode = new LLNode<E>(element);
					
					newNode.prev = node.prev;
					node.prev.next = newNode;
					
					newNode.next = node;
					node.prev = newNode;
	
					size++;
				}
			}
		}
		
		

	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		
		
		if(index <0 || index > size)
			throw(new IndexOutOfBoundsException());
		
		int n = 0;
		
		LLNode<E> node = head;
		
		while(n <= index){
			n++;
			if(node.next == null)
				return null;
			else
				node = node.next;
		}
		
		LLNode<E> prev = node.prev;
		LLNode<E> next = node.next;
		
		node.prev.next = next;
		next.prev = prev;
		size--;
				
		return node.getData();
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		
		
		if(element == null)
			throw(new NullPointerException());
		
		if(index <=0 || index > size)
			throw(new IndexOutOfBoundsException());
		
		
			int n = 0;
			LLNode<E> node = head;
			while(n <= index){
				n++;
				if(node.next == null)
					break;
				else
					node = node.next;
			}
			
			if(node == null){
				throw(new NullPointerException());
			}else{
				if(element != null){
					LLNode<E> newNode = new LLNode<E>(element);
					
					newNode.prev = node.prev;
					newNode.next = node.next;
					
					node.prev.next = newNode;
					node.next.prev = newNode;
	
					
				}
			}
		
		
		
		return node.getData();
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public E getData(){
		return this.data;
	}

}
