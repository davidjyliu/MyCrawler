package crawler.example;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.abola.crawler.CrawlerPack;


/**
 * 資料探索練習 Facebook Graph Api Search 
 * 
 * 重點
 * 1. 利用Graph Api調整出需要的資料
 * 2. 取得一組Access Token (long term token)
 * 3. 試著用你會的方式，先行探索資料
 * 
 * @author Abola Lee
 *
 */



public class FBInsights {
	
	public static void main(String[] args) {
		
		//FB generated code:
		//"https://graph.facebook.com/v2.5/search?q=%E9%9D%A0%E5%8C%97&type=page&fields=id%2Cname%2Clikes%2Ctalking_about_count&access_token=CAACEdEose0cBAI1qiXoO1l7yjPeeZCToTLeavcRArGeka6YefF2hZByjomR9wfqK4l7awOL9iAAK6ZCVf3NgauiPqS55SlFMPqauTyH7DjYtb2IdqTmGnuRhBZA3RzFkwh7wjZCp2bqoZAoPeuZAaZCk1SiwR91gqQjSBybwUovHpHf4tFTLAwwHga5jQGudbnunRID2Hwj2SLVBWZCizmZA8O"

		String accessToken = "CAACEdEose0cBAI1qiXoO1l7yjPeeZCToTLeavcRArGeka6YefF2hZByjomR9wfqK4l7awOL9iAAK6ZCVf3NgauiPqS55SlFMPqauTyH7DjYtb2IdqTmGnuRhBZA3RzFkwh7wjZCp2bqoZAoPeuZAaZCk1SiwR91gqQjSBybwUovHpHf4tFTLAwwHga5jQGudbnunRID2Hwj2SLVBWZCizmZA8O";
		
		// 遠端資料路徑
		// >>>Fill here<<< 
		String uri = 
				"https://graph.facebook.com/v2.5/"
				+ "search?q=%E9%9D%A0%E5%8C%97&"
				+ "&type=page"
				+ "&fields=id,name,likes,talking_about_count"  // 補完
				+ "&access_token="+ accessToken;

		// Jsoup select 後回傳的是  Elements 物件
		Elements elems =
				CrawlerPack.start()
				.getFromJson(uri)
				.select("data");
		
		String output = "id,按讚數,名稱,討論人數\n";
		
		// 遂筆處理
		for( Element data: elems ){
			
			// 如何取出資料??
			// >>>Fill here<<< 
			String id =  data.select("id").text();
			String likes = data.select("likes").text();
			String name = data.select("name").text();
			String talking_about_count = data.select("talking_about_count").text();
			
			output += id+","+likes+",\""+name+"\","+talking_about_count+"\n";
		}
		
		System.out.println( output );
	} 
}
