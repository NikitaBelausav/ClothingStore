<!-- Reset Password Form -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="Manage Auctions">
    <h2>Manage Auctions</h2>

    ${content}

    <script>
        function askDelete(id) {
            if (confirm("Are you sure you want to delete auction " + id + "?") == true) {
                alert("Deleting " + id + "!");
                fetch("./support/manage/auction?auctionID=" + id, {
                  method: "DELETE",
                  redirect: "follow"
                }).then(res => {
                    self.location = res.url;
                });
            }
        }    
    </script>
</t:base>