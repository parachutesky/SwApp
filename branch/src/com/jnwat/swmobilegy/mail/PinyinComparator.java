package com.jnwat.swmobilegy.mail;

import java.util.Comparator;

import com.jnwat.bean.Contacts;
import com.jnwat.bean.Contacts2;



/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<Contacts2> {

	/*public int compare(Contacts o1, Contacts o2) {
		if (o1.getFirstLetter().equals("@")
				|| o2.getFirstLetter().equals("#")) {
			return -1;
		} else if (o1.getFirstLetter().equals("#")
				|| o2.getFirstLetter().equals("@")) {
			return 1;
		} else {
			return o1.getFirstLetter().compareTo(o2.getFirstLetter());
		}
	}*/

	public int compare(Contacts2 o1, Contacts2 o2) {
		if ("@".equals(o1.getFirstLetter())||"#".equals(o2.getFirstLetter())
				) {
			return -1;
		} else if ("#".equals(o1.getFirstLetter())||"@".equals( o2.getFirstLetter())
				) {
			return 1;
		} else {
			return o1.getFirstLetter().compareTo(o2.getFirstLetter());
		}
	}

}
