package org.scripps.branch.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.scripps.branch.classifier.ManualTree;
import org.scripps.branch.entity.Weka;
import org.scripps.branch.viz.JsonTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	ServletContext servletContext;
	
	@RequestMapping(value = "/MetaServer", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public @ResponseBody String scoreOrSaveTree(@RequestBody JsonNode data){
		Weka wekaObj = new Weka();
		InputStream test_file = servletContext.getResourceAsStream(servletContext.getRealPath("/WEB-INF/data/Metabric_clinical_expression_DSS_sample_filtered.arff"));
		try {
			wekaObj.buildWeka(test_file, null, "metabric_with_clinical");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JsonTree t = new JsonTree();
        ManualTree readtree = new ManualTree();
        LinkedHashMap<String, Classifier> custom_classifiers = null;
		readtree = t.parseJsonTree(wekaObj, data.get("treestruct"), data.get("dataset").asText(), custom_classifiers);
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(readtree);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
    }
	
//	String command = data.get("command").asText(); //scoretree or savetree
//	int prev_tree_id = -1;
//	String dataset = data.get("dataset").asText();
//	dataset = "metabric_with_clinical";//todo fix this so javascript and serverside agree about this..
//	
//	//To avoid penalizing user for genes added to his/her own tree.
//	HttpSession session = request_.getSession();
//	Player player = (Player) session.getAttribute("player");
//	if(player!=null || command.equals("scoretree")){
//		int PlayerId = -1;
//		if(player!=null){
//			PlayerId = player.getId();
//		}
//		Weka weka = name_dataset.get(dataset);	
//		if(weka==null){
//			handleBadRequest(request_, response, "no dataset loaded for dataset: "+dataset);
//			return;
//		}		
//		//create the weka tree structure
//		JsonTree t = new JsonTree();
//		ManualTree readtree = new ManualTree();
//		readtree = t.parseJsonTree(weka, data.get("treestruct"), dataset, custom_classifiers);
//		List<String> entrez_ids = t.getEntrezIds(data.get("treestruct"), new ArrayList<String>());
//		int numnodes = readtree.numNodes();
//		Evaluation eval = new Evaluation(weka.getTest());
//		eval.evaluateModel(readtree, weka.getTest());
//		HashMap distributionData = readtree.getDistributionData();
//		ObjectNode result = mapper.createObjectNode();
//		result.put("pct_correct", eval.pctCorrect());
//		result.put("size", numnodes);
//		double nov = Tree.getUniqueIdNovelty(entrez_ids, PlayerId);
//		result.put("novelty", nov);//
//		result.put("text_tree", readtree.toString());
//		//serialize and return the result		
//		JsonNode treenode = readtree.getJsontree();
//		result.put("treestruct", treenode);
//		response.setContentType("text/json");
//		PrintWriter out = response.getWriter();
//		String result_json = mapper.writeValueAsString(result);
//
//		//now store it in the database
//		String comment = "";
//		int user_saved = 0;
//		int privateflag = 0;
//		comment = data.get("comment").asText();
//		String ip = request_.getRemoteAddr();
//		List<Feature> features = new ArrayList<Feature>();
//		for(String entrez_id : entrez_ids){
//			Feature f = weka.features.get(entrez_id);
//			features.add(f);
//		}
//		if(command.equals("savetree")){
//			user_saved = 1;
//			prev_tree_id = data.get("previous_tree_id").asInt();
//			privateflag = data.get("privateflag").asInt();
//		}
//		Tree tree = new Tree(0, PlayerId, ip, features, result_json,comment, user_saved, privateflag);
//		int tid = tree.insert(prev_tree_id, privateflag);
//		float score = 0; 
//		score = (float) ((750 * (1 / numnodes)) + (500 * nov) + (1000 * eval.pctCorrect()));
//		tree.insertScore(tid, dataset, (float)eval.pctCorrect(), (float)numnodes, (float)nov, score);
//		ArrayList json_badges = new ArrayList();
//		if(command.equals("savetree")){
//			Badge _badge = new Badge();
//			json_badges = _badge.getEarnedBadges(tid, PlayerId);
//		}
//		result.put("badges", mapper.valueToTree(json_badges));	
//		result.put("tree_id", tid);
//		//System.out.println(distributionData);
//		result.put("distribution_data", mapper.valueToTree(distributionData));
//		result_json = mapper.writeValueAsString(result);
//		out.write(result_json);
//		out.close();
//	} else {
//		PrintWriter out = response.getWriter();
//		String result_json = "{message: 'Please login to save a tree.'}";
//		out.write(result_json);
//		out.close();
//	}

}