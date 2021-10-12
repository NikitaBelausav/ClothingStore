<!-- Question View -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="${question.questionText}">
    <h2>${question.questionText}</h2>
    <h6>Asked by ${question.euLogin}</h6>
    <br>
    ${content}
    <a href="./support">Back</a>
</t:base>