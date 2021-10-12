<!-- Generate Sales Report Form View -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="Generate Report">
    <h2>Generate a Sales Report</h2>
    <form method="post">
        <ul>
            <li>
                <input type="checkbox" name="fTotal">
                <label for="fTotal">Total Earnings</label>
            </li>
            <li>
                <input type="checkbox" name="fBestSelling">
                <label for="fBestSelling">Best Selling Items</label>
            </li>
            <li>
                <input type="checkbox" name="fBestBuyers">
                <label for="fBestBuyers">Best Buyers</label>
            </li>
            <li>
                <input type="checkbox" name="fEarningsAll" id="tall">
                <label for="fEarningsAll">Earnings Per:</label>
                <ul>
                    <li>
                      <input type="checkbox" name="fPerItem">
                      <label for="fPerItem">Item</label>
                    </li>
                    <li>
                      <input type="checkbox" name="fPerType">
                      <label for="fPerType">Item Type</label>
                    </li>
                    <li>
                      <input type="checkbox" name="fPerUser">
                      <label for="fPerUser">End user</label>
                    </li>
                </ul>
            </li>
        </ul>

        <button type="submit">Generate Report</button>
    </form>

    <script>
        //  helper function to create nodeArrays (not collections)
        const nodeArray = (selector, parent=document) => [].slice.call(parent.querySelectorAll(selector))
        //  checkboxes of interest 
        const allThings = nodeArray('input');


        //  global listener
        addEventListener('change', e => {
          let check = e.target;
    
          //  exit if change event did not come from 
          //  our list of allThings 
          if(allThings.indexOf(check) === -1) return;
    
          //  check/unchek children (includes check itself)
          const children = nodeArray('input', check.parentNode);
          children.forEach(child => child.checked = check.checked);
    
          //  traverse up from target check
          while(check){
          
            //  find parent and sibling checkboxes (quick'n'dirty)
            const parent   = (check.closest(['ul']).parentNode).querySelector('input');
            const siblings = nodeArray('input', parent.closest('li').querySelector(['ul']));
    
            //  get checked state of siblings
            //  are every or some siblings checked (using Boolean as test function) 
            const checkStatus = siblings.map(check => check.checked);
            const every  = checkStatus.every(Boolean);
            const some = checkStatus.some(Boolean);   
            
            //  check parent if all siblings are checked
            //  set indeterminate if not all and not none are checked
            parent.checked = every;   
            parent.indeterminate = !every && every !== some;
    
            //  prepare for nex loop
            check = check != parent ? parent : 0;
          }
        })

    </script>
</t:base>