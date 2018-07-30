package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.controller.HomeController;
import org.apache.commons.lang.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveWordsService implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordsService.class);
    private TrieNode rootNode = new TrieNode();

//    public static void main(String[] args){
//        SensitiveWordsService s = new SensitiveWordsService();
//        s.addWord("色情");
//        s.addWord("赌博");
//        s.addWord("暴力");
//        System.out.println(s.filter("色的    暴 力的赌的多 对多赌 博的的暴力的色 情多对多色情狂"));
//    }

    /**
     * 过滤器，输入原始文本，将文本中的敏感词替换为"***"，返回处理后的文本
     * @param text
     * @return
     */
    public String filter(String text){
        if(!StringUtils.hasText(text)){
            return text;
        }
        StringBuilder sb = new StringBuilder();
        String replacement = "***";
        int begin = 0;
        int position = 0;
        TrieNode tempNode = rootNode;

        //对文本的每个字符进行遍历
        while (position<text.length()){
            Character c = text.charAt(position);

            //如果当前字符是干扰符号，则直接跳过；
            // 如果字典树遍历还未开始则将头指针begin++，并且将当前字符添加到暂存区sb中
            if(isSymbol(c)){
                position++;
                if(tempNode==rootNode){
                    sb.append(c);
                    begin++;
                }
                continue;
            }

            TrieNode subNode;
            if(null != (subNode = tempNode.getSubNode(c))){
                //如果当前Node下面包含有c字符对应的Node，则将position指针移动一位
                tempNode = subNode;
                position++;
                //如果包含c字符的subNode已经是结尾Node，则表示begin到position的字符串是敏感词，需要替换为"***"
                //同时将字典树的指针tempNode回归到根结点
                if(subNode.isKeyWordEnd()){
                    sb.append(replacement);
                    tempNode = rootNode;
                    begin = position;
                }
                if(position == text.length()){
                    for(int i = begin; i<position; i++){
                        sb.append(text.charAt(i));
                    }
                    break;
                }
            }else {
                //如果当前Node下不包含c字符对应的Node，则position指针移动一位，并将begin与position同步
                //同时将字典树的指针tempNode回归到根结点
                //将begin到position之间的字符串添加到暂存区sb
                position++;
                for(int i = begin; i<position; i++){
                    sb.append(text.charAt(i));
                }
                begin = position;
                tempNode = rootNode;
            }
        }
        return sb.toString();
    }

    //对每一行敏感词进行遍历，将该关键字的每个字添加到字典树的每个结点上
    private void addWord(String lineTxt){
        TrieNode tempNode = rootNode;
        for(int i = 0; i<lineTxt.length(); i++){
            Character c = lineTxt.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(null == subNode){
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            tempNode = subNode;
            //如果字符遍历到头则将该节点标记为尾部节点
            if(i == lineTxt.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    //服务组件初始化时就将保存敏感词的文本读取到组件中
    @Override
    public void afterPropertiesSet() throws Exception {
        BufferedReader reader = null;
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            reader = new BufferedReader(new InputStreamReader(is));
            String lintTxt;
            while ((lintTxt = reader.readLine()) != null){
                addWord(lintTxt.trim());
            }
        }catch (Exception e){
            logger.error("读取敏感词文件错误：" + e.getMessage());
        }finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    //判断字符是否为符号
    private boolean isSymbol(char c){
        int ic = (int) c;
        return !CharUtils.isAsciiNumeric(c) && (ic<0x2E80 || ic>0x9FFF);
    }

    //字典树节点，其中包含一个Map，该Map保存了所有子节点，key为节点对应的字符
    private class TrieNode{
        private boolean end = false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        private void addSubNode(Character key, TrieNode node){
            subNodes.put(key, node);
        }

        private TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        private boolean isKeyWordEnd(){
            return end;
        }

        private void setKeyWordEnd(boolean end) {
            this.end = end;
        }
    }
}
