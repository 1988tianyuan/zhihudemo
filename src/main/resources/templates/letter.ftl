<#include "header.ftl">
<link rel="stylesheet" href="../static/styles/letter.css">
    <div id="main">
        <div class="zg-wrap zu-main clearfix ">
            <ul class="letter-list">
                <#list conversations as conversation>
                <li id="conversation-item-10001_622873">
                    <a class="letter-link" href="${request.contextPath}/msg/detail?conversationId=${conversation.get("conversation").conversationId}">
                    </a>
                    <div class="letter-info">
                        <span class="l-time">${conversation.get("conversation").createDate?string('yyyy年MM月dd日 hh:mm:ss')}</span>
                        <div class="l-operate-bar">
                            <a href="${request.contextPath}/msg/delete?conversationId=${conversation.get("conversation").conversationId}" class="sns-action-del" data-id="10001_622873">
                                删除
                            </a>
                        </div>
                    </div>
                    <div class="chat-headbox">
                        <span class="msg-num">
                            ${conversation.get("unreadNum")}
                        </span>
                        <a class="list-head">
                            <img alt="头像" src="${request.contextPath}${conversation.get("user").headUrl}">
                        </a>
                    </div>
                    <div class="letter-detail">
                        <a title="${conversation.get("user").name}" class="letter-name level-color-1">
                            ${conversation.get("user").name}
                        </a>
                        <p class="letter-brief">
                            <a href="${request.contextPath}/msg/detail?conversationId=${conversation.get("conversation").conversationId}">
                                ${conversation.get("conversation").content}
                            </a>
                        </p>
                    </div>
                </li>
                </#list>
            </ul>

        </div>
    </div>
<#include "footer.ftl">
