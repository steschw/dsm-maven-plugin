<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=${.output_encoding}" />
    <meta http-equiv="Content-Language" content="${.lang}" />
    <meta name="robots" content="index,follow" />
    <style type="text/css">
        @import url(css/style.css);
    </style>
</head>
<body>

    <h1>
        DSM Report -
        <img src="./images/package.png" alt="" class="" />
        <a href="./all_packages.html" title="" target="summary" class="">${title}</a>
    </h1>

    <table cellspacing="0" cellpadding="0">
        <colgroup>
            <col/>
            <col/>
        </colgroup>
        <colgroup>
            <#list rows as i>
                <col class="${i?item_parity}"/>
            </#list>
        </colgroup>
        <tr>
            <th></th>
            <th></th>
            <#list rows as i>
                <th class="packageName_cols" title="${i.name}">${i.positionIndex}</th>
            </#list>
        </tr>

        <#assign rowIndex = 0>

        <#list rows as package>
            <tr class="${package?item_parity}">
                <th class="packageName_rows" title="${package.name}">
                    <img src="./images/package.png"	alt="Package"/>
                    <a href="${package.name}.html">${package.obfuscatedPackageName}</a>
                    <span class="numberOfClasses" title="${numberOfClasses[rowIndex]} classes">(${numberOfClasses[rowIndex]})</span>
                </th>
                <th class="packageNumber_rows">
                    ${package.positionIndex}
                </th>

                <#assign columnIndex = 0>

                <#list package.numberOfDependencies as dependCount>
                    <#if (columnIndex == package.positionIndex-1)>
                        <td class="diagonal" title="${names[columnIndex]}">
                            ${dependCount}
                        </td>
                    <#else>
                        <#if ("${dependCount}"?length > 0)>
                            <#if "${dependCount}"?ends_with("C")>
                                <td class="cycle" title="${names[columnIndex]} has cycle dependency with ${names[rowIndex]}">
                                    ${dependCount}
                                </td>
                            <#else>
                                <td title="${names[columnIndex]} uses ${names[rowIndex]}">
                                    ${dependCount}
                                </td>
                            </#if>
                        <#else>
                            <td>
                                ${dependCount}
                            </td>
                        </#if>
                    </#if>
                    <#assign columnIndex=columnIndex + 1>
                </#list>
            </tr>
            <#assign rowIndex=rowIndex + 1>
        </#list>
    </table>
</body>