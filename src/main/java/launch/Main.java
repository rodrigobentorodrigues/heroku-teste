package launch;

import java.io.File;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 *
 * @author rodrigobento
 */
public class Main {

    public static void main(String[] args) {

        String diretorioWebApp = "src/main/webapp";

        Tomcat tomcat = new Tomcat();

        String porta = System.getenv("PORT");
        if (porta == null || porta.isEmpty()) {
            porta = "8080";
        }

        tomcat.setPort(Integer.valueOf(porta));

        try {
            StandardContext contexto = (StandardContext) tomcat.addWebapp("/",
                    new File(diretorioWebApp).getAbsolutePath());
            
            File webInf = new File("target/classes");
            
            WebResourceRoot recursos = new StandardRoot(contexto);
            recursos.addPreResources(new DirResourceSet(recursos, "/WEB-INF/classes",
                    webInf.getAbsolutePath(), "/"));
            
            contexto.setResources(recursos);
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
        }

    }

}
