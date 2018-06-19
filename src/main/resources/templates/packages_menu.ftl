<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Packages</title>
    <meta http-equiv="Content-Type" content="text/html; charset=${.output_encoding}" />
    <meta http-equiv="Content-Language" content="${.lang}" />
    <meta name="robots" content="index,follow" />
    <style type="text/css">
        @import url(css/style.css);
    </style>
</head>
<body>
    <b>Packages:</b>
    <ul>
        <li>
            <a title="" target="summary" href="./all_packages.html"	class="">
                <img src="./images/package.png" alt="" class="" />
                all_packages
            </a>
        </li>
        <#list aPackageNames as package>
        <li>
            <a title="" target="summary" href="./${package}.html" class="">
                <img src="./images/package.png" alt="" class="" />
                ${package}
            </a>
        </li>
        </#list>
    </ul>
</body>
</html>