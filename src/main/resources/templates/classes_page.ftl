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
        <a href="./all_packages.html" title="" target="summary" class="">all_packages</a> -
        <img src="./images/package.png" alt="" class="" />
        ${title}
    </h1>

    <table>
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
                <th class="packageNumber_cols" title="${i.name}">
                    ${i.positionIndex}
                </th>
            </#list>
        </tr>

        <#assign rowIndex = 0>

        <#list rows as class>
            <tr class="${class?item_parity}">
                <th class="packageName_rows">
                    <img src="./images/class.png" alt="Class" />
                    <span>${class.name}</span>
                </th>
                <th class="packageNumber_rows">
                    ${class.positionIndex}
                </th>

                <#assign columnIndex = 0>

                <#list class.numberOfDependencies as dependCount>
                    <#if (columnIndex == class.positionIndex-1)>
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