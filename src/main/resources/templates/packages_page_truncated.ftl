<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="ROBOTS" content="index,follow" />
    <meta name="TITLE" content="${title}" />
    <meta name="DESCRIPTION" content="" />
    <meta name="KEYWORDS" content="" />
    <meta name="AUTHOR" content="" />
    <meta name="Copyright" content="" />
    <meta http-equiv="Content-Language" content="${.lang}" />
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

        <tr id="first-row">
            <th></th>
            <#list rows as package>
                <th title="${package.name}">
                    <span class="vertical_text">
                        <a href="${package.name}.html" target="" class="">
                            ${package.obfuscatedPackageName}
                        </a>
                    </span>
                </th>
            </#list>
        </tr>
        <#assign rowIndex=0>
        <#list rows as package>
            <tr>
                <th class="packageName_rows" title="${package.name}">
                    <img src="./images/package.png"	alt="" class="" />
                    <a href="${package.name}.html" target="" class="">
                        ${package.obfuscatedPackageName}
                    </a>
                    (${numberOfClasses[rowIndex]})
                </th>

                <#assign columnIndex=0>

                <#list package.numberOfDependencies as dependCount>
                    <#if (columnIndex == package.positionIndex-1)>
                        <td class="diagonal" title="${names[columnIndex]}">
                            ${dependCount}
                        </td>
                    <#else>
                        <#if ("${dependCount}"?length > 0)>
                            <#if "${dependCount}"?ends_with("C")>
                                <td class="cycle" title="${names[columnIndex]} have cycle dependency with ${names[rowIndex]}">
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
                    <#assign columnIndex=columnIndex +1>
                </#list>
            </tr>
        <#assign rowIndex=rowIndex + 1>
        </#list>
    </table>
</body>