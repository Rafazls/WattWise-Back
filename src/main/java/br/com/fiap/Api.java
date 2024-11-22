package br.com.fiap;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Api extends ResourceConfig {
    public Api() {
        packages("br.com.fiap");
    }
}
