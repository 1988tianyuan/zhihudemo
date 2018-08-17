<#include "header.ftl">

<#macro comment_quetion vo>
    <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
        <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
        <div class="feed-item-inner">
            <div class="avatar">
                <a title="${vo.userName}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="${request.contextPath}/user/${vo.userId}">
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
                            <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="${request.contextPath}/user/${vo.userId}">${vo.userName}</a>
                            在${vo.createDate?string('yyyy年MM月dd日 hh:mm:ss')}时 <strong>回答</strong> 了该问题</div>
                        <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                            <div class="zh-summary summary clearfix">
                                <a target="_blank" href="${request.contextPath}/question/${vo.get("questionId")}">${vo.get("questionTitle")}</a>
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
            <a title="${vo.userName}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="${request.contextPath}/user/${vo.userId}">
                <img src="${request.contextPath}${vo.userHead}" class="zm-item-img-avatar"></a>
        </div>
        <div class="feed-main">
            <div class="feed-content" data-za-module="AnswerItem">
                <div class="feed-question-detail-item">
                    <div class="question-description-plain zm-editable-content"></div>
                </div>
                <div class="expandable entry-body">
                    <div class="zm-item-answer-author-info">
                        <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="${request.contextPath}/zhihudemo/user/${vo.userId}">${vo.userName}</a>
                        在${vo.createDate?string('yyyy年MM月dd日 hh:mm:ss')}时 <strong>关注</strong> 了该问题</div>
                    <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                        <div class="zh-summary summary clearfix">
                            <a target="_blank" href="${request.contextPath}/question/${vo.get("questionId")}">${vo.get("questionTitle")}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>

<link rel="stylesheet" href="../static/styles/index.css">
<link rel="stylesheet" href="../static/styles/detail.css">
<div class="zg-wrap zu-main clearfix " role="main">
    <div class="zm-profile-section-wrap zm-profile-followee-page">
        <div class="zm-profile-section-list">
            <div id="zh-profile-follows-list">
                <div class="zh-general-list clearfix">
                    <div class="zm-profile-card zm-profile-section-item zg-clear no-hovercard">
                        <div class="zg-right">
                            <#if profileUser.get("followed")>
                                <button class="zg-btn zg-btn-unfollow zm-rich-follow-btn small nth-0
                                    js-follow-user" data-status="1" data-id="${profileUser.get("user").id}">取消关注</button>
                                <#else>
                                <button class="zg-btn zg-btn-follow zm-rich-follow-btn small nth-0
                                    js-follow-user" data-id="${profileUser.get("user").id}">关注</button>
                            </#if>
                        </div>
                        <a title="Barty" class="zm-item-link-avatar" href="${request.contextPath}/user/${profileUser.get("user").id}">
                            <img src="${request.contextPath}${profileUser.get("user").headUrl}" class="zm-item-img-avatar">
                        </a>
                        <div class="zm-list-content-medium">
                            <h2 class="zm-list-content-title"><a data-tip="p$t$buaabarty" href="${request.contextPath}/user/${profileUser.get("user").id}" class="zg-link">${profileUser.get("user").name}</a></h2>
                            <div class="details zg-gray">
                                <a target="_blank" href="${request.contextPath}/user/${profileUser.get("user").id}/followers" class="zg-link-gray-normal">${profileUser.get("followerCount")}粉丝</a>
                                /
                                <a target="_blank" href="${request.contextPath}/user/${profileUser.get("user").id}/followees" class="zg-link-gray-normal">${profileUser.get("followeeCount")}关注</a>
                                /
                                <a target="_blank" href="#" class="zg-link-gray-normal">${profileUser.get("commentCount")} 回答</a>
                                /
                                <a target="_blank" href="#" class="zg-link-gray-normal">${profileUser.get("likeCount")} 赞同</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <div class="zh-question-followers-sidebar">
                <div class="zg-gray-normal">
                    <strong class="js-user-count">共同关注的用户：</strong>
                </div>
                <div class="list zu-small-avatar-list zg-clear">
                    <#list shareFollowees as sharedFollowee>
                        <a data-tip="p$b$yi-yi-98-91-99" class="zm-item-link-avatar" href="${request.contextPath}/user/${sharedFollowee.id}"
                            data-original_title="${sharedFollowee.name}">
                            <img src="${request.contextPath}${sharedFollowee.headUrl}" class="zm-item-img-avatar">
                        </a>
                    </#list>
                </div>
            </div>
            <hr>
            <div class="zu-main-content-inner">
                <div class="zg-section" id="zh-home-list-title">
                    <i class="zg-icon zg-icon-feedlist"></i>他的最新动态
                    <input type="hidden" id="is-topstory">
                </div>
                <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                    <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                    <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">
                    <#list feeds as feed>
                        <#if feed.type == 1>
                            <@comment_quetion vo=feed/>
                        <#elseif feed.type == 4>
                            <@follow_quetion vo=feed/>
                        </#if>
                    </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <div class="zg-section" id="zh-home-list-title">
                <i class="zg-icon zg-icon-feedlist"></i>他提的问题
                <input type="hidden" id="is-topstory">
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
                                    <a title="${vo.get("user").name}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="${request.contextPath}/user/${vo.get("user").id}">
                                        <img src="${request.contextPath}${vo.get("user").headUrl}" class="zm-item-img-avatar"></a>
                                </div>
                                <div class="feed-main">
                                    <div class="feed-content" data-za-module="AnswerItem">
                                        <meta itemprop="answer-id" content="389034">
                                        <meta itemprop="answer-url-token" content="13174385">
                                        <h2 class="feed-title">
                                            <a class="question_link" target="_blank" href="${request.contextPath}/question/${vo.get("question").id}">${vo.get("question").title}</a>
                                        </h2>
                                        <div class="feed-question-detail-item">
                                            <div class="question-description-plain zm-editable-content"></div>
                                        </div>
                                        <div class="expandable entry-body">
                                            <#--<div class="zm-item-vote">-->
                                                <#--<a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">4168</a></div>-->
                                            <div class="zm-item-answer-author-info">
                                                <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="${request.contextPath}/user/${vo.get("user").id}">${vo.get("user").name}</a>
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
                                                        <i class="z-icon-follow"></i>关注问题</a>
                                                    <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                                        <i class="z-icon-comment"></i>${vo.get("question").commentCount} 个回答
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
            </div>
        </div>
    </div>
</div>
<#include "footer.ftl">
<script type="text/javascript" src="../static/scripts/main/site/profile.js"></script>