package com.internal.tasks.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;

public class InitDatabase extends HttpServlet {
	 
	    private static final long serialVersionUID = 1L;
	 
	    private Server server = null;
	    @Override
	    public void init() throws ServletException {
	        super.init();
	        server = new Server();
	        try {
	            System.out.println("Start Database");
	            HsqlProperties properties = new HsqlProperties();
	            properties.setProperty("server.database.0", "jdbc:hsqldb:hsql://localhost");         
	            properties.setProperty("server.port", "9001");
	            Server server = new Server();
	            server.setProperties(properties);
	            server.start();
	            System.out.println("Database is started");
	        } catch (Exception e) {
	            throw new ServletException(e);
	        }       
	    }
	     
	    @Override
	    public void destroy() {     
	           super.destroy();
	           server.stop();
	           server.shutdown();
	    }
}
