
/*
 *	Names: Tom Lapid, Paz Reingold
 *	User Names: tomlapid, pazreingold
 *	Id's: 203135256, 208209734
 */
/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{


	private HeapNode heapMin;
	private int heapSize;
	private HNLinkedList<HeapNode> theHeap;

	
	public BinomialHeap(){
		this.heapSize = 0;
		this.theHeap = new HNLinkedList<HeapNode>();
	}
	
	
	public HNLinkedList<HeapNode> getTheHeap() {
		return theHeap;
	}


	public void setTheHeap(HNLinkedList<HeapNode> theHeap) {
		this.theHeap = theHeap;
	}

	public void setHeapMin(HeapNode heapMin) {
		this.heapMin = heapMin;
	}

	
   /**
    * public boolean empty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    * Complexity - O(1). 
    */
    public boolean empty()
    {
    	 return this.heapSize == 0;
    }
		
   /**
    * public void insert(int value)
    *
    * Insert value into the heap 
    * Complexity - O(logn).
    */
    public void insert(int value) 
    {    
    	
    	HNLinkedList<HeapNode>.Cell currCell = theHeap.getFirst();
       	HeapNode inserted = new HeapNode(value,0);
       	
       	if(currCell==null){//empty heap
       		theHeap.addFirst(inserted);
       		this.heapSize++;
       		this.heapMin = inserted;
       		return; 
       	}
       	
       	while (currCell!=null) {
       		
      	   	HeapNode currNode = currCell.getNode();
        	if(currNode.getDegree()== inserted.getDegree())
        	{
        		inserted = meldNodes(inserted,theHeap.removeFirst()); //theHeap.removeFirst() returns currNode
        	}
        	
        	else{
        		theHeap.addFirst(inserted);
        		if(inserted.getValue()<=this.heapMin.getValue()){
        			this.heapMin = inserted;
        		}
        		this.heapSize++;
        		return;
        	}
           	currCell = currCell.next();
        }

       	
		theHeap.addFirst(inserted);
		this.heapMin = inserted;
		this.heapSize++;

    }
    
    /**
     * Complexity - O(1). 
     */
    
    private HeapNode meldNodes (HeapNode tree1, HeapNode tree2)
    {

    	if(tree1.getValue()<=tree2.getValue()){
    		tree1.getChildren().addLast(tree2);
    		tree1.setDegree(tree1.getDegree()+1);
    		return tree1;
    	}
    	
    	else{
    		tree2.getChildren().addLast(tree1);
    		tree2.setDegree(tree2.getDegree()+1);
    		return tree2;
    	}

    }

    /**
     * public void deleteMin()
     *
     * Delete the minimum value
     * Complexity - O(logn).
     */
     public void deleteMin()
     {
     	if(empty()){
    		 return;
    	}
 
     	HNLinkedList<HeapNode>childList = heapMin.getChildren();
     	BinomialHeap childrenHeap = new BinomialHeap();
     	childrenHeap.setTheHeap(childList);
     	this.theHeap.remove(this.heapMin);
       	this.heapSize-=1; //the actual deleted node.
     	meld(childrenHeap);

     }

     /**
      * public int findMin()
      *
      * Return the minimum value
      * Complexity - O(1).
      */
    public int findMin()
    {
    	if(empty()){
    		return -1;
    	}
    	return this.heapMin.getValue();
    } 
    
    /**
     * public void meld (BinomialHeap heap2)
     *
     * Meld the heap with heap2
     * Complexity - O(logn).
     */
     public void meld (BinomialHeap heap2)
     {
    	
    	HNLinkedList<HeapNode> Result = new HNLinkedList<HeapNode>();
     	HNLinkedList<HeapNode>.Cell heap1currCell = this.theHeap.getFirst();
     	HNLinkedList<HeapNode>.Cell heap2currCell = heap2.getTheHeap().getFirst();
   	    HeapNode balance = null;
     	 
    	while (heap1currCell!=null && heap2currCell!=null) { 
     		 HeapNode node1 = heap1currCell.getNode();
     		 HeapNode node2 = heap2currCell.getNode();  
     		
     		//case1
     		 if (balance != null && balance.getDegree()<node2.getDegree() &&
     				 balance.getDegree()<node1.getDegree() )
     		 {
     			 Result.addLast(balance);
     			 balance=null;
     		 }
     		 
     		 else if(node1.getDegree()==node2.getDegree())
     		 {
     			 //case2
     			 if(balance != null){ //there is balance
     				 Result.addLast(balance);
     				 balance = meldNodes(node1,node2);
     				 heap1currCell =heap1currCell.next();
     	     		 heap2currCell =heap2currCell.next();
     			 }
     			 //case3
     			 else{
      				 balance = meldNodes(node1,node2);
      				 heap1currCell =heap1currCell.next();
      	     		 heap2currCell =heap2currCell.next();
     			 }
     		 }
     		 
     		 else
     		 {
     			 HeapNode minNode = smallerDegreeNode(node1,node2);
     			 //case4
     			 if(balance != null){
     				 balance = meldNodes(minNode,balance);
     			 }
     			 //case5
     			 else{
     				 Result.addLast(minNode);
     			 }
     			 if(minNode==heap1currCell.getNode()){
     				 heap1currCell =heap1currCell.next();
     			 }
     			 else{
     				heap2currCell =heap2currCell.next();
     			 }
     		 }

     	 }
    	
     	 
    	//in case one of the heaps has more heap nodes.
    	if(heap1currCell!=null){
    		Result = meldWithOneEmptyHeap(heap1currCell,Result,balance);
    	}
    	else{
    		Result = meldWithOneEmptyHeap(heap2currCell,Result,balance);
    	}

     	//updating new heap minimum.
    	updateHeapMin(Result);
        this.heapSize+=heap2.heapSize;
        this.theHeap = Result;
        
     }
     
     /**
      * helper method which run through the heap cells and update the HeapMin field. 
      * @param Result
      * Complexity  - O(logn)
      */
     private void updateHeapMin(HNLinkedList<HeapNode> Result){
    	 
      	HNLinkedList<HeapNode>.Cell currCell = Result.getFirst();
      	if(currCell == null){ //two empty heaps.
      		this.heapMin=null;
      		return; 
      	}
      	
      	this.heapMin =currCell.getNode();
      	while(currCell!=null){
      		if(currCell.getNode().getValue()<this.heapMin.getValue()){
      			this.heapMin=currCell.getNode();
      		}
      		currCell=currCell.next();
      	}

     }

     /**
      * helper method which melds the rest of the given heap of heapcurrCell into the Result heap.
      * @param heapcurrCell
      * @param Result
      * @param balance
      * @return
      * Complexity- O(logn)
      */
     private HNLinkedList<HeapNode> meldWithOneEmptyHeap(HNLinkedList<HeapNode>.Cell heapcurrCell,
    		 HNLinkedList<HeapNode> Result, HeapNode balance){
    	 
     	 while(heapcurrCell!=null){
     		 if(balance!=null){
     			 //case 1
     			 if(balance.getDegree()<heapcurrCell.getNode().getDegree()){
     				Result.addLast(balance);
     				balance=null;
     			 }
     			 else{
     				//case 2
     				balance=meldNodes(balance,heapcurrCell.getNode());
     			 }
     		 }
     		 else{
     			//case 3
     			Result.addLast(heapcurrCell.getNode());
     		 }
     		heapcurrCell =heapcurrCell.next();
     	 }
     	 if(balance!=null)
     	 {
			Result.addLast(balance);
     	 }
    	 return Result;
     }


     /**
      * helper method which returns the HeapNode with the smaller degree among the two that were given.
      * @param node1
      * @param node2
      * @return
      * Complexity- O(1)
      */
     private HeapNode smallerDegreeNode(HeapNode node1,HeapNode node2){
     	if(node1.getDegree()<node2.getDegree()){
     		return node1;
     	}
     	else{
     		return node2;
     	}
     }

     /**
      * public int size()
      *
      * Return the number of elements in the heap
      * Complexity- O(1)  
      */
    public int size()
    {
    	return this.heapSize;
    }
    
    /**
     * public int minTreeRank()
     *
     * Return the minimum rank of a tree in the heap.
     * Complexity- O(1)
     */
     public int minTreeRank()
     {
     	if(empty()){
     		return -1; 
     	}
         return theHeap.getFirst().getNode().getDegree(); 
     }
	
   /**
    * public boolean[] binaryRep()
    *
    * Return an array containing the binary representation of the heap.
    * Complexity- O(logn)
    */
    public boolean[] binaryRep()
    {
    	
    	if(theHeap.isEmpty()){
    		return new boolean[1];
    	}
    	boolean[] arr = new boolean[theHeap.getLast().getNode().getDegree() + 1]; 
    	HNLinkedList<HeapNode>.Cell currCell = theHeap.getFirst();
       	while(currCell != null){
    		arr[currCell.getNode().getDegree()]=true;
    		currCell = currCell.next();
    	}
        return arr; 
        
    }

   /**
    * public void arrayToHeap()
    *
    * Insert the array to the heap. Delete previous elemnts in the heap.
    * Complexity- O(nlogn)
    */
    public void arrayToHeap(int[] array)
    {
    	this.setTheHeap(new HNLinkedList<HeapNode>());
    	this.heapSize=0;
        for(int elem : array){
        	this.insert(elem);
        }
    }
	
   /**
    * public boolean isValid()
    * checks beside the valid tree that there are no more the one tree with one rank.
    * Returns true if and only if the heap is valid.
    * Complexity- O(n) - //////////////////////the heap n
    */
    
     
    public boolean isValid() 
    {
    	if(empty()){
    		return true;
    	}
    	
    	int[]check = new int[theHeap.getLast().getNode().getDegree() + 1];
    	HNLinkedList<HeapNode>.Cell currCell = theHeap.getFirst();
    	
       	while(currCell != null){
       		int currCellDegree = currCell.getNode().getDegree();
       		if(check[currCellDegree]>0){
       			return false ; //more than 1 with same rank or 
       		}
       		if(!isValidBinTree(currCell.getNode())){
       			return false; //invalid binomial tree.
       		}
       		check[currCellDegree]++;
    		currCell = currCell.next();
    	}
    	return true; 
    }
    
    
    

    
    /**
     * helper method that returns true if and only if the heapNode is valid.
     * @param node
     * @return
     *  return the tree is valid if for each node:
     *	a. his degree is k --> it has k children
     *	b. he is smaller than all of his children
     *	c.each child with degree no more than k-1
     *	d.has one child of every degree 0---k-1
     * Complexity- O(n) 
     */
    public boolean isValidBinTree(HeapNode node) 
    {	
       	int[]check = new int[node.getDegree()];
    	//first,must have k children.
    	if(node.getDegree()!=node.getChildren().size()){
    		return false;
    	}
    	
    	HNLinkedList<HeapNode>.Cell currCell = node.getChildren().getFirst();
    	while(currCell!=null){
    		
    		HeapNode child = currCell.getNode();
    		if(node.getValue()<=child.getValue()){
    			if(!isValidBinTree(child) || child.getDegree() >= node.getDegree()){
    				return false;
    			}
    			if(check[child.getDegree()]>0){
    				return false;
    			}
    			check[child.getDegree()]++;
    		}
    		else{
    			return false;
    		}
       		currCell=currCell.next();
    	}
    	return true;

    }
    



    /**
     * public class HeapNode
     * 
     * If you wish to implement classes other than BinomialHeap
     * (for example HeapNode), do it in this file, not in 
     * another file 
     *  
     */	
 	public class HeapNode{
 		

 		private HNLinkedList<HeapNode>children;
 		private int degree;
 		private int value; 
 		
 		public HeapNode(int value,int degree){
 			this.children = new HNLinkedList<HeapNode>();
 			this.degree = degree;
 			this.value = value;
  		}


 		public HNLinkedList<HeapNode> getChildren() {
 			return this.children;
 		}

 		public void setChildren(HNLinkedList<HeapNode> children) {
 			this.children = children;
 		}

 		public int getDegree() {
 			return degree;
 		}

 		public void setDegree(int degree) {
 			this.degree = degree;
 		}

 		public int getValue() {
 			return value;
 		}

 		public void setValue(int value) {
 			this.value = value;
 		}
 	 	

 		
 	}

	public class HNLinkedList<HeapNode> {
    	
      	 public class Cell{
      		
      		private HeapNode node;
      		private Cell next;
      		
      		public Cell(HeapNode node,Cell next){
      			this.node=node;
      			this.next=next;
      		}
      		
      		public HeapNode getNode(){
      			return node;
      			
      		}
      		
      		public Cell next(){
      			return next;
      			
      		}

      		public void setNext(Cell next){
      			this.next=next;
      		}
      		
      		public void setNode(HeapNode node){
      			this.node=node;
      		}
      		
          		
      	}

      	 
      	private Cell first;
      	private Cell last;
      	private int size;
      	
      	public  HNLinkedList(){ 
      		
      		this.first = null;
      		this.last = null;
      		this.size = 0;

      	}

      	public Cell getFirst(){
      		return first;
      	}
      	
      	public Cell getLast(){
      		return last;
      	}

      	public boolean isEmpty(){
      		return this.size == 0;
      	}

      	/**
      	 * remove the first HeapNode from a HeapNode Linked List and returns it
      	 * Complexity - O(1);
      	 * @return
      	 */
      	public HeapNode removeFirst(){
      		if(this.size == 0){
      			return null;
      		}
      		else if(this.size == 1){
      			this.last = null;
      		}
      	
      		Cell tmp = this.first;
      		this.first = first.next();
      		this.size--;
      		return tmp.getNode();
      	}

      	
      	/**
      	 * remove the given HeapNode from a HeapNode Linked List.
      	 * Complexity - O(logn);
      	 */
      	public void remove(HeapNode node){
   		
      		if(node==this.first.getNode()){
      			removeFirst();
      			return;
      		}
      		Cell curr = this.first;
      		Cell prev = curr;
      		while(curr.getNode() != node){
      			prev = curr;
      			curr = curr.next;
      		}
      		if(node == this.last){
      			this.last = prev;
      		}
      		prev.setNext(curr.next);
      		this.size--;
      	}
      	
      	/**
      	 * add the given HeapNode at the start of the HeapNode Linked List.
      	 * Complexity - O(1);
      	 * @return
      	 */
      	public void addFirst(HeapNode node){
      		Cell first = new Cell(node,this.first);
      		this.first = first;
      		if(this.size == 0){
      			this.last = first;
      		}
      		this.size++;
      	}
      	
      	/**
      	 * add the given HeapNode at the end of the HeapNode Linked List.
      	 * Complexity - O(1);
      	 * @return
      	 */
      	public void addLast(HeapNode node){
      		
      		Cell newlast=new Cell(node,null);
      		if(isEmpty()){
      			this.first = newlast;
      			this.last = newlast;
      		}
      		else{
      			this.last.setNext(newlast);
      			this.last = newlast;
      		}
      		this.size++;
      		
      	}
      	
      	public Integer size(){
      		return this.size;
      	}


      }



}

