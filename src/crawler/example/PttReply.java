package crawler.example;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.abola.crawler.CrawlerPack;

public class PttReply {

	public static void main(String[] args) {
		
		String uri = "https://www.ptt.cc/bbs/Gossiping/M.1459053918.A.5F0.html";

		Elements elems =
				CrawlerPack.start()
			    .addCookie("over18", "1")
				.getFromHtml(uri)
				.select("#main-content") ;
			
			for( Element elem: elems.select("span,div") ){
				elem.remove();
			}
			
			System.out.println(elems.text());
	}

}
