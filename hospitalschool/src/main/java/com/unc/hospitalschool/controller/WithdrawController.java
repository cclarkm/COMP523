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
@RequestMapping("/withdraw")
public class WithdrawController {

  private static Logger logger = LoggerFactory.getLogger("LOGGER");


  @Autowired
  private WithdrawDao withdrawDao;

  @GetMapping
  @ResponseBody
  public ResponseEntity<Map<String, Object>> getAllWithdraws() {
    logger.info("Get all withdraws called");
    Map<String, Object> map = new HashMap<String, Object>();
    List<Map<String, String>> jsons = new ArrayList<Map<String, String>>();

    for (Withdraw withdraw : withdrawDao.findAll()) {
      jsons.add(withdraw.toJson());
    }
    map.put("withdraws", jsons);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> newWithdraw(@RequestBody Map<String, String> body) {
    logger.info(body.toString());
    if (!body.containsKey("withdraw")) {
      return ResponseEntity.badRequest().body("withdraw field not provided");
    }
    withdrawDao.save(new Withdraw(body.get("withdraw")));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "/{wid}")
  public ResponseEntity<Object> updateWithdraw(@RequestBody Map<String, String> body,
      @PathVariable int wid) {
    Withdraw withdraw = withdrawDao.findByWid(wid);
    logger.info("Updating county " + wid);
    if (withdraw == null) {
      logger.error("Unable to update - withdraw with wid: " + wid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to update - withdraw with wid: " + wid + " not found");
    }
    if (body.containsKey("withdraw")) {
      withdraw.setWithdraw(body.get("withdraw"));
      withdrawDao.save(withdraw);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      logger.error("Unable to update - withdraw; incorrect request data");
      return ResponseEntity.badRequest()
          .body("Unable to update - withdraw. Incorrect request field");
    }
  }

  @DeleteMapping(value = "/{wid}")
  public ResponseEntity<Object> deleteByCid(@PathVariable int wid) {
    Withdraw withdraw = withdrawDao.findByWid(wid);
    if (withdraw == null) {
      logger.error("Unable to delete - withdraw with wid: " + wid + " not found");
      return ResponseEntity.badRequest()
          .body("Unable to delete - withdraw with wid: " + wid + " not found");
    } else {
      withdrawDao.delete(withdraw);
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }
}
