<#include "header.ftl">
<link rel="stylesheet" href="/static/styles/result.css">
<link rel="stylesheet" href="/static/styles/detail.css">
    <div id="main">
        <div class="zg-wrap zu-main clearfix ">
            <div class="zm-profile-section-wrap zm-profile-followee-page">
                <div class="zm-profile-section-head">
                    <span class="zm-profile-section-name">
                        <a href="/user/${curUser.id}">${curUser.name}</a> 粉丝 ${followerCount} 人
                    </span>
                </div>
                <div class="zm-profile-section-list">
                    <div id="zh-profile-follows-list">
                        <div class="zh-general-list clearfix">
                            <#list followerList as follower>
                                <div class="zm-profile-card zm-profile-section-item zg-clear no-hovercard">
                                    <#if follower.get("followed")>
                                        <div class="zg-right">
                                            <button class="zg-btn zg-btn-unfollow zm-rich-follow-btn small nth-0
                                            js-follow-user" data-status="1" data-id="${follower.get("user").id}">取消关注</button>
                                        </div>
                                    <#else>
                                        <div class="zg-right">
                                            <button class="zg-btn zg-btn-follow zm-rich-follow-btn small nth-0
                                            js-follow-user" data-id="${follower.get("user").id}">关注</button>
                                        </div>
                                    </#if>
                                    <a title="Barty" class="zm-item-link-avatar" href="/user/${follower.get("user").id}">
                                        <img src="${follower.get("user").headUrl}" class="zm-item-img-avatar">
                                    </a>
                                    <div class="zm-list-content-medium">
                                        <h2 class="zm-list-content-title"><a data-tip="p$t$buaabarty" href="/user/${follower.get("user").id}" class="zg-link" title="Barty">${follower.get("user").name}</a></h2>
                                        <div class="details zg-gray">
                                            <a target="_blank" href="/user/${follower.get("user").id}/followers" class="zg-link-gray-normal">${follower.get("followerCount")}粉丝</a>
                                            /
                                            <a target="_blank" href="/user/${follower.get("user").id}/followees" class="zg-link-gray-normal">${follower.get("followeeCount")}关注</a>
                                            /
                                            <a target="_blank" href="#" class="zg-link-gray-normal">${follower.get("commentCount")} 回答</a>
                                            /
                                            <a target="_blank" href="#" class="zg-link-gray-normal">${follower.get("likeCount")} 赞同</a>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <a aria-role="button" class="zg-btn-white zu-button-more">更多</a>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
<#include "footer.ftl">
<script type="text/javascript" src="/static/scripts/main/site/follow.js"></script>