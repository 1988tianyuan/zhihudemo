
<#include "header.ftl">
<link rel="stylesheet" href="../static/styles/index.css">
<link rel="stylesheet" href="../static/styles/detail.css">
<div class="zg-wrap zu-main clearfix " role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <div class="zg-section" id="zh-home-list-title">
                <i class="zg-icon zg-icon-feedlist"></i>最新动态
                <input type="hidden" id="is-topstory">
                <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">
                            <i class="zg-icon zg-icon-settings"></i>设置</a></span>
            </div>
            <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">

                    <#list vos as vo>
                    <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
                        <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
                        <div class="feed-item-inner">
                            <div class="avatar">
                                <a title="${vo.get("user").name}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.get("user").id}">
                                    <img src="${vo.get("user").headUrl}" class="zm-item-img-avatar"></a>
                            </div>
                            <div class="feed-main">
                                <div class="feed-content" data-za-module="AnswerItem">
                                    <meta itemprop="answer-id" content="389034">
                                    <meta itemprop="answer-url-token" content="13174385">
                                    <h4 class="feed-title">
                                        <a class="question_link" target="_blank" href="/question/${vo.get("question").id}">${vo.get("question").title}</a>
                                    </h4>
                                    <div class="feed-question-detail-item">
                                        <div class="question-description-plain zm-editable-content"></div>
                                    </div>
                                    <div class="expandable entry-body">
                                        <div class="zm-item-vote">
                                            <a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">4168</a></div>
                                        <div class="zm-item-answer-author-info">
                                            <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.get("user").id}">${vo.get("user").name}</a>
                                            ${vo.get("question").createDate?string('yyyy年MM月dd日 hh:mm:ss')}</div>
                                        <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount">4168</span>&nbsp;人赞同</a></span>
                                        </div>
                                        <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/question/${vo.get("question").id}" data-author-name="${vo.get("user").name}" data-entry-url="/question/${vo.get("question").id}">
                                            <div class="zh-summary summary clearfix">${vo.get("question").content}</div>
                                        </div>
                                    </div>
                                    <div class="feed-meta">
                                        <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                            <div class="zm-meta-panel">
                                                <a data-follow="q:link" class="follow-link zg-follow meta-item" href="javascript:;" id="sfb-123114">
                                                    <#if vo.get("followed")>
                                                        已关注问题
                                                    <#else>
                                                        <i class="z-icon-follow"></i>未关注问题
                                                    </#if>
                                                </a>
                                                <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                                    <i class="z-icon-comment"></i>${vo.get("question").commentCount} 条评论
                                                </a>
                                                <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                                    <i class="z-icon-fold"></i>${vo.get("followCount")}人收藏该问题
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </#list>
                </div>
                <a href="javascript:;" id="zh-load-more" data-method="next" class="zg-btn-white zg-r3px zu-button-more" style="">更多</a></div>
        </div>
    </div>
</div>
<#include "footer.ftl">
