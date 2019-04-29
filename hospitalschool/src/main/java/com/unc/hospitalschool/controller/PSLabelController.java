package com.unc.hospitalschool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.unc.hospitalschool.dao.*;
import com.unc.hospitalschool.model.*;

@RestController
@RequestMapping("/psLabel")
public class PSLabelController {

  private static Logger logger = LoggerFactory.getLogger("LOGGER");


  @Autowired
  private PSLabelDao psLabelDao;

  @GetMapping
  @ResponseBody
  public ResponseEntity<Map<String, Object>> getAllPSLabels() {
    logger.info("Get all PSLabels called");
    Map<String, Object> map = new HashMap<String, Object>();
    List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

    for (PSLabel psLabel : psLabelDao.findAll()) {
      jsons.add(psLabel.toJson());
    }
    map.put("PSLabels", jsons);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> newPSLabel(@RequestBody Map<String, String> body) {
    logger.info(body.toString());
    if (!(body.containsKey("label") && body.containsKey("code"))) {
      return ResponseEntity.badRequest().body("label/code field not provided");
    }
    psLabelDao.save(new PSLabel(body.get("label"), body.get("code")));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "/{lid}")
  public ResponseEntity<Object> updatePSLabel(@RequestBody Map<String, String> body,
      @PathVariable int lid) {
    PSLabel psLabel = psLabelDao.findByLid(lid);
    logger.info("Updating PSLabel " + lid);
    if (psLabel == null) {
      logger.error("Unable to update - psLabel with lid: " + lid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to update - psLabel with lid: " + lid + " not found");
    }
    if (!(body.containsKey("label") && body.containsKey("code"))) {
      logger.error("Unable to update - psLabel; incorrect request data");
      return ResponseEntity.badRequest()
          .body("Unable to update - psLabel. Incorrect request field");
    }
    psLabel.setLabel(body.get("label"));
    psLabel.setCode(body.get("code"));

    psLabelDao.save(psLabel);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{lid}")
  public ResponseEntity<Object> deleteByLid(@PathVariable int lid) {
    PSLabel psLabel = psLabelDao.findByLid(lid);
    if (psLabel == null) {
      logger.error("Unable to delete - psLabel with lid: " + lid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to delete - psLabel with lid: " + lid + " not found");
    } else {
      psLabelDao.delete(psLabel);
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }
}
