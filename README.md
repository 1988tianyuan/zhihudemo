# springboot实战项目
模仿知乎网站搭建的简单的问答网站，这是我第一个springboot项目，具备发布问题、回答、站内信、站内全文搜索、点赞点踩、关注、目标推送等功能，
对多种web后端组件如mysql、redis和solr等进行了实践运用，还用Python实现了简单的爬虫

# 项目简介
该项目利用springboot进行依赖注入的组件管理并结合freemarker引擎实现MVC，采用mybatis作为orm，采用mysql 5.7版本进行数据持久化，采用redis实现
点赞点踩、站内互关、异步消息队列等功能，利用solr 7.4实现站内全文搜索（在Linux部署solr服务时遇到一些问题，服务无法正常启动，正在探索中...），
并使用Python的爬虫库Pyspider爬取了一部分v2ex站的内容到本项目中进行展示

## 项目目录结构
```
├─com.liugeng.zhihudemo----------------------------java源码主目录
│  │
│  ├─async--------------------------自己基于redis实现的异步消息队列，包括事件处理器、事件生产者和事件消费者
│  │
│  ├─config-----------------------java config文件，对拦截器和静态资源进行配置
│  │
│  ├─controller-------------------------控制器层，主要进行请求信息和后台数据的调度
│  │
│  ├─dao--------------------------dao层，用于mybatis生成mapper对象进行数据库操作
│  │
│  ├─interceptor--------------------------拦截器，用于进行登录验证和登录跳转
│  │
│  ├─pojo--------------------------数据实体类
│  │
│  ├─service--------------------------service层，用于进行大部分业务操作

```