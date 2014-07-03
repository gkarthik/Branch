package org.scripps.branch.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.scripps.branch.classifier.ManualTree;
import org.scripps.branch.config.ApplicationContext;
import org.scripps.branch.entity.Weka;
import org.scripps.branch.globalentity.WekaObject;
import org.scripps.branch.viz.JsonTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class scoreSaveManualTree {
	
	@Autowired
	private WekaObject weka;
	
	@RequestMapping(value = "/MetaServer", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public @ResponseBody String scoreOrSaveTree(@RequestBody JsonNode data, HttpServletRequest request){
		Weka wekaObj = weka.getWeka();
        JsonTree t = new JsonTree();
        ManualTree readtree = new ManualTree();
        LinkedHashMap<String, Classifier> custom_classifiers = new LinkedHashMap<String, Classifier>();
		readtree = t.parseJsonTree(wekaObj, data.get("treestruct"), data.get("dataset").asText(), custom_classifiers);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode result = mapper.createObjectNode();
		//serialize and return the result		
		JsonNode treenode = readtree.getJsontree();
		result.put("treestruct", treenode);
		String result_json = "";
		try {
			result_json = mapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result_json;
    }
}