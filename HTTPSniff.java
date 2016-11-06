import java.net.*;
import java.io.*;
public class HTTPSniff {  
	public static int BUFFER_SIZE = 90000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket server = new ServerSocket(8888);
			System.out.println("##################################");
			System.out.println("#                                #");
			System.out.println("#           HTTP Sniff           #");
			System.out.println("#                                #");
			System.out.println("#    Developer By Huang Andrew   #");
			System.out.println("#                                #");
			System.out.println("##################################");
            System.out.println("Proxy Port is 8888......");
			System.out.println("Listeneter....");
			do{
			Socket socket = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	  	    DataOutputStream outdata = new DataOutputStream(socket.getOutputStream());
	        String url=in.readLine();
			URL urls = null;
			try{
			if(url.contains("GET")){
				urls = new URL(url.replaceAll("GET", "").replace("HTTP/1.1", ""));
			}else{
				urls = new URL(url.replaceAll("POST", "").replaceAll("HTTP/1.1", ""));
		    }
		    HttpURLConnection http = (HttpURLConnection)urls.openConnection();
		    http.setDoInput(true);
		    http.setDoOutput(true);
		    http.connect();
		    InputStream is = http.getInputStream();
		    byte by[] = new byte[ BUFFER_SIZE ];
            int index = is.read( by, 0, BUFFER_SIZE );
            while ( index != -1 )
            {
              outdata.write( by, 0, index );
              index = is.read( by, 0, BUFFER_SIZE );
              if(url.contains("GET")){
     	         System.out.println("GET:"+url.replaceAll("GET", "").replace("HTTP/1.1", ""));
     	      }else{
     	         System.out.println("POST:"+url.replaceAll("POST", "").replace("HTTP/1.1", ""));
     	      }
     	      System.out.println("ResponseCode: "+http.getResponseCode());
     	      System.out.println("ContentType: "+http.getContentType());
     	      System.out.println("ContentLength: "+http.getContentLength());
     	      System.out.println("Header: "+http.getHeaderFields());
     	      System.out.println("==================================================================");
            }
            outdata.flush();
            if(is != null){
            	is.close();
            }
			if (outdata != null) {
	           outdata.close();
	        }
	        if (in != null) {
	           in.close();
	        }
	        if (socket != null) {
	           socket.close();
	        }
		    }catch(Exception e){}
			}while(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
