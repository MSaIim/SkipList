public class SkipNode<E> 
{
	private E key;
	
	//Remove if removing printHorizontal()
	public int pos;
	
	//Links
	private SkipNode<E> up, down, left, right;
	
	//Left and right keys. Cannot add before -oo or after +oo.
	public static String negInf = "-oo";
	public static String posInf = "+oo";
	
	public SkipNode() {}
	
	public SkipNode(Object k)
	{
		key = (E) k;					// Add key to ArrayList
		
		up = down = left = right = null;		// Set links to null
	}
	
	public boolean equals(Object o) 
	{
		SkipNode<E> ent;
	
		try { 
			ent = (SkipNode<E>) o;    		// Check if o is a SkipNode
		}
		catch (ClassCastException ex) { 
			return false; 
		}

		return (ent.getKey() == key);
	  }

	//Get key at index
	public E getKey() { 
		return key;
	}
	
	//Replace value at index
	public void setKey(E key) { 
		this.key = key;
	}

	public SkipNode<E> getUp() {
		return up;
	}

	public void setUp(SkipNode<E> up) {
		this.up = up;
	}

	public SkipNode<E> getDown() {
		return down;
	}

	public void setDown(SkipNode<E> down) {
		this.down = down;
	}

	public SkipNode<E> getLeft() {
		return left;
	}

	public void setLeft(SkipNode<E> left) {
		this.left = left;
	}

	public SkipNode<E> getRight() {
		return right;
	}

	public void setRight(SkipNode<E> right) {
		this.right = right;
	}

	public static String getNegInf() {
		return negInf;
	}
	
	public static String getPosInf() {
		return posInf;
	}
}
