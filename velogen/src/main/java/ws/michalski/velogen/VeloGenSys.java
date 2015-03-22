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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author markus
 * @version 2014.07.04
 * 
 * 
 * TODOs
 * Parametrisierung 
 *   - simpleDateFmt
 * 
 * 
 */
public class VeloGenSys {

	private final String engineName = "VeloGen";	
	private String simpleDateFmt = "dd.MM.yyyy HH:mm:ss";
	
	private final int majorVersion = 0;
	private final int minorVersion = 1;
	private final int microVersion = 2;
	
	private String filePrefix 		= "";
	private String fileCoreName		= "";
	private String fileSufix  		= "";
	private String dependTemplate 	= "";
	
	
	
	public String getTime(){
		return new SimpleDateFormat(simpleDateFmt).format(new Date());
	}
	
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	public String getFileSufix() {
		return fileSufix;
	}
	public String getEngineName() {
		return engineName;
	}
	public String getEngineVersion() {
		return majorVersion + "." + minorVersion + "." + microVersion;
	}
	public void setFileSufix(String fileSufix) {
		this.fileSufix = fileSufix;
	}
	public String getDependTemplate() {
		return dependTemplate;
	}
	public void setDependTemplate(String dependTemplate) {
		this.dependTemplate = dependTemplate;
	}

	public String getSimpleDateFmt() {
		return simpleDateFmt;
	}

	public void setSimpleDateFmt(String simpleDateFmt) {
		this.simpleDateFmt = simpleDateFmt;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVerdion() {
		return minorVersion;
	}

	public int getMicroVersion() {
		return microVersion;
	}

	public String getFileCoreName() {
		return fileCoreName;
	}

	public void setFileCoreName(String fileCoreName) {
		this.fileCoreName = fileCoreName;
	}

	
	
	
}
