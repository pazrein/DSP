import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
/*
 *	Names: Tom Lapid, Paz Reingold
 *	User Names: tomlapid, pazreingold
 *	Id's: 203135256, 208209734
 */

/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer keys and values
 *
 */

public class RBTree {


/**
 * public class RBNode
 */
	
	
  public static class RBNode{
	  	
	  	private int key;
	  	private String value;
	  	private RBNode parent=null;
		private RBNode rightChild=null;
	  	private RBNode leftChild=null;
	  	private String Color="Black";

	  	/**
	  	 * @pre key is a non-negative integer
	  	 * @post this.getKey() == key
	  	 * @post this.getValue() == value
	  	 * @param key
	  	 * @param value
	  	 */
	  	public RBNode(int key,String value){
	  		this.key=key;
	  		this.value=value;
	  	}

		boolean isRed(){
			return this.Color.equals("Red");
		}
		
		boolean isBlack(){
			return this.Color.equals("Black");
		}
		
		RBNode getLeft(){
			return leftChild;
		}
		
		private void setLeft(RBNode leftChild) {
			this.leftChild = leftChild;
		}
		
		RBNode getRight(){
			return rightChild;
		}
		
		private void setRight(RBNode rightChild) {
			this.rightChild = rightChild;
		}
		
		public RBNode getParent() {
			return parent;
		}
		
		private void setParent(RBNode parent) {
			this.parent = parent;
		}
	  	
		public int getKey() {
			return key;
		}
		
		private void setKey(int key) {
			this.key = key;
		}
		
		String getValue(){
			return value;
		}
		
		private void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return Color;
		}

		/**
		 * @pre color can be Red or Black.
		 * @post this.getColor() == color.
		 * @param color
		 */
		private void setColor(String color) {  
			if(color.equals("Black")||color.equals("Red")){
				this.Color = color;
			}
		}

		public String Print(){
			return ("(Key: "+ getKey() +",Value: "+  getValue() +",Color: "+getColor()+")");
		}

	}
  
  private RBNode root=null;
  private RBNode Max_Value=null;
  private RBNode Min_Value=null;
  private int Tree_size=0;
  private RBNode leaf = new RBNode(-1,null);  //Black by default
  
  /**
   * public RBTree()
   * empty constructor , used in order not to get the default constructor.
   */
  public RBTree(){

  }

