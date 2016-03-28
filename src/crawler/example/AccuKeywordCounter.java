package crawler.example;

import java.util.HashMap;

public class AccuKeywordCounter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String strKeywords = ""
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂,時光之硯,movie,生活風格,美昇國際影業,藝文,Arts}"
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂,時光之硯,movie,生活風格,美昇國際影業,藝文}"
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂,時光之硯,movie,生活風格,美昇國際影業}"
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂,時光之硯,movie,生活風格}"
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂,時光之硯,movie}"
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂,時光之硯}"
				+ "{風格養成,learning,電影,格蘭花格,au_img,香功堂}"
				+ "{風格養成,learning,電影,格蘭花格,au_img}";

		strKeywords = strKeywords.replace("{","").replace("}",",");
		
		System.out.println(strKeywords);

		HashMap<String, Integer> keyCountMap = new HashMap<String, Integer>();

		String[] keyArray = strKeywords.split(",");
		
		for (String key:keyArray) {
			if (keyCountMap.containsKey(key)) {
				keyCountMap.put(key,keyCountMap.get(key)+1);
			} else {
				keyCountMap.put(key,1);
			}
		}

		System.out.println(keyCountMap);
	}

}
