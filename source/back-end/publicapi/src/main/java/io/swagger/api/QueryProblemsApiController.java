package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.QuerryInfo;
import io.swagger.model.QuerryResult;
import io.swagger.model.StatusCode;
import io.swagger.service.ProblemService;
import io.swagger.utils.ParserErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@javax.annotation.Generated(value = "io.io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-07T02:38:17.975Z[GMT]")
@Controller
public class QueryProblemsApiController implements QueryProblemsApi {

    private static final Logger log = LoggerFactory.getLogger(QueryProblemsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProblemService problemService;

    @org.springframework.beans.factory.annotation.Autowired
    public QueryProblemsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<QuerryResult> queryProblems(@ApiParam(value = "") @Valid @RequestBody QuerryInfo body) {
        String accept = request.getHeader("Accept");


        QuerryResult querryResult = new QuerryResult();
        try {
            querryResult = problemService.queryProblem(body);
        } catch (ParserErrorException e) {
            // todo 表达异常
            e.printStackTrace();
            log.error(e.getMessage());
            querryResult.setStatus(StatusCode.ERROR);
            // todo
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("message", e.getMessage());
            querryResult.addResultsItem(stringStringHashMap);

            return new ResponseEntity<>(querryResult, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<QuerryResult>(querryResult, HttpStatus.OK);
    }

}
