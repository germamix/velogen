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

import java.util.Map;
import java.util.Properties;

import ws.michalski.velogen.exceptions.VeloGenCommandException;
import ws.michalski.velogen.exceptions.VeloGenException;

/**
 * 
 * @author markus
 * @version 2014.07.04
 * 
 * 
 *          TODOs
 * 
 * 
 */
public class VeloLauncher {

	public static void main(String[] args) {

		VeloGenManager manager = new VeloGenManager();
		VeloGenCmd veloCmd = null;
		Map<String, Object> pluginContext = null;
		VeloGenEngine engine = null;
		Properties properties = null;

		try {
			
			// Parameter verarbeiten
			veloCmd = manager.getCommands(args);
			
			// Plugin ausführen
			pluginContext = manager.run();
			
			// Engine erstellen
			if(veloCmd.getCommonParam().getTemplatePath() != null){
				properties = new Properties();
				properties.setProperty("file.resource.loader.path", 
						veloCmd.getCommonParam().getTemplatePath());
			}
			
			
			// VeloEngine hollen.
			if(properties != null){
				engine = manager.getVelogenEngine(properties);
			} else {
				engine = manager.getVelogenEngine();
			}
			
			// Context vom Plugin mergen 
			engine.mergeContext(pluginContext);
			
			
			// Falls Filename als Parameter gesetzt war, wird fileCoreName mit dem Wert gesetzt
			if(veloCmd.getCommonParam().getFileName() != null){
				engine.getVeloGenSys().setFileCoreName(veloCmd.getCommonParam().getFileName());	
			}
			
			
		} catch (VeloGenCommandException | VeloGenException e) {

			System.out.println("Error: " + e.getCause().getMessage());
			manager.getUsage();
			System.exit(10);
		}


//			System.out.println(veloCmd.getCommonParam().getOutputPath());
//			System.out.println("Parameter sind: " + Arrays.toString(args));
//			System.out.println("Command ist: " + manager.getCommand());
//			System.out.println("Get-Path " + manager.getOutputPath());
		
		
		
		try {
			
			String templateName = veloCmd.getCommonParam().getTemplate();
			
			while(! engine.isComplete()){
				
				String erg = engine.generate(templateName);
				
				// Dateiname festlegen
				String fileName = engine.getVeloGenSys().getFilePrefix().trim() +
						engine.getVeloGenSys().getFileCoreName().trim() + 
						engine.getVeloGenSys().getFileSufix().trim();
				manager.writeOutputFile(fileName, erg, true);
				
				// Falls abhängige Templates vorhanden
				if(! engine.getVeloGenSys().getDependTemplate().isEmpty()){
					templateName = engine.getVeloGenSys().getDependTemplate();
				}
			}
			
		} catch (VeloGenException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(11);
		}

	}

}
