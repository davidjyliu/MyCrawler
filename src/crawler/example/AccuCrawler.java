package crawler.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.abola.crawler.CrawlerPack;

//************************************************************************************************************************
//
//目前抓取設定(時間:全部(不含已結束活動))
//"http://www.accupass.com/search/changeconditions/r/0/" + classnum + "/0/0/1/" + pagenum + "/00010101/99991231"
//
//要抓歷史資料(時間:已結束)
//"http://www.accupass.com/search/changeconditions/r/0/" + classnum + "/5/0/1/" + pagenum + "/00010101/99991231"
//
//新抓法 抓全部資料(由於使用自訂日期 不確定會不會被鎖定IP之類 慎用)
//"http://www.accupass.com/search/changeconditions/r/0/" + classnum + "/6/0/1/" + pagenum + "/00010101/99990101"
//
//抓活動的內容(num為活動eventIdNumber)
//"https://api.accupass.com/v3/events/" + num
//
//************************************************************************************************************************
//
///r 後面參數說明(起始時間,結束時間 要修改的話 要將時間參數設為6)
//http://www.accupass.com/search/changeconditions/r/參數1/參數2/參數3/參數4/參數5/參數6/00010101/99991231
//                                                  地區  分類  時間  票價  排序  頁數  起始時間 結束時間
//參數1 地區
//0全部 1台北 2新北 3桃園 4新竹 5苗栗 6台中 7南投 8彰化 9雲林 10嘉義 11台南 12高雄 13屏東 14宜花東 15基隆 16香港 17其他
//
//參數2 分類(classnum)
//0全部 999精選 1藝文 2美食 3運動 4旅遊 5科技 6娛樂 7學習 8時尚 9公益 10攝影 11商業 12健康 13其他
//
//參數3 時間
//0全部 1今天 2明天 3本周 4本周末 5已結束 6自訂日期
//
//參數4 票價
//0全部 1免費 2付費
//
//參數5 排序
//1時間近 2高瀏覽 3高收藏 4 最準確
//
//參數6 頁數(pagenum)
//由0開始

public class AccuCrawler {

	public static void main(String args[]) throws IOException {

		String startCat = "1";
		String startUrl = "http://www.accupass.com/search/changeconditions/r/0/" + startCat
				+ "/0/0/0/0/00010101/99991231";
		String nextUrl = "";
		String lastUrl = "";
		String strFilename = "output_" + startCat;
		Date startTime = new Date();
		Boolean goLoop = true;
		//int	intElementCount = 0;

		// FileInputStream fisEvent = new
		// FileInputStream("D:/CH99_Project/"+strFilename+".txt");
		// InputStreamReader isrEvent = new InputStreamReader(fisEvent,"UTF-8");
		// BufferedReader brEvent = new BufferedReader(isrEvent);

		FileOutputStream fosEvent = new FileOutputStream(
				"C:/AccupassOutput/" + strFilename + "_" + startTime.getTime() + ".txt", true);
		OutputStreamWriter oswEvent = new OutputStreamWriter(fosEvent, "UTF-8");
		BufferedWriter bwEvent = new BufferedWriter(oswEvent);
		PrintWriter pwEvent = new PrintWriter(bwEvent);

		while (goLoop == true) {

			System.out.println("===== Starting: " + startUrl + " ======");
			Document docStartUrl = CrawlerPack.start().getFromHtml(startUrl);
			Elements startElements = docStartUrl.select("div[^event]");

			for (Element event : startElements) {

				String eventRow = event.attr("event-row");
				// System.out.println(eventRow);

				int eventIdStart = eventRow.indexOf("eventIdNumber") + 16;
				int eventIdEnd = eventRow.indexOf("photoUrl") - 3;
				String eventId = eventRow.substring(eventIdStart, eventIdEnd);
//				System.out.println("ID:" + eventId);
				
				String eventHtmlUrl = "http://www.accupass.com/event/register/" + eventId;
				Document docHtmlUrl = CrawlerPack.start().getFromHtml(eventHtmlUrl);
				
				String eventKeywords = docHtmlUrl.select("meta[name~=keywords]").attr("content").toString();
//				System.out.println("Keywords:" + eventKeywords);

				String eventDesc = docHtmlUrl.select("meta[name~=description]").attr("content").toString().replace("\n"," ");
//				System.out.println("Desc:" + eventDesc);

				URL eventApiUrl = new URL("https://api.accupass.com/v3/events/" + eventId);
				InputStreamReader inEventApi = new InputStreamReader(eventApiUrl.openStream());
				BufferedReader brEventApi = new BufferedReader(inEventApi);

				String eventApiLine;
				String eventContent = null;

				while ((eventApiLine = brEventApi.readLine()) != null) {

					// int descStart = eventApiLine.indexOf("description") + 14;
					// int descEnd = eventApiLine.indexOf("content") - 3;
					// String eventDesc = eventApiLine.substring(descStart,
					// descEnd);
					// eventDesc = Jsoup.parse(eventDesc).text();
					// System.out.println(eventDesc);

					int contentStart = eventApiLine.indexOf("content") + 10;
					int contentEnd = eventApiLine.indexOf("statusName") - 3;
					eventContent = Jsoup.parse(eventApiLine.substring(contentStart, contentEnd)).text().replace("\n"," ");
//					System.out.println("Content:" + eventContent);
				}

				String strCsvEvent = "{" + eventId + "},{" + eventKeywords + "},{" + eventDesc + "},{" + eventContent
						+ "}";
				//intElementCount = intElementCount + 1;
				pwEvent.println(strCsvEvent);
				strCsvEvent = "";

				brEventApi.close();

			}

			
			nextUrl = docStartUrl.select("a:matchesOwn(>{1})").attr("href").toString();
			lastUrl = docStartUrl.select("a:matchesOwn(>{3})").attr("href").toString();

			if(lastUrl == ""){
				//System.out.println("Breaking");
				goLoop = false;
			}else{
				startUrl = "http://www.accupass.com/search/changeconditions" + nextUrl.substring(7);
				System.out.println("===== Finished: " + startUrl + " ======");
				//System.out.println("===== Crawled: " + intElementCount + " events =====");
			}
			
		}

		pwEvent.close();
		System.out.println("Done");
		Date finishTime = new Date();
		System.out.println("Start time: "+startTime);
		System.out.println("Finish time: "+finishTime);
		long finTime = finishTime.getTime();
		long staTime = startTime.getTime();
		float ETC = finTime-staTime;
		System.out.println("Elapsed time: "+ETC/1000+" seconds");
		
	}

}
