<!-- Manage Bids Form -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="Manage bids">
    <h2>Manage Bids</h2>

    ${content}

    <script>
        function askDelete(id) {
            if (confirm("Are you sure you want to delete bid " + id + "?") == true) {
                alert("Deleting " + id + "!");
                fetch("./support/manage/bid?bidID=" + id, {
                  method: "DELETE",
                  redirect: "follow"
                }).then(res => {
                    self.location = res.url;
                });
            }
        }    
    </script>
</t:base>