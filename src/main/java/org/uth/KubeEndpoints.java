package org.uth;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.client.OpenShiftClient;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/endpoints")
public class KubeEndpoints
{
  public KubeEndpoints() {}

//  @Inject
//  KubernetesClient client;

  @Inject
  OpenShiftClient client;

  @GET
  @Path("/pods")
  @Produces(MediaType.TEXT_PLAIN)
  public String envtest( @QueryParam("namespace") String namespace )
  {
    System.out.println( namespace );
    System.out.println( "Found " + client.projects().list().getItems().size() + " projects...");

    StringBuffer response = new StringBuffer();

    for(Namespace project : client.namespaces().list().getItems())
    {
      response.append( project.getMetadata().getName() + "/n" );
    }

    for( Pod pod : client.pods().inNamespace(namespace).list().getItems())
    {
      //response.append( pod.toString() + "\n" );
      //response.append( pod.getMetadata().toString() + "\n" );
      response.append( pod.getMetadata().getName() + ", " + pod.getMetadata().getLabels() + "\n" );
    }

    return response.toString();
  }
}