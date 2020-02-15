public class EnhancedFor
{
	public static void main(String[] args) /* Not a comment
	* text
	Assignment Requirment*/
	{	int[] list ={1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int sum = sumListEnhanced(list);
		System.out.println("Sum of elements in list: " + sum); /* not this
		*this is
		no this*/
		System.out.println("Original List");
		printList(list);
		System.out.println("Calling addOne");
		addOne(list);
		System.out.println("List after call to addOne");
		printList(list);
		System.out.println("Calling addOneError");
		addOneError(list);
		System.out.println("List after call to addOneError. Note elements of list did not change.");
		printList(list);
	}

	// pre: list != null
	public static int sumListEnhanced(int[] list) // comment
	{	int total = 0;
		for(int val : list) /*comment*/
		{	total += val;
		}
		return total;
	}

	public static int sumListOld(int[] list)
	{	int total = 0;
		for(int i = 0; i < list.length; i++)
		{	total += list[i];
			System.out.println( list[i] );
		}
		return total;
	}

	public static void addOneError(int[] list)
	{	for(int val : list)
		{	val = val + 1;
		}
	}

	/* Comment*/

	/* Not a comment*/public static void addOne(int[] list)
	{	for(int i = 0; i < list.length; i++)
		{	list[i]++;
		}
	}

	public static void printList(int[] list)
	{	System.out.println("index, value");
		for(int i = 0; i < list.length; i++)
		{	System.out.println(i + ", " + list[i]);
		}
	}



}
