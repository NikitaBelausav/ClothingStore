<!-- Answer Question View -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="Answering ${question.questionText}">
    <h2>Answer ${question.euLogin}'s Question</h2>
    <tf:support-answer question="${question.questionText}" crLogin="${crLogin}" />
</t:base>