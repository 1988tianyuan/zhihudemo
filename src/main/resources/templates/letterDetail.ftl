<#include "header.ftl">
<link rel="stylesheet" href="../static/styles/letter.css">

<div id="main">
    <div class="zg-wrap zu-main clearfix ">
        <ul class="letter-chatlist">
            <#list messages as message>
            <li id="msg-item-4009580">
                <a class="list-head">
                    <img alt="头像" src="${request.contextPath}${message.get("fromUser").headUrl}">
                </a>
                <div>${message.get("fromUser").name}</div>
                <div class="tooltip fade right in">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner letter-chat clearfix">
                        <div class="letter-info">
                            <p class="letter-time">${message.get("message").createDate?string('yyyy年MM月dd日 hh:mm:ss')}</p>
                        </div>
                        <p class="chat-content">
                            ${message.get("message").content}
                        </p>
                    </div>
                </div>
            </li>
            </#list>
        </ul>

    </div>
</div>


<#include "footer.ftl">