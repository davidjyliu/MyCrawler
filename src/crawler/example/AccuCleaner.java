package crawler.example;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

public class AccuCleaner {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub

		//Test input from crawler
		String strDirty = "{1603141038151750454510},"
				+ "{風格養成 ,learning ,電影 ,格蘭花格 ,au_img ,香功堂 ,時光之硯 ,movie ,生活風格 ,美昇國際影業 ,藝文 ,Arts},"
				+ "{美昇國際影業與格蘭花格聯手催生大人的課外活動課，給下班後想來點不一樣的你！極限量名額請把握好吃好玩好好學的難得機會！},"
				+ "{───────────────────────────影談。味｜　大人的課外活動課 電影講座不該只是排排坐，互動的方式應該有更多創意組合。 Maison Motion美昇國際影業 "
				+ "與 Glenfarclas格蘭花格， 共同推出【影談。味】大人的課外活動課，將定期在不同場域舉辦。 以限量名額開放給有心人，給你好吃好酒與好學問。 讓我們一同在城市的不同空間，"
				+ "激盪生活與品味的更多可能。 ────────────────────────影談。味｜　第一課：他與他的電影奇幻人生 香功堂主，一個用書寫電影耕耘上萬字走到破千萬人次點擊的上班族； "
				+ "時光之硯，一個從科技業出走投身電影工作的熱血份子。 愛看電影這件事，對兩個上班族來說，是如何在他們的生活中引起化學變化？ 【影談。味】首堂課外活動課，邀請兩個愛電影的男人首度相談。"
				+ " 在好酒與美食之間，與你分享屬於他們金馬奇幻影展最難忘的， 與他們如何把愛電影搞成了一樁不思議的奇幻人生。 　　───────────────────────────────────"
				+ "影談。味｜　　組成 【前味】行家劃重點 (10min) 由美昇國際影業帶來最新發行電影〈羊男的冰島冒險〉與〈巴黎走音天后〉觀影小記 【中味】影人相談中 (70min含中場休息10min) "
				+ "由《時光之硯》張硯拓與《香功堂》堂主兩人首度公開對談他們的電影奇幻人生兩三事 【後味】來點大人味 (中場休息與報到前皆可隨時參與) 現場備有機動威士忌品酒學，給你多懂一點大人的滋味 "
				+ "───────────────────────────影談。味｜　　學費 課外活動餐酒知識飽足價 每人貳佰伍拾元整 (極限量名額30枚) "
				+ "──────────────────────────────────影談。味｜　　地點 思劇場Thinkers' Theater(台北市大同區迪化街一段32巷1號3樓) "
				+ "從捷運北門站3號出口，出來後沿塔城街直走進迪化街，在迪化街32巷左轉就到了，步行約8分鐘 從捷運中山站或西門站騎Ubike約5~10分鐘，可達永樂市場（民樂街、南京西路223巷）"
				+ "Ubike站 ──────────────────────────────────影談。味｜　　主辦 　 我們為電影癡狂，因電影存在。我們深信不管媒材如何改變，人們永遠屈服於銀幕的魔力。 "
				+ "美昇國際影業期許成為一個有機體，邀你一同進入影像的世界對話和辯論， 在動人的故事裡產生彼此互文的關連，在以時光雕刻的光影交錯中分享、交會。 Glenfarclas "
				+ "格蘭花格創廠於1836年，長久以來由家族獨立經營，目前已經傳承至第六代。 在其他酒廠皆陸續被各大財團併購瓜分、逐漸失去獨立自主性的大環境下，仍然堅持著由家族獨立經營， "
				+ "遵循真正的傳統造酒工法，並以高成本的優質的Oloroso雪莉桶威士忌聞名全球。 在百家爭鳴的產業裡面，Glenfarclas格蘭花格成為特立獨行的一股清流，真正不隨波逐流的行家，"
				+ "才懂得欣賞這樣獨特風格的酒廠。 《影談‧味》活動提醒 酒後不開車 安全有保障}";

		//Set regex: [NOT(^) Chinese chars(\u4E00-\u9FFF) English/Number chars(\\w) { (\\{) } (\\})]
		String strPattern = "[^\u4E00-\u9FFF\\w\\{\\}]+";
		
		//Prepare PrintWriter to write cleaning results
		Date startTime = new Date();
		FileOutputStream fosEvent = new FileOutputStream("C:/AccupassOutput/clean" + startTime.getTime() + ".txt",
				true);
		OutputStreamWriter oswEvent = new OutputStreamWriter(fosEvent, "UTF-8");
		BufferedWriter bwEvent = new BufferedWriter(oswEvent);
		PrintWriter pwEvent = new PrintWriter(bwEvent);

		//Remove special characters, format input from crawler
		String strClean = strDirty.replaceAll(strPattern, ",").replaceAll("\\{,", "{").replaceAll(",\\}", "}");

		pwEvent.println(strClean);
		pwEvent.close();
	}

}
