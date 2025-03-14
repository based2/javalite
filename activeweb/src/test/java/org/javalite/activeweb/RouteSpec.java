/*
Copyright 2009-(CURRENT YEAR) Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/

package org.javalite.activeweb;

import app.controllers.RestfulController;
import app.controllers.SimpleController;
import org.junit.Test;

/**
 * @author Igor Polevoy
 */
public class RouteSpec extends RequestSpec {

    SimpleController simpleController = new SimpleController();
    RestfulController restfulController = new RestfulController();

    //////////////// START STANDARD ACTIONS //////////////////////////////
    @Test
    public void shouldReturnGETMethodForActionWithoutAnnotation(){
        request.setMethod("GET"); // any method will do, avoiding NPE

        a(new Route(simpleController, "index", HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(simpleController, "new1",HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(simpleController, "destroy", HttpMethod.DELETE).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(simpleController, "create", HttpMethod.POST).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(simpleController, "update", HttpMethod.PUT).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(simpleController, "destroy", HttpMethod.DELETE).actionSupportsHTTPMethod()).shouldBeTrue();
    }

    @Test(expected = ActionNotFoundException.class)
    public void shouldThrowExceptionForNonExistentAction(){
        the(new Route(simpleController, "blah", HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
    }


    //////////////// END STANDARD ACTIONS //////////////////////////////

    @Test
    public void shouldDetectRestfulController(){
        a(restfulController.restful()).shouldBeTrue();
        a(simpleController.restful()).shouldBeFalse();
    }

    /*
    Spec:
        GET 	/photos 	            index 	        display a list of all photos
        GET 	/photos/new_form 	    new_form        return an HTML form for creating a new photo
        POST 	/photos 	            create 	        create a new photo
        GET 	/photos/:id 	        show            display a specific photo
        GET 	/photos/:id/edit_form   edit_form 	    return an HTML form for editing a photo
        PUT 	/photos/:id 	        update          update a specific photo
        DELETE 	/photos/:id 	        destroy         delete a specific photo
*/

    @Test
    public void shouldDetectRestfulControllers(){
        request.setMethod("GET"); // any method will do, avoiding NPE

        a(new Route(restfulController, "index", HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "newForm", HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "show", HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "editForm", HttpMethod.GET).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "create", HttpMethod.POST).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "update", HttpMethod.PUT).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "destroy", HttpMethod.DELETE).actionSupportsHTTPMethod()).shouldBeTrue();
        a(new Route(restfulController, "options", HttpMethod.OPTIONS).actionSupportsHTTPMethod()).shouldBeTrue();
    }
}
