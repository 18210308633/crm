function subsearch(){
    $('#searchForm').submit();
}
var total = $('#total').val();

if(total == 0){
    $("#pageLimit").text("暂无数据");
}else{
	var pagesize = $('#pageSize').val();
	var pageNo = $('#pageNo').val();
	var totalPage = total/pagesize;
	if(totalPage < 1){
        totalPage = 1;
    }else{
        totalPage = Math.ceil(totalPage);
    }
	$("#pageLimit").bootstrapPaginator({
	    currentPage: pageNo,//当前的请求页面。
	    totalPages: totalPage,//一共多少页。
	    size:"large",//应该是页眉的大小。
	    bootstrapMajorVersion: 3,//bootstrap的版本要求。
	    alignment:"right",
	    numberOfPages:10,//一页列出多少数据。
	    shouldShowPage:true,//是否显示按钮
	    itemTexts: function (totalPage, pageNo, pagesize) {
	        switch (totalPage) {
	        case "first": return "首页";
	        case "prev": return "上一页";
	        case "next": return "下一页";
	        case "last": return "尾页";
	        case "page": return pageNo;
	        }//默认显示的是第一页。
	    },
	        onPageClicked: function (numberOfPages, originalEvent, totalPage, currentPage){//给每个页眉绑定一个事件，其实就是ajax请求，其中page变量为当前点击的页上的数字。
	        	$('#pageSize').val("10");
	            $('#pageNo').val(currentPage);
	        	subsearch(); 
	        }
	});
}