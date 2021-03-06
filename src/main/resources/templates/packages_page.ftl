<#import "cell.ftl" as cell>
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

    <table>
        <colgroup>
            <col/>
            <#if showNumbers>
                <col/>
            </#if>
        </colgroup>
        <colgroup>
            <#list rows as package>
                <col class="${package?item_parity}"/>
            </#list>
        </colgroup>

        <#if showColNames>
            <tr>
                <th></th>
                <#if showNumbers>
                    <th></th>
                </#if>
                <#list rows as package>
                    <th class="packageName_cols" title="${package.name}">
                        <a href="${package.name}.html">
                            ${package.obfuscatedPackageName}
                        </a>
                    </th>
                </#list>
            </tr>
        </#if>
        <#if showNumbers>
            <tr>
                <th></th>
                <th></th>
                <#list rows as package>
                    <th class="packageNumber_cols" title="${package.name}">
                        ${package.positionIndex}
                    </th>
                </#list>
            </tr>
        </#if>

        <#assign rowIndex = 0>

        <#list rows as package>
            <tr class="${package?item_parity}">
                <th class="packageName_rows" title="${package.name}">
                    <img src="./images/package.png"	alt="Package"/>
                    <a href="${package.name}.html">${package.obfuscatedPackageName}</a>
                    <span class="numberOfClasses" title="${numberOfClasses[rowIndex]} classes">(${numberOfClasses[rowIndex]})</span>
                </th>
                <#if showNumbers>
                    <th class="packageNumber_rows">
                        ${package.positionIndex}
                    </th>
                </#if>

                <#assign columnIndex = 0>

                <#list package.cells as c>
                    <@cell.render cell=c rowName=names[rowIndex] colName=names[columnIndex]/>

                    <#assign columnIndex=columnIndex + 1>
                </#list>

            </tr>

            <#assign rowIndex=rowIndex + 1>

        </#list>
    </table>
</body>