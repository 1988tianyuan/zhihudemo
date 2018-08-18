
<#include "header.ftl">

<#macro like_comment vo>
    <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
        <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
        <div class="feed-item-inner">
            <div class="avatar">
                <a title="${vo.userName}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.userId}">
                    <img src="${request.contextPath}${vo.userHead}" class="zm-item-img-avatar">
                </a>
            </div>
            <div class="feed-main">
                <div class="feed-content" data-za-module="AnswerItem">
                    <div class="feed-question-detail-item">
                        <div class="question-description-plain zm-editable-content"></div>
                    </div>
                    <div class="expandable entry-body">
                        <div class="zm-item-answer-author-info">
                            <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.userId}">${vo.userName}</a>
                            在${vo.createDate?string('yyyy年MM月dd日 hh:mm:ss')}时
                            <strong>点赞</strong> 了问题
                            <a target="_blank" href="/question/${vo.get("questionId")}"><strong> ${vo.get("questionTitle")} </strong></a>
                            下 <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.get("commentOwnerId")}">${vo.get("commentOwnerName")}</a> 的回答：
                        </div>
                        <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                            <div class="zh-summary summary clearfix">
                                <a target="_blank" href="#">${vo.get("commentContent")}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro comment_quetion vo>
    <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
        <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
        <div class="feed-item-inner">
            <div class="avatar">
                <a title="${vo.userName}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.userId}">
                    <img src="${request.contextPath}${vo.userHead}" class="zm-item-img-avatar">
                </a>
            </div>
            <div class="feed-main">
                <div class="feed-content" data-za-module="AnswerItem">
                    <div class="feed-question-detail-item">
                        <div class="question-description-plain zm-editable-content"></div>
                    </div>
                    <div class="expandable entry-body">
                        <div class="zm-item-answer-author-info">
                            <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.userId}">${vo.userName}</a>
                            在${vo.createDate?string('yyyy年MM月dd日 hh:mm:ss')}时 <strong>回答</strong> 了该问题</div>
                        <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                            <div class="zh-summary summary clearfix">
                                <a target="_blank" href="/question/${vo.get("questionId")}">${vo.get("questionTitle")}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro follow_quetion vo>
<div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
    <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
    <div class="feed-item-inner">
        <div class="avatar">
            <a title="${vo.userName}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.userId}">
                    <img src="${request.contextPath}${vo.userHead}" class="zm-item-img-avatar"></a>
        </div>
        <div class="feed-main">
            <div class="feed-content" data-za-module="AnswerItem">
                <div class="feed-question-detail-item">
                    <div class="question-description-plain zm-editable-content"></div>
                </div>
                <div class="expandable entry-body">
                    <div class="zm-item-answer-author-info">
                        <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.userId}">${vo.userName}</a>
                        在${vo.createDate?string('yyyy年MM月dd日 hh:mm:ss')}时 <strong>关注</strong> 了该问题</div>
                    <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                        <div class="zh-summary summary clearfix">
                            <a target="_blank" href="/question/${vo.get("questionId")}">${vo.get("questionTitle")}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>

<link rel="stylesheet" href="./static/styles/index.css">
<link rel="stylesheet" href="./static/styles/detail.css">
<div class="zg-wrap zu-main clearfix " role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <div class="zg-section" id="zh-home-list-title">
                <i class="zg-icon zg-icon-feedlist"></i>新鲜事
                <input type="hidden" id="is-topstory">
                <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">
                            <i class="zg-icon zg-icon-settings"></i>设置</a></span>
            </div>
            <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">
                    <#list feeds as feed>
                        <#if feed.type == 1>
                            <@comment_quetion vo=feed/>
                        <#elseif feed.type == 4>
                            <@follow_quetion vo=feed/>
                        <#elseif feed.type == 0>
                            <@like_comment vo=feed/>
                        </#if>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "footer.ftl">
