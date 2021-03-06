/*
* Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Copyright the ZAP Development Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zaproxy.zap.extension.api.ApiImplementor;
import org.zaproxy.zap.extension.api.JavaAPIGenerator;
import org.zaproxy.zap.extension.api.NodeJSAPIGenerator;
import org.zaproxy.zap.extension.api.PhpAPIGenerator;
import org.zaproxy.zap.extension.api.PythonAPIGenerator;
import org.zaproxy.zap.extension.reveal.RevealAPI;
import org.zaproxy.zap.extension.selenium.SeleniumAPI;
import org.zaproxy.zap.extension.selenium.SeleniumOptions;
import org.zaproxy.zap.extension.spiderAjax.AjaxSpiderAPI;
import org.zaproxy.zap.extension.spiderAjax.AjaxSpiderParam;

public class ApiGenerator {

	private static final String JAVA_OUTPUT_DIR = "../zap-api-java/subprojects/zap-clientapi/src/main/java/org/zaproxy/clientapi/gen";

	private static final String PYTHON_OUTPUT_DIR = "../zap-api-python/src/zapv2/";
	
	private static final String NODE_OUTPUT_DIR = "../zap-api-nodejs/src/";

	public static List<ApiImplementor> getApiImplementors() {
		List<ApiImplementor> list = new ArrayList<ApiImplementor>();
		
		// If you implement an API for a _release_ add-on please add it here (in alphabetical order)
		// so that all of the client APIs are generated
		// Note that the following files will also need to be edited manually:
		//    __init__.py (in zap-api-python project)
		//    ClientApi.java (in zap-api-java project)
		// In zaproxy project:
		// 	nodejs/api/zapv2/index.js
		//	php/api/zapv2/src/Zap/Zapv2.php

		ApiImplementor api;

		api = new AjaxSpiderAPI(null);
		api.addApiOptions(new AjaxSpiderParam());
		list.add(api);

		list.add(new RevealAPI(null));
		list.add(new SeleniumAPI(new SeleniumOptions()));

		return list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JavaAPIGenerator japi = new JavaAPIGenerator(JAVA_OUTPUT_DIR, true);
			japi.generateAPIFiles(getApiImplementors());

			NodeJSAPIGenerator napi = new NodeJSAPIGenerator(NODE_OUTPUT_DIR, true);
			napi.generateAPIFiles(getApiImplementors());
		
			PhpAPIGenerator phapi = new PhpAPIGenerator("../zaproxy/php/api/zapv2/src/Zap", true);
			phapi.generateAPIFiles(getApiImplementors());

			PythonAPIGenerator pyapi = new PythonAPIGenerator(PYTHON_OUTPUT_DIR, true);
			pyapi.generateAPIFiles(getApiImplementors());

			//WikiAPIGenerator wapi = new WikiAPIGenerator("../zaproxy-wiki", true);
			//wapi.generateWikiFiles(getApiImplementors());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
