/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at 

     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ws.michalski.velogen;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import ws.michalski.velogen.exceptions.VeloGenException;

/**
 * 
 * @author markus
 * @version 2014.07.04
 * 
 */
public class VeloGenEngine {

	private VelocityEngine veloEngine = null;
	private Context veloContext = null;
	private Charset charSet = null;
	private Logger logger;
	private boolean complete = false;
	private VeloGenSys veloGenSys = null;
	private Properties veloProperties = null;
	
	
	public VeloGenEngine() throws Exception{
		super();
		veloProperties = new Properties();
		veloProperties.put(VelocityEngine.RESOURCE_LOADER,  "classpath");
		initVeloEngine(veloProperties);
	}

	public VeloGenEngine(Properties p) throws Exception{
		initVeloEngine(p);
	}

	private void initVeloEngine(Properties properties) throws Exception {
		
		logger = LogManager.getLogger();
		if(logger.isTraceEnabled()){
			logger.trace("Create VeloGenEngine");
		}
		charSet = Charset.forName(System.getProperty("file.encoding")); 
		if(logger.isDebugEnabled()){
			logger.debug("System charset encoding is " + charSet.displayName());
		}

		veloProperties = properties; 
		veloEngine = new VelocityEngine();
		veloEngine.init(veloProperties);
		
		veloContext = new VelocityContext();
		
		veloGenSys = new VeloGenSys();
		veloContext.put("VELOSYS", veloGenSys);
	}
	
	
	
	public String generate(String templateName) throws VeloGenException {
		
		Template template = null;
		String mergeString = null;
		
		try {
			template = veloEngine.getTemplate(templateName, charSet.displayName());
			Writer writer = new StringWriter();
			template.merge(veloContext, writer);
			mergeString = writer.toString();
			writer.close();
			
			complete = veloGenSys.getDependTemplate().isEmpty();
			if(logger.isDebugEnabled()){
				logger.debug("Depended template: " + veloGenSys.getDependTemplate());
				logger.debug("complete: " + complete);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new VeloGenException(e.getMessage());
		}
		
		
		return mergeString;
	}


	
	public void mergeContext(Map<String, Object> pluginContext){
		Set<String> keys = pluginContext.keySet();
		for (String key : keys) {
			veloContext.put(key, pluginContext.get(key));
		}
		
	}
	
	
	public Charset getCharSet() {
		return charSet;
	}

	public void setCharSet(Charset charSet) {
		this.charSet = charSet;
	}


	public boolean isComplete() {
		return complete;
	}


	public VeloGenSys getVeloGenSys() {
		return veloGenSys;
	}

	public Context getVeloContext() {
		return veloContext;
	}

	public Properties getVeloProperties() {
		return veloProperties;
	}


	
	
}
