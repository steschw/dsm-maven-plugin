<#macro render cell rowName colName>
    <#if !cell.valid>
        <td class="diagonal" title="${colName}">
            X
        </td>
    <#else>
        <#if (cell.dependencyCount > 0)>
            <#if cell.cycle>
                <td class="cycle" title="${colName} has cycle dependency with ${rowName}">
                    ${cell.dependencyCount}C
                </td>
            <#else>
                <td title="${colName} uses ${rowName}">
                    ${cell.dependencyCount}
                </td>
            </#if>
        <#else>
            <td>
            </td>
        </#if>
    </#if>
</#macro>
