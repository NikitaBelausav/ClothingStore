<!-- Sales Report View -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="u" uri="/WEB-INF/taglibs/util.tld" %>

<t:base title="Sales Report">
    <h2>Sales Report:</h2>
    <u:generate-report flags="${flags}" />
</t:base>