/**
 * public RBTree(int key,String value)
 * initialize a tree and his root by the given parameters using the insert method. 
 * @param key
 * @param value
 */
  public RBTree(int key,String value){
	  this.insert(key,value);
  }
  
 /**
   * public RBNode getRoot()
   * returns the root of the red black tree
   * Complexity - O(1)
   */

  public RBNode getRoot() {  
	  return this.root; 
  }
  
  /**
   * @return the leaf node, for internal testing.
   */
  public RBNode getLeaf(){
		return this.leaf;
  }
  
  /**
   * public boolean empty()
   * returns true if and only if the tree is empty
   * Complexity - O(1)
   */
  
  public boolean empty() {  
    return this.root==null; 
  }

 /**
   * public String search(int k)
   * @param k
   * @return the value of an item with key k if it exists in the tree
   * otherwise, returns null. 
   * Complexity - O(logn)
   */

  
  public String search(int k)
  {
	RBNode search_res = inner_search(root,k); 
	if (search_res==leaf){
		return null;
	}
	return search_res.getValue();
	
  }
  
  /**
   *  private RBNode inner_search(RBNode x,int k)
   *  @param x
   *  @param k
   *  @return the node with the key k
   *  Complexity - O(logn)
   */
  
  private RBNode inner_search(RBNode x,int k){
	  
		if(x==null || x==leaf){
			return leaf;
		}
		
		if (k==x.key){  
			return x;
		}

		if(k<x.key){
			return inner_search(x.getLeft(),k);
		}
		
		else{	//(k>x.key)
			return inner_search(x.getRight(),k);
		}
		
  }
  

  /**
   * private void replace_leftChild(RBNode x,RBNode y)
   * @param x
   * @param y
   * replacing the left child of x with y.
   * Complexity - O(1)
   */
  
  private void replace_leftChild(RBNode x,RBNode y){
	  x.setLeft(y);
	  y.setParent(x);
  }
  

  /**
   * private void replace_rightChild(RBNode x,RBNode y)
   * @param x
   * @param y
   * replacing the right child of x with y.
   * Complexity - O(1)
   */
  
  private void replace_rightChild(RBNode x,RBNode y){
	  x.setRight(y);
	  y.setParent(x); 
	  
  }
  

  /**
   * private void Transplant(RBNode x,RBNode y)
   * @param x
   * @param y
   * transplanting y instead of x in the tree.
   * Complexity - O(1)
   */
  
  private void Transplant(RBNode x,RBNode y){
	  
	  if(x == this.root){
		  x = y;
		  this.root = y;
	  }
	  if(x == x.getParent().getLeft()){
		  replace_leftChild(x.getParent(),y);
	  }
	  else{
		  replace_rightChild(x.getParent(),y);
	  }
	  
  }
  

  /**
   * private void Left_Rotate(RBNode x)
   * @param x
   * rotates the subtree of x to the left.
   * Complexity - O(1)
   */
  
  private void Left_Rotate(RBNode x){
	  RBNode y = x.getRight();
	  Transplant(x,y);
	  replace_rightChild(x,y.getLeft());
	  replace_leftChild(y,x);
  }
  
  
  /**
   * private void Right_Rotate(RBNode x)
   * @param x
   * rotates the subtree of x to the right.
   * Complexity - O(1)
   */
  
  private void Right_Rotate(RBNode x){
	  RBNode y = x.getLeft();
	  Transplant(x,y);
	  replace_leftChild(x,y.getRight());
	  replace_rightChild(y,x);
  }
  
  /**
   * private RBNode RBTree_Position(RBNode T,int z)
   * @param T
   * @param z
   * @return - if the tree T contains a node with the key z returns this node, else  returns z's future parent
   * Complexity - O(logn)
   */

  private RBNode RBTree_Position(RBNode T,int z){ 
	  
	  RBNode Res;
	  if(T.getRight().getValue() == null && T.getLeft().getValue() == null){	 //the children are leafs
		  Res = T;
	  }
	  
	  else if(T.getRight().getValue() == null){ // T.leftChild!=leaf	
		  if(z >= T.getKey()){
			  Res = T;
		  }
		  else{
			  Res = RBTree_Position(T.getLeft(),z);
		  }
	  }
	  
	  else if(T.getLeft().getValue() == null){ // T.rightChild!=leaf	
		  if(z <= T.getKey()){
			  Res = T;
		  }
		  else{
			  Res = RBTree_Position(T.getRight(),z);
		  }
	  }
	  
	  else{  //two children
		  
		  if(z < T.getKey()){
			  Res = RBTree_Position(T.getLeft(),z);
		  }
		  else if(z > T.getKey()){
			  Res = RBTree_Position(T.getRight(),z);
		  }
		  else{
			  Res = T;
		  }
		  
	  }
	return Res;

  }
  

  /**
   * private int RB_Tree_InsertFixup(RBNode root,RBNode z)
   * @param root
   * @param z
   * @return the number of color switches that have been done during repairing the tree
   * or 0 if no color switches were necessary.
   * Complexity - O(logn)
   */
  
  private int RB_Tree_InsertFixup(RBNode root,RBNode z){

	  int cnt = 0;
	  
	  while(z.getParent() != null && z.getParent().isRed()){
		  
		  //parent is a left child.
		  if(z.getParent() == z.getParent().getParent().getLeft()){     
			  RBNode y = z.getParent().getParent().getRight();   //y is z's uncle
			  //Case 1. uncle is red
			  if(y.isRed()){  
				  z.getParent().setColor("Black");
				  y.setColor("Black");
				  z.getParent().getParent().setColor("Red");
				  cnt+=3;
				  z=z.getParent().getParent(); //in order to keep checking upwards.
			  }
			  else{
				  //Case 2. z is a right child
				  if(z == z.getParent().getRight()){ 
					  z = z.getParent();
					  Left_Rotate(z);
				  }
				  //Case3 - organizing colors then rotate.
				  z.getParent().setColor("Black");
				  z.getParent().getParent().setColor("Red");
			 	  cnt += 2;
				  Right_Rotate(z.getParent().getParent());
			  }
 
		  }
	 
		  //parent is a right child.
		  else{  
			  RBNode y = z.getParent().getParent().getLeft(); //y is z's uncle
			  //Case 1. uncle is red
			  if(y.isRed()){
				  z.getParent().setColor("Black");
				  y.setColor("Black");
				  z.getParent().getParent().setColor("Red");
				  cnt += 3;
				  z = z.getParent().getParent(); //in order to keep checking upwards.
			  }
			  else{
				  //Case 2. z is a left child
				  if(z == z.getParent().getLeft()){ 
					  z = z.getParent();
					  Right_Rotate(z);
				  }
				  //Case3 - organizing colors then rotate.
				  z.getParent().setColor("Black");
				  z.getParent().getParent().setColor("Red");
				  cnt += 2;
				  Left_Rotate(z.getParent().getParent());
			  }
					  
		  }
		  
	  }
	  
	  if(this.root.isRed()){
		  this.root.setColor("Black");
		  cnt++;
	  }
	   
	  return cnt;
  }
  
  
  
  
  /**
   * public int insert(int k, String v)
   * inserts an item with key k and value v to the red black tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of color switches, or 0 if no color switches were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * 
   * Complexity - O(logn)
   */
  
   public int insert(int k, String v) {
	   

	  RBNode z = new RBNode(k,v); 
	  
	  if(this.empty()){
		  z.setColor("Black");
		  z.setLeft(leaf);
		  z.setRight(leaf);
		  root=z;
		  Tree_size++;
		  Max_Value=z;
		  Min_Value=z;
		  return 1;   // insert to empty tree count as color change
	  }
	  
	  RBNode y = RBTree_Position(this.root,k);
	  
	  if(k == y.getKey()){
		  return -1;
	  }
	  
	  z.setParent(y);
	  z.setLeft(leaf);
	  z.setRight(leaf);
	  z.setColor("Red");
	  
	  if(k < y.getKey()){
		  y.setLeft(z);
	  }
	  else{
		  y.setRight(z);
	  }
	  Tree_size++;
	  if(Max_Value.getKey()<k){
		  Max_Value=z;
	  }
	  if(Min_Value.getKey()>k){
		  Min_Value=z;
	  }
	  return RB_Tree_InsertFixup(this.root,z);
   }

   
   /**
    * private int Min_Key(RBNode x)
    * @param x
    * @return the minimum key of subtree x
    * Complexity - O(logn)
    */
    
   private int Min_Key(RBNode x){  
	   while(x.getLeft() != leaf){
		   x = x.getLeft();
	   }
	   return x.getKey();
   }
   
   
   /**
    * private int Max_Key(RBNode x)
    * @param x
    * @return the maximum key of subtree x
    * Complexity - O(logn)
    */
   
   private int Max_Key(RBNode x){
	   while(x.getRight()!=leaf){
		   x=x.getRight();
	   }
	   return x.getKey();
   } 
   

   /**
    * private RBNode Successor(RBNode x)
    * @param x
    * @return the successor RBNode of RBNode x
    * Complexity - O(logn)
    */
   
   private RBNode Successor(RBNode x){
	   if(x.getRight() != leaf){
		     return inner_search(this.root,Min_Key(x.getRight()));
	   }
	   RBNode y = x.getParent();
	   while(y != null && x == y.getRight()){
		   x = y;
		   y = x.getParent();
	   }
	   return y;
   }

   
   /**
    * private RBNode PreDecessor(RBNode x)
    * @param x
    * @return the predecessor RBNode of RBNode x
    * Complexity - O(logn)
    */
   
   private RBNode PreDecessor(RBNode x){
	   if(x.getLeft() != leaf){
		   return inner_search(this.root,Max_Key(x.getLeft()));
	   }
	   RBNode y = x.getParent();
	   while(y != null && x == y.getLeft()){
		   x = y;
		   y = x.getParent();
	   }
	   return y;

   }
   
   /**
    * private int RB_Tree_DeleteFixup(RBNode x)
    * @param x
    * @return the number of color switches that have been done during repairing the tree
    * or 0 if no color switches were necessary.
    * Complexity - O(logn)
    */
   
   
  private int RB_Tree_DeleteFixup(RBNode x){
	  
	  int cnt = 0;
	  
	  while(x != this.root && x.isBlack()){
		  RBNode xFather;
		  //x is a left child
		  if(x == x.getParent().getLeft()){
			  //w is x's brother.
			  RBNode w = x.getParent().getRight();
			  
			  //Case1
			  if(w.isRed()){
				  
				  w.setColor("Black");
				  cnt++;
				  if(x.getParent().isBlack()){
					  x.getParent().setColor("Red");
					  cnt++;
				  }
				  if(x==leaf){
					  xFather=x.getParent();
					  Left_Rotate(x.getParent());
					  x.setParent(xFather);
				      //we want w to stay x's brother after the changes.
					  w=x.getParent().getRight();
				  }
				  else{
					  Left_Rotate(x.getParent());
					  w=x.getParent().getRight();
				  }

			  }
			  
			  //Case2
			  if(w.getLeft().isBlack() && w.getRight().isBlack()){
				 if(w.isBlack()){ 
					 w.setColor("Red"); 
					 cnt++;
				 }
				 x = x.getParent();
			  }

			  //Case 3
			  else if (w.getRight().isBlack()){
				  if(w.getLeft().isRed()){
					  w.getLeft().setColor("Black");
					  cnt++;
				  }
				  if(w.isBlack()){
					  w.setColor("Red");
					  cnt++;
				  }
				  
				  if(x==leaf){
					  xFather=x.getParent();
					  Right_Rotate(w);
					  x.setParent(xFather);
					  w=x.getParent().getRight();
				  }
				  else{
					  Right_Rotate(w);
					  w=x.getParent().getRight();
				  }


			  }
	
			  
			  //Case 4

			  if(w.isBlack() && w.getRight().isRed()){   
				  if(!(w.getColor().equals(x.getParent().getColor()))){
					  w.setColor(x.getParent().getColor());
					  cnt++;
				  }
				  if(x.getParent().isRed()){
					  x.getParent().setColor("Black");
					  cnt++;
				  }
				  if( w.getRight().isRed()){
					  w.getRight().setColor("Black");
					  cnt++;
				  }

				  Left_Rotate(x.getParent());
			      x = this.root;
			  }
  
		  }
		  

		  //x is a right child
		  else if(x == x.getParent().getRight()){
			  //w is brother of x.
			  RBNode w = x.getParent().getLeft();
			  
			  //Case1
			  if(w.isRed()){
				  w.setColor("Black");
				  cnt++;
				  if(x.getParent().isBlack()){
					  x.getParent().setColor("Red");
					  cnt++;
				  }
				  if(x==leaf){
					  xFather=x.getParent();
					  Right_Rotate(x.getParent());
					  x.setParent(xFather);
				      //we want w to stay x's brother after the changes.
					  w = x.getParent().getLeft();
				  }
				  else{
					  Right_Rotate(x.getParent());
					  w = x.getParent().getLeft();
				  }
			  }
			  
			  //Case2
			  if(w.getLeft().isBlack() && w.getRight().isBlack()){
				 if(w.isBlack()){ 
					 w.setColor("Red"); 
					 cnt++;
				 }
				 x = x.getParent();
			  }
			  
			  //Case 3
			  else if (w.getLeft().isBlack()){
				  
				  if(w.getRight().isRed()){
					  w.getRight().setColor("Black");
					  cnt++;
				  }
				  if(w.isBlack()){
					  w.setColor("Red");
					  cnt++;
				  }
				  
				  if(x==leaf){
					  xFather=x.getParent();
					  Left_Rotate(w);
					  x.setParent(xFather);
					  w = x.getParent().getLeft();
				  }
				  else{
					  Left_Rotate(w);
					  w = x.getParent().getLeft();
				  }

			  }
			  
			  //Case 4

			  if(w.isBlack() && w.getLeft().isRed()){  
				 
				  if(!(w.getColor().equals(x.getParent().getColor()))){
					  w.setColor(x.getParent().getColor());
					  cnt++;
				  }
				  if(x.getParent().isRed()){
					  x.getParent().setColor("Black");
					  cnt++;
				  }
				  if( w.getLeft().isRed()){
					  w.getLeft().setColor("Black");
					  cnt++;
				  }

				  Right_Rotate(x.getParent());
				  x = this.root;
			  }
		  }	  
 
	  }
	  if(x.isRed()){
		  x.setColor("Black");
		  cnt++;
	  }
	  return cnt;
  }


  /**
   * public int delete(int k)
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of color switches, or 0 if no color switches were needed.
   * returns -1 if an item with key k was not found in the tree.
   * Complexity - O(logn)
   */
   
   
   public int delete(int k)
   {
	   RBNode z = inner_search(this.root,k); 
	   int cnt=0;

	   // key k does'nt exit in the tree inner_search returns leaf
	   if(z == leaf){  
		   return -1;
	   }
	   
  	   if(k==Min_Value.getKey()){
  		   Min_Value=Successor(z);
   	   }
  	
  	   if(k==Max_Value.getKey()){
     	   Max_Value=PreDecessor(z);
  	   }
 
	   //finding y, the node to be deleted.
	   RBNode y;
  	   if(z.getLeft()==leaf || z.getRight()==leaf ){
  		   y = z;
  	   }
  	   else{   
  		  y = Successor(z);
  	   }
  	   	   	   
  	   //x is a child of y.
  	   RBNode x;
  	   if(y.getLeft() != leaf){
  		   x = y.getLeft();
   	   }
  	   else{
  		   x = y.getRight();
  	   }
  	   
	   //linking x with y's parent because we delete y.
  	   x.setParent(y.getParent());
  	   if(y==this.root){ 
  		   if(x==leaf){
  			 Tree_size--;
  			 this.root = null;
  			 return 0;
  		   }
  		   this.root = x; 
  	   }
  	   else if(y == y.getParent().getLeft()){
  		   y.getParent().setLeft(x);
  	   }
  	   else if(y == y.getParent().getRight()){
  		   y.getParent().setRight(x);
  	   }
  	     	  
  	   //if y is the successor of z.
  	   if(y != z) {
  		   z.setKey(y.getKey());
  		   z.setValue(y.getValue());
       }
  	   
  	   if(y.isBlack()){
  		   cnt += RB_Tree_DeleteFixup(x);  //x is the dark black which represents all the paths with one black missing(y was black)
  	   }
  	   
  	   Tree_size--;
	   return cnt;
   }

   /**
    * public String min()
    * Returns the value of the item with the smallest key in the tree,
    * or null if the tree is empty
    * Complexity O(1)
    */
   
   public String min()
   {
	   if(empty()){
		   return null;
	   }
	   return Min_Value.getValue(); 
   }
   

   /**
    * @return minimal key for internal testing - complexity O(1).
    */
   public int getMinKey()
   {
	   return Min_Value.getKey(); 
   }

   /**
    * public String max()
    * Returns the value of the item with the largest key in the tree,
    * or null if the tree is empty
    * Complexity O(1)
    */
   
   public String max()
   {
	   if(empty()){
		   return null;
	   }
	   return Max_Value.getValue(); 
   }

  /**
   * public int[] keysToArray()
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * Complexity O(n)
   */
   
  public int[] keysToArray() 
  {
	  	if(empty()){
	  		int [] arr = {};
	  		return arr;
	  	}
        int[] arr = new int[Tree_size]; 
        keysToArray_inner(this.root,arr,0);
        return arr;   
  }

  /**
   * private int keysToArray_inner(RBNode x,int[]arr,int index)
   * @param x
   * @param arr
   * @param index
   * @return the current index in the array which is going to be updated with the relevant key
   * Complexity O(n)
   */
  
  private int keysToArray_inner(RBNode x,int[]arr,int index){

		    if (x.getLeft() != leaf) {
		        index = keysToArray_inner(x.getLeft(), arr, index);
		    }
		  
		   	arr[index++] = x.getKey();

		    if (x.getRight() != leaf) {
		        index = keysToArray_inner(x.getRight(), arr, index);
		    }
		    return index; 
	    
  }

  /**
   * public String[] valuesToArray()
   * Returns an array which contains all values in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * Complexity O(n)
   */
  
  public String[] valuesToArray()
  {
	  
	  if(empty()){
	  		String [] arr = {};
	  		return arr;
	  }
      String[] arr = new String[Tree_size]; 
      valuesToArray_inner(this.root,arr,0);
      return arr;  
  }
  
  /**
   * private int valuesToArray_inner(RBNode x,String[]arr,int index)
   * @param x
   * @param arr
   * @param index
   * @return the current index in the array which is going to be updated with the relevant value
   * Complexity O(n)
   */
  
  private int valuesToArray_inner(RBNode x,String[]arr,int index){
	  	 
	    if (x.getLeft() != leaf) {
	        index = valuesToArray_inner(x.getLeft(), arr, index);
	    }
	   	arr[index++] = x.getValue();
	    if (x.getRight() != leaf) {
	        index = valuesToArray_inner(x.getRight(), arr, index);
	    }
	    return index; 
  
  }

   /**
    * public int size()
    * Returns the number of nodes in the tree.
    * precondition: none
    * postcondition: none
    * Complexity O(1)
    */
   public int size()
   {
	   return Tree_size;
   }
   
   
   /**
    * @param x
    * printing the tree.
    */
   public void TreePrint(RBNode x){
	   
	   if(x!=leaf){
		   
		   System.out.println(x.Print());
		   TreePrint(x.getLeft());
		   TreePrint(x.getRight());
		   
	   }
   }



}
  


