package solr.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadFileTest {
	public static void main(String[] args) throws IOException{
		/**
		 * http://82.177.234.240:8983/solr/select?q=ccg&fl=id,last_modified,content_type,author&wt=json
		 */
		String URL = "http://72.177.234.240:8983/solr/update/extract?literal.id=doc6&commit=true";
		String charSet = "UTF-8";
		MultipartUtility utility = new MultipartUtility(URL, charSet);
		
		File file = new File("/Users/zchen323/Downloads/test.pdf");
		
		utility.addFilePart("test", file);
		
		List<String> resp = utility.finish();
		
		System.out.println(resp);
	}
}
