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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.JCommander;

/**
 * 
 * @author markus
 * @version 2014.07.04
 * 
 * 
 * TODOs
 * 
 * 
 */
public class VeloGenCmd {

	protected JCommander jc = null;
	protected VeloCommonParam commonParam = null;
	
	

	private Logger logger;
	
	public VeloGenCmd(){
		
		logger = LogManager.getLogger();
		
		commonParam = new VeloCommonParam();
		
		jc = new JCommander(commonParam);
		jc.setProgramName("velogen");
		
	}
	
	protected void usage(){
		if(jc != null){
			jc.usage();
		} else {
			logger.error("JCommander is null.");
		}
	}
	
	protected void usage(String command){
		jc.usage(command);
	}
	
	protected void parse(String[] args){
		jc.parse(args);
	}
	
	
	protected void addCommand(String name, Object obj){
		jc.addCommand(name, obj);
	}

	public VeloCommonParam getCommonParam() {
		return commonParam;
	}

	public void setCommonParam(VeloCommonParam commonParam) {
		this.commonParam = commonParam;
	}

	public Logger getLogger() {
		return logger;
	}
	
	
	
}
