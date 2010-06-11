/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.jetty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.http.HttpConsumer;
import org.apache.camel.component.http.HttpEndpoint;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Handler;

/**
 * @version $Revision$
 */
public class JettyHttpEndpoint extends HttpEndpoint {

    private boolean sessionSupport;
    private List<Handler> handlers;
    private HttpClient client;
    private JettyHttpBinding jettyBinding;
    private boolean enableJmx;
    private int requestBufferSize;
    private int responseBufferSize;

    public JettyHttpEndpoint(JettyHttpComponent component, String uri, URI httpURL) throws URISyntaxException {
        super(uri, component, httpURL);
    }

    @Override
    public JettyHttpComponent getComponent() {
        return (JettyHttpComponent) super.getComponent();
    }

    @Override
    public Producer createProducer() throws Exception {
        JettyHttpProducer answer = new JettyHttpProducer(this, getClient());
        answer.setBinding(getJettyBinding());
        return answer;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new HttpConsumer(this, processor);
    }   

    public void setSessionSupport(boolean support) {
        sessionSupport = support;
    }

    public boolean isSessionSupport() {
        return sessionSupport;
    }
    
    public void setRequestBufferSize(int bufferSize) {
        requestBufferSize = bufferSize;
    }
    
    public int getRequestBufferSize() {
        return requestBufferSize;
    }
    
    public void setResponseBufferSize(int bufferSize) {
        responseBufferSize = bufferSize;
    }
    
    public int getResponseBufferSize() {
        return responseBufferSize;
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public HttpClient getClient() {
        if (client == null) {
            return getComponent().getHttpClient();
        }
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public synchronized JettyHttpBinding getJettyBinding() {
        if (jettyBinding == null) {
            jettyBinding = new DefaultJettyHttpBinding();
            jettyBinding.setHeaderFilterStrategy(getHeaderFilterStrategy());
            jettyBinding.setThrowExceptionOnFailure(isThrowExceptionOnFailure());
        }
        return jettyBinding;
    }

    public void setJettyBinding(JettyHttpBinding jettyBinding) {
        this.jettyBinding = jettyBinding;
    }

    public boolean isEnableJmx() {
        return this.enableJmx;
    }

    public void setEnableJmx(boolean enableJmx) {
        this.enableJmx = enableJmx;
    }
}
