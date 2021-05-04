$(document).ready(function() {

    var win = $(window);
    var doc = $(document);
    let pageNum = 1;
    // var totalPage = data.charAt(data.length-1);

    // Each time the user scrolls
    win.scroll(function() {
        var totalPage=2;
        // Vertical end reached?
        if (doc.height() - win.height() == win.scrollTop() && totalPage>=pageNum) {
            // New row
            pageNum = pageNum+1;
            let $categoryId = $("#categoryId").val();

            // let data = JSON.stringify($('#nextPage').serializeFormJSON());

            let data = {"categoryId": $categoryId};
           //  console.log(data);
            var conextRoot = "/" + window.location.pathname.split('/')[1];

            $.ajax({
                type: "POST",
                url: conextRoot + "/category/productsNext/",
                data: data,
                // contentType: "application/json",
                success: function (data) {
                    console.log(data);
                    if(data=="done" ||data==null){
                        console.log("do nothing");
                    }
                    else {
                        totalPage = parseInt(data.charAt(data.length - 1));
                        var newdata = data.substring(1, data.length - 2);
                        var arr = newdata.split(",");
                        for (var i = 0; i < arr.length; i++) {
                            console.log(arr[i]);
                            var objmo = arr[i].replaceAll(";", ",");
                            console.log(objmo);

                            var obj = JSON.parse(objmo);
                            console.log(obj["productId"]);
                            dispList(obj)
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log('error');
                }
            });
        }

    });
});

function dispList(resp) {
    // Remove old Data
    console.log(resp)

    // Update new data
        let $row ='<tbody>\n'+

            '                                <tr>\n' +
            '                                    <td >'+ resp["productId"]+'</td>\n'+
            '                                    <td >'+ resp["productName"]+'</td>\n' +
            '                                    <td >'+ resp["productDescription"]+'</td>\n' +
            '                                    <!--                                    <td th:text="${product.imageList}">Edinburgh</td>-->\n' +
            '                                    <td >'+ resp["productPrice"]+'</td>\n' +
            '                                    <td >'+ resp["uploadDate"]+'</td>\n' +
            '                                    <th><a href="/customer/product?id='+ resp["productId"]+'" class="btn ui-state-default" style="">View Product Details</a></th>\n'+
            '                                </tr>\n' +
            '                                </tbody>';
        $('#dataTable').append($row);

}