package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URLEncoder;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class viewAction extends ActionSupport {
	      public static Reader reader;
	      public static SqlMapClient sqlMapper;
	      
	      private boardVO paramClass = new boardVO(); //파라미터를 저장할 객체
	      private boardVO resultClass = new boardVO();
	      
	      private int currentPage;
	      
	      private int no;
	      private String password;
	      
	      private String fileUploadPath = "c://java//upload//";
	      
	      private InputStream inputStream;
	      private String contentDispsition;
	      private long contentLength;
	      
	      public viewAction() throws IOException{
	    	  
	    	        reader = Resources.getResourceAsReader("sqlMapConfig.xml");
	    	        sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
	    	        reader.close();
	      }
	      
	      public String execute()throws Exception {
	    	  
	    	       paramClass.setNo(getNo());
	    	       sqlMapper.update("updateReadHit", paramClass);
	      
	    	       resultClass = (boardVO) sqlMapper.queryForObject("selectOne", getNo());
	    	       
	    	       return SUCCESS;
	      }
	      
	      public String download() throws Exception{
	    	  
	    	       resultClass = (boardVO) sqlMapper.queryForObject("selectOne", getNo());
	    	       
	    	       File fileInfo = new File(fileUploadPath + resultClass.getFile_savname());
	    	       
	    	       setContentLength(fileInfo.length());
	    	       setContentDisposition("attachment;filename="
	    	                     + URLEncoder.encode(resultClass.getFile_orgname(), "UTF-8"));
	    	       setInputStream(new FileInputStream(fileUploadPath
	    	    		         + resultClass.getFile_savname()));
	               return SUCCESS;
	      }
	      
	      public String checkForm() throws Exception {
	    	  
	    	     return SUCCESS;
	      }
	      
	      public String checkAction() throws Exception {
	    	  
	    	       paramClass.setNo(getNo());
	    	       paramClass.setPassword(getPassword());
	    	       
	    	       resultClass = (boardVO) sqlMapper.queryForObject("selectPassword", paramClass);
	    	       
	    	       if (resultClass == null)
	    	    	         return ERROR;
	    	       
	    	       return SUCCESS;
	      }
	      
	      public boardVO getParamClass() {
	    	        return paramClass;
	      }
	      
	      public void setParamClass(boardVO paramClass) {
	    	       this.paramClass = paramClass;
	      }

	  	public boardVO getResultClass() {
	  		return resultClass;
	  	}

	  	public void setResultClass(boardVO resultClass) {
	  		this.resultClass = resultClass;
	  	}

	  	public String getPassword() {
	  		return password;
	  	}

	  	public void setPassword(String password) {
	  		this.password = password;
	  	}

	  	public String getFileUploadPath() {
	  		return fileUploadPath;
	  	}

	  	public void setFileUploadPath(String fileUploadPath) {
	  		this.fileUploadPath = fileUploadPath;
	  	}

	  	public int getNo() {
	  		return no;
	  	}

	  	public void setNo(int no) {
	  		this.no = no;
	  	}

	  	public InputStream getInputStream() {
	  		return inputStream;
	  	}

	  	public void setInputStream(InputStream inputStream) {
	  		this.inputStream = inputStream;
	  	}

	  	public String getContentDisposition() {
	  		return contentDisposition;
	  	}

	  	public void setContentDisposition(String contentDisposition) {
	  		this.contentDisposition = contentDisposition;
	  	}

	  	public long getContentLength() {
	  		return contentLength;
	  	}

	  	public void setContentLength(long contentLength) {
	  		this.contentLength = contentLength;
	  	}

	  	public int getCurrentPage() {
	  		return currentPage;
	  	}

	  	public void setCurrentPage(int currentPage) {
	  		this.currentPage = currentPage;
	  	}
}
