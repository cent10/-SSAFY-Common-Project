package com.ssafy.jara.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class JsonFilter extends OncePerRequestFilter {
	
	protected static Log log = LogFactory.getLog(JsonFilter.class);
	
	// Json value XSS Filter를 위한 필터링 값 리스트
	public static ArrayList<String> checkList = new ArrayList<String>(Arrays.asList("<script>", "</script>", "#{", "#{}", "${", "${}"));   
	
	@Override
	protected void doFilterInternal(
		HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
		throws ServletException, IOException {

		chain.doFilter(new SimpleXssFilterInternal(req), resp);
	}
	
	public static class SimpleXssFilterInternal extends HttpServletRequestWrapper {
		
		private byte[] body;
		
		public SimpleXssFilterInternal(HttpServletRequest request) {
			super(request);
			
			try {
				
				InputStream is = request.getInputStream();
				
				InputStreamReader isr = new InputStreamReader(is, "UTF-8"); // 한글 깨짐 해결
				
				if(isr != null) {
					StringBuffer sb = new StringBuffer();
					
					while(true) {
						int data = isr.read();
						
						if(data < 0) 
							break;
						
						sb.append((char) data);
					}
					
					log.info("============ sb : " + sb.toString());
					
					String result = doWork(sb.toString());
					
					log.info("============ result : " + result);
					
					body = result.getBytes(StandardCharsets.UTF_8);
					
					log.info("============ body : " + body);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public ServletInputStream getInputStream() throws IOException {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
			
			return new ServletInputStream() {
				
				@Override
				public int read() throws IOException {
					return byteArrayInputStream.read();
				}
				
				@Override
				public void setReadListener(ReadListener listener) {}
				
				@Override
				public boolean isReady() {
					return true;
				}
				
				@Override
				public boolean isFinished() {
					return byteArrayInputStream.available() == 0;
				}
			};
		}
		
		private String doWork(String input) {
			
			for (int i = 0; i < checkList.size(); i++) {
				String s = checkList.get(i);
				
//				log.info("============ s : " + s);
				
				if(input.indexOf(s) >= 0) {
					input = input.replaceAll(s, StringEscapeUtils.escapeHtml4(s));
				} 
			}
			
			log.info("============ input : " + input);
			
			return input;

//			StringBuffer sb = new StringBuffer();
			
//			for (int i = 0; i < input.length(); i++) {
//				char c = input.charAt(i);
//				if(c == '(' || c == ')' || c == '<' || c == '>' || c == '$' || c == '#' || c == '&') {
//					sb.append(StringEscapeUtils.escapeHtml4(String.valueOf(c)));
//				} else {
//					sb.append(c);
//				}
//				
//			}
			
//			return sb.toString();
		}
	}
	
}