<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">

                <form role="form" method="post" action="/sell/seller/product/save">
                    <div class="form-group">
                        <label >商品名字</label>
                        <input class="form-control" name="productName" value="${(productInfo.productName)!""}" type="text"   />
                    </div>
                    <div class="form-group">
                        <label >商品价格</label>
                        <input class="form-control" name="productPrice" value="${(productInfo.productPrice)!""}" type="text"   />
                    </div>
                    <div class="form-group">
                        <label >商品库存</label>
                        <input class="form-control" name="productStock" value="${(productInfo.productStock)!""}" type="text"   />
                    </div>
                    <div class="form-group">
                        <label >商品描述</label>
                        <input class="form-control" name="productDescription" value="${(productInfo.productDescription)!""}" type="text"   />
                    </div>
                    <div class="form-group">
                        <label >商品类目</label>
                        <select name="categoryType">
                            <#list productCategoryList as productCategory>
                            <option value="${productCategory.categoryType}"
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType == productCategory.categoryType>
                                    selected
                                    </#if>
                            >${productCategory.categoryName}</option>
                            </#list>
                        </select>

                    </div>
                    <div class="form-group">
                        <label >商品图片</label>
                         <img src="${(productInfo.productIcon)!""}"/>
                        <input class="form-control" name="productIcon" value="${(productInfo.productIcon)!""}" type="text"   />
                    </div>
<#--                    <div class="form-group">-->
<#--                        <label >商品状态</label>-->
<#--                        <input class="form-control" name="productStatus" value="${(productInfo.getProductStatusEnum().message)!""}" type="text"   />-->
<#--                    </div>-->
                    <input type="hidden" name="productId" value="${(productInfo.productId)!""}"/>
                     <button type="submit" class="btn btn-default">Submit</button>
                </form>

        </div>
    </div>

</div>





</body>
</html>