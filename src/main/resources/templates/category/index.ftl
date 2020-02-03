<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">

                <form role="form" method="post" action="/sell/seller/category/save">
                    <div class="form-group">
                        <label >类目名字</label>
                        <input class="form-control" name="categoryName" value="${(productCategory.categoryName)!""}" type="text"   />
                    </div>
                    <div class="form-group">
                        <label >类目type</label>
                        <input class="form-control" name="categoryType" value="${(productCategory.categoryType)!""}" type="text"   />
                    </div>
                    <input type="hidden" name="categoryId" value="${(productCategory.categoryId)!""}"/>
                     <button type="submit" class="btn btn-default">Submit</button>
                </form>

        </div>
    </div>

</div>





</body>
</html>