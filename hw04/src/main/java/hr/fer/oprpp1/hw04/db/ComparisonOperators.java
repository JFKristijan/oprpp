package hr.fer.oprpp1.hw04.db;

/**Class that represents ComparionOperators as static methods
 * @author Fran Kristijan Jelenčić
 *
 */
public class ComparisonOperators {
	public static final IComparisonOperator LESS = (value1,value2)->(value1.compareTo(value2)<0);
	public static final IComparisonOperator LESS_OR_EQUALS = (value1,value2)->(value1.compareTo(value2)<=0);
	public static final IComparisonOperator GREATER = (value1,value2)->(value1.compareTo(value2)>0);
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1,value2)->(value1.compareTo(value2)>=0);
	public static final IComparisonOperator EQUALS = (value1,value2)->(value1.compareTo(value2)==0);
	public static final IComparisonOperator NOT_EQUALS = (value1,value2)->(value1.compareTo(value2)!=0);
	public static final IComparisonOperator LIKE = (value1,value2)->{
		int index = value2.indexOf("*");
		if(index==-1) 
			return value1.equals(value2);
		String lstrana = value2.substring(0,index);
		String dstrana = value2.substring(index+1);
		return value1.startsWith(lstrana)&&value1.endsWith(dstrana);
	};
}
