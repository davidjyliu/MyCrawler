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

public class GetAccupass {

	public static void main(String args[]) throws IOException {

		String startCat = "1";
		String startUrl = "http://www.accupass.com/search/changeconditions/r/0/" + startCat
				+ "/0/0/0/0/00010101/99991231";
		String nextUrl = "";
		String lastUrl = "";
		String strFilename = "output_" + startCat;
		Date startTime = new Date();
		Boolean goLoop = true;

		// FileInputStream fisEvent = new
		// FileInputStream("D:/CH99_Project/"+strFilename+".txt");
		// InputStreamReader isrEvent = new InputStreamReader(fisEvent,"UTF-8");
		// BufferedReader brEvent = new BufferedReader(isrEvent);

		FileOutputStream fosEvent = new FileOutputStream(
				"C:/Users/Student/Desktop/eventoutput/" + strFilename + "_" + startTime.getTime() + "txt", true);
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
				pwEvent.println(strCsvEvent);
				strCsvEvent = "";

				brEventApi.close();

			}

			
			nextUrl = docStartUrl.select("a:matchesOwn(>{1})").attr("href").toString();
			lastUrl = docStartUrl.select("a:matchesOwn(>{3})").attr("href").toString();

			if(lastUrl == ""){
				System.out.println("Breaking");
				goLoop = false;
			}else{
				startUrl = "http://www.accupass.com/search/changeconditions" + nextUrl.substring(7);
				System.out.println("===== Finished: " + startUrl + " ======");
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
