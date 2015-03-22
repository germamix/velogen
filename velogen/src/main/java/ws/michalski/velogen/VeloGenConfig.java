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

import java.io.InputStream;

import javax.xml.bind.JAXB;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ws.michalski.velogen.config.Configuration;


/**
 * 
 * @author markus
 * @version 2014.07.04
 * 
 */
public class VeloGenConfig {

	private static VeloGenConfig instance;
	private static final String CONFIG_XML = "velogen-config.xml";
	
	
	private Logger logger;
	
	
	private VeloGenConfig(){
		super();
		logger = LogManager.getLogger();
	}
	
	
	public Configuration loadConfig() {
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(CONFIG_XML);
		
		Configuration config = JAXB.unmarshal(is, Configuration.class);
		logger.debug("Loading " + CONFIG_XML + ". Static-Classes: " + config.getStaticClasses().getStaticClass().size()
				+ " Plugins: " + config.getPlugins().getPlugin().size());
		return config;
	}
	

	
	public static VeloGenConfig getInstance(){
		if(instance == null){
			instance = new VeloGenConfig();
		}
		return instance;
	}

	
	
	
	
}
