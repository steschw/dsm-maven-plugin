<#macro render cell rowName colName>
    <#if !cell.valid>
        <td class="diagonal" title="${colName}">
            -
        </td>
    <#else>
        <#if (cell.dependencyCount > 0)>
            <#if (cell.cycleCount > 0)>
                <td class="ratio_${(cell.dependencyRatio * 10)?round} cycle" title="${colName} has cycle dependency with ${rowName}">
                    ${cell.dependencyCount}C
                </td>
            <#else>
                <td class="ratio_${(cell.dependencyRatio * 10)?round}" title="${colName} uses ${rowName}">
                    ${cell.dependencyCount}
                </td>
            </#if>
        <#else>
            <td>
            </td>
        </#if>
    </#if>
</#macro>
