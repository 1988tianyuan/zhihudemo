package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.controller.HomeController;
import com.liugeng.zhihudemo.pojo.Question;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private static final String solr_url = "http://127.0.0.1:8983/solr/zhihuDemo";
    private static final String QUESTION_TITLE_FIELD = "question_title";
    private static final String QUESTION_CONTENT_FIELD = "question_content";
    private SolrClient solrClient = new HttpSolrClient.Builder(solr_url).build();

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    public List<Question> searchQuestion(String keyword, int offset, int count, String hlPre, String hlPost) throws SolrServerException, IOException{
        List<Question> questionList = new ArrayList<>();
        //从solr中搜索question，通过title和content两个字段联合搜索
        SolrQuery query = new SolrQuery();
        query.set("q", QUESTION_TITLE_FIELD +":"+keyword+ " OR "+ QUESTION_CONTENT_FIELD + ":" + keyword);
        query.setStart(offset);
        query.setRows(count);
        query.setHighlight(true);
        query.addHighlightField(QUESTION_TITLE_FIELD+","+QUESTION_CONTENT_FIELD);
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");
        query.setHighlightFragsize(100);
        QueryResponse queryResponse = solrClient.query(query);

        //搜索结果为一个map，将map数据导入到question实体中
        Map<String, Map<String, List<String>>> highlighting =  queryResponse.getHighlighting();
        for(Map.Entry<String, Map<String, List<String>>> entry : highlighting.entrySet()){
            Question question = new Question();
            int id = Integer.parseInt(entry.getKey());
            question.setId(id);
            if(entry.getValue().containsKey(QUESTION_TITLE_FIELD)){
                List<String> titleList = entry.getValue().get(QUESTION_TITLE_FIELD);
                if(titleList.size()>0){
                    question.setTitle(titleList.get(0));
                }
            }
            if(entry.getValue().containsKey(QUESTION_CONTENT_FIELD)){
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT_FIELD);
                if(contentList.size()>0){
                    question.setContent(contentList.get(0));
                }
            }
            questionList.add(question);
        }
        return questionList;
    }

    public boolean newQuestionIndex(int qid, String title, String content){
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", String.valueOf(qid));
        document.setField(QUESTION_TITLE_FIELD, title);
        document.setField(QUESTION_CONTENT_FIELD, content);
        try {
            UpdateResponse response = solrClient.add(document);
            int status = response.getStatus();
            if(status == 0){
                logger.info("添加问题：“"+title+"”成功 ！");
                solrClient.commit();
            }else {
                logger.info("添加问题：“"+title+"”失败 ！");
            }
            return response != null && status == 0;
        } catch (Exception e) {
            logger.error("向solr添加document时出错："+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
