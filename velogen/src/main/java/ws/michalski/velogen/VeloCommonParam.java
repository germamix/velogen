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

import com.beust.jcommander.Parameter;

public class VeloCommonParam {

	// common parameters
		@Parameter(names={"-tp", "--template-path"}, description="template path")
		private String templatePath;
		
		@Parameter(names={"-op", "--output-path"}, description="output path")
		private String outputPath; 
		
		@Parameter(names={"-ov", "--overwirte"}, description="overwrite output files")
		private boolean overwirte = false;
		
		@Parameter(names={"-t", "--template-name"}, description="template name")
		private String template;
		
		@Parameter(names={"-f", "--file-name"}, description="file name for generated output", required=false)
		private String fileName;
		
		

		public String getTemplatePath() {
			return templatePath;
		}

		public String getOutputPath() {
			return outputPath;
		}

		public boolean isOverwirte() {
			return overwirte;
		}

		public String getTemplate() {
			return template;
		}

		public String getFileName() {
			return fileName;
		}

		
	
	
}
