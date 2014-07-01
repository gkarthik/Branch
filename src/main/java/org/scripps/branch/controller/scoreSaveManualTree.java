package org.scripps.branch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class scoreSaveManualTree {

	@RequestMapping(value = "/MetaServer", method = RequestMethod.POST)
    public @ResponseBody String scoreOrSaveTree(){
        String json = "{\"Hello\":\"World\"}";
        return json;
    }

}