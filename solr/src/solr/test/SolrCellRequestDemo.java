package solr.test;

// https://cwiki.apache.org/confluence/display/solr/Uploading+Data+with+Solr+Cell+using+Apache+Tika
	public class SolrCellRequestDemo {
		  public static void main (String[] args) throws IOException, SolrServerException {
		    SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/my_collection").build();
		    ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
		    req.addFile(new File("my-file.pdf"));
		    req.setParam(ExtractingParams.EXTRACT_ONLY, "true");
		    NamedList<Object> result = client.request(req);
		    System.out.println("Result: " + result);
		}

