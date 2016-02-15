import java.util.Random;

//Need Comparable<E> for compareTo() method
public class SkipList<E extends Comparable<E>>
{
	private SkipNode<E> head;
	private SkipNode<E> tail;
	
	private int size, height;
	private Random rand;
	
	public SkipList()
	{
		//Head and tail to negative and positive infinity
		SkipNode<Object> p1 = new SkipNode<Object>(SkipNode.negInf);
		SkipNode<Object> p2 = new SkipNode<Object>(SkipNode.posInf);
		
		/* ----------------------------------------------
		Empty skiplist
	
				    null        null
	                              ^           ^
	                              |           |
		head --->  null <-- -inf <----> +inf --> null
	                              |           |
	                              v           v
	                             null        null
		---------------------------------------------- */
		
		//Head points to -oo | Tail points to +oo
		head = (SkipNode<E>) p1;
		tail = (SkipNode<E>) p2;
		
		p1.setRight(p2);
		p2.setLeft(p1);
		
		//Get random number
		rand = new Random();
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	//Traverses through the SkipList to find a suitable spot for a node.
	//See diagram below.
	public SkipNode<E> find(E key)
	{
		//Start at head
		SkipNode<E> p = head;
	
		//Keep running until a spot is found
		while (true)
		{
			/* --------------------------------------------
			Search RIGHT until you find a LARGER entry
	
	           	E.g.: k = 34
	
	                     10 ---> 20 ---> 30 ---> 40
	                                      ^
	                                      |
	                                      p stops here
			p.right.key = 40
			-------------------------------------------- */
	    	 
			//Check if right key is not positive infinity and if it's less than or equal to key on right
			while ((p.getRight().getKey() != SkipNode.posInf) && (p.getRight().getKey().compareTo(key) <= 0))
			{
				//Move link to the right
				p = p.getRight();
			}
	
			/* ---------------------------------
		   	 Go down one level if you possible
		   	--------------------------------- */
	        if (p.getDown() != null)
	        	p = p.getDown();
	        else
	        	break;		//Lowest level reached, exit loop
	     }
	
	     return(p);         	// p.key <= k
	}
	
	//Find a key
	public E get(E key)
	{
		SkipNode<E> p = find(key);
		
		if(key.equals(p.getKey()))
			return(p.getKey());
		else
			return null;
	}
	
	//Add to list
	public void add(E key)
	{
		int level;
		SkipNode<E> p, q;
		
		//Find a suitable place to insert the node we want to add
		p = find(key);
		
		//Make sure key is not already in list
		if(key.equals(p.getKey()))
			return;
		
		/*-------------------------------------------------------------------
		| Insert
		|  - First line is the actual node we want to add (call it addNode)
		|  - Set left link of addNode to previous node (p)
		|  - Set right link of addNode to p's right link
		|  - Make p's left link point to q, which is now left of it
		|  - Make p's right link point to q
		|
		|  Ex:  p <---> q <---> something
		-------------------------------------------------------------------*/
		q = new SkipNode<E>(key);
		q.setLeft(p);
		q.setRight(p.getRight());
		p.getRight().setLeft(q);
		p.setRight(q);
		
		//Lowest level = 0
		level = 0;
		
		//Coin toss. If less than 0.5 make a new level
		while(rand.nextDouble() < 0.5)
		{
			//Node to add to next level
			SkipNode<E> e = new SkipNode<E>(key);
			
			//Check if height exceeds current level. If so, make a new level
			if(level >= height)
			{
				//Head and tail to negative and positive infinity
				SkipNode<Object> p1 = new SkipNode<Object>(SkipNode.negInf);
				SkipNode<Object> p2 = new SkipNode<Object>(SkipNode.posInf);
				
				//Increase height level
				height++;
				
				//Set links for new level
				p1.setRight(p2);
				p1.setDown((SkipNode<Object>) head);
				p2.setLeft(p1);
				p2.setDown((SkipNode<Object>) tail);
				
				head.setUp((SkipNode<E>) p1);
				tail.setUp((SkipNode<E>) p2);
				
				//Head to top left and tail top right
				head = (SkipNode<E>) p1;
				tail = (SkipNode<E>) p2;
			}
		
			//Scan backwards
			while(p.getUp() == null)
				p = p.getLeft();
		
			p = p.getUp();
			
			//Add node with same key one level up and initialize links
			e.setLeft(p);
			e.setRight(p.getRight());
			e.setDown(q);
			
			//Change neighbor links
			p.getRight().setLeft(e);
			p.setRight(e);
			q.setUp(e);
			
			//Set q up for next iteration
			q = e;
			
			//Increase level count so if statement doesn't keep making new levels
			level++;
		}
		
		//Increase number of entries
		size++;
	}
	
	//---------------------------------------------------PRINTING--------------------------------------------------------
	public void printHorizontal()
	{
		int i = 0;
		String s = "";
		SkipNode<E> p;
		
		p = head;
		
		while(p.getDown() != null)
			p = p.getDown();
		
		while(p != null)
		{
			p.pos = i++;
			p = p.getRight();
		}
		
		p = head;
		
		while(p != null)
		{
			s = getOneRow(p);
			System.out.println(s);
			p = p.getDown();
		}
	}
	
	public String getOneRow(SkipNode<E> p)
	{
		String s;
		int a = 0, b, i;
		
		s = "" + p.getKey();
		p = p.getRight();
		
		while(p != null)
		{
			SkipNode<E> q = p;
			
			while(q.getDown() != null)
				q = q.getDown();
			
			b = q.pos;
			s = s + " <-";
			
			for(i = a+1; i < b; i++)
				s = s + "--------";
			
			s = s + "> " + p.getKey();
			a = b;
			p = p.getRight();
		}
		
		return s;
	}
}
