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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.context.Context;

import ws.michalski.velogen.config.Configuration;
import ws.michalski.velogen.config.Plugin;
import ws.michalski.velogen.config.StaticClass;
import ws.michalski.velogen.exceptions.VeloGenCommandException;
import ws.michalski.velogen.exceptions.VeloGenException;
import ws.michalski.velogen.interfaces.VeloGenPlugin;

public class VeloGenManager {

	private Logger logger;

	private Configuration config 				= null;
	private VeloGenCmd veloCmd 					= null;
	private String command 						= null;
	private Map<String, VeloGenPlugin> plugins 	= null;
	
	public VeloGenManager(){
		super();
		
		logger = LogManager.getLogger();
		
		config = VeloGenConfig.getInstance().loadConfig();
		veloCmd = new VeloGenCmd();
		
		plugins = new HashMap<>();
		loadPlugis();
		
	}
	
	public VeloGenEngine getVelogenEngine(Properties properties) throws VeloGenException {
		
		VeloGenEngine engine = null;
		try {
			engine = new VeloGenEngine(properties);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new VeloGenException(e.getMessage());
		}
		
		settingEngine(engine);
		
		return engine;
	}

	
	
	public VeloGenEngine getVelogenEngine() throws VeloGenException {
		
		VeloGenEngine engine = null;
		try {
			engine = new VeloGenEngine();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new VeloGenException(e.getMessage());
		}
		
		settingEngine(engine);
		
		return engine;
	}

	
	private void settingEngine(VeloGenEngine engine) {
		Context context = engine.getVeloContext();
		
		// Lade statische Klassen
		loadStaticClass(context);
	}
	
	
	private void loadStaticClass(Context context) {
		
		for (StaticClass staticClass : config.getStaticClasses().getStaticClass()) {
			
			logger.debug("Loading Static-Class " + staticClass.getClazz() + " as " + staticClass.getKeyName());
			
			Class<?> staticCls;
			try {
				staticCls = Class.forName(staticClass.getClazz());
				context.put(staticClass.getKeyName(), staticCls);
			} catch (ClassNotFoundException e) {
				logger.fatal(e.getMessage());
				e.printStackTrace();
				System.exit(2);
			}
		}
	}


	public VeloGenCmd getCommands(String[] argv) throws VeloGenCommandException{
		try {
			veloCmd.parse(argv);
		} catch (com.beust.jcommander.ParameterException e) {
			logger.error("Command parse error: " + e.getMessage());
			throw new VeloGenCommandException("", e);
		}
		command = veloCmd.jc.getParsedCommand();
		return veloCmd;
	}
	
	
	public Map<String, Object> run(){
		if(command == null){
			// Kein Plugin sollte aufgerufen werden. Leeres Map wird geliefert.
			return new HashMap<>();
		}
		VeloGenPlugin plugin = plugins.get(command);
		Object param = plugin.getCommandParam();
		return plugin.run(param);
		
	}
	
	
	
	public void getUsage(){
		veloCmd.usage();
	}
	
	public void getUsage(String command){
		veloCmd.usage(command);
	}
	
	/**
	 * Plugins werden aus config geladen
	 */
	private void loadPlugis(){
		
		for (Plugin plugin : config.getPlugins().getPlugin()) {
			addPlugin(plugin);
		}
	}

	/**
	 * Plugin wird registriert
	 * 
	 * @param plugin
	 */
	@SuppressWarnings("unchecked")
	public void addPlugin(Plugin plugin) {
		logger.debug("Loading Plugin " + plugin.getClazz() + " as " + plugin.getName());
		
		Class<? extends VeloGenPlugin> plugInCls;
		try {
			plugInCls = (Class<? extends VeloGenPlugin>) Class.forName(plugin.getClazz());
			VeloGenPlugin pi = plugInCls.newInstance();
			veloCmd.addCommand(plugin.getName(), pi.getCommandParam());
			
			plugins.put(plugin.getName(), pi);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			System.exit(3);
		}
	}


	
	/**
	 * Liefert Output-Path für generierte Dateien
	 * 
	 * 1. Es wird geprüft, ob Parameter -op gesetzt ist, sonst
	 * 2. wird geprüft, ob config diese Information enthält, sonst
	 * 3. wird Unterverzeichnis ./output genutzt.
	 * 
	 * 
	 * @return
	 */
	public String getOutputPath(){
		
		String outputPath = null;
		
		if(veloCmd.getCommonParam().getOutputPath() != null){
			outputPath = veloCmd.getCommonParam().getOutputPath();
		} else if(config.getOutputPath() != null){
			outputPath = config.getOutputPath();
		} else {
			outputPath = SystemUtils.USER_DIR 
					+ SystemUtils.FILE_SEPARATOR 
					+ "output";
		}
		
		
		
		Path path = Paths.get(outputPath);
		
		if(Files.notExists(path, LinkOption.NOFOLLOW_LINKS) ||
				! Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
			logger.error("Output path error");
			// TODO: Exception
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Path " + path.toAbsolutePath().normalize().toString());
		}
		
		return path.toAbsolutePath().normalize().toString();
	}
	
	
	
	
	
	public void writeOutputFile(String fileName, String content, boolean replace) throws VeloGenException{
		
		Path target = Paths.get(getOutputPath(), fileName);
		
		if(! replace &&
				Files.exists(target, LinkOption.NOFOLLOW_LINKS)){
			throw new VeloGenException(target.normalize().toString() + " exist. Use option -ov for overwrite.");
		}
		
		try (FileOutputStream fos = new FileOutputStream(target.toFile(), false)) {
			try (BufferedWriter bwr = new BufferedWriter(new OutputStreamWriter(fos))) {
				bwr.write(content);
				bwr.flush();
			}
			
			fos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public String getCommand() {
		return command;
	}
	
	
	
	
}
