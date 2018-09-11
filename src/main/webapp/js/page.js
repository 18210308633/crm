function subsearch(){
    $('#searchForm').submit();
}
var total = $('#total').val();

if(total == 0){
    $('#pagination').text('暂无数据');
}else{
    var pagesize = $('#pageSize').val();
    var pageNo = $('#pageNo').val();
    var totalPage = total/pagesize;
    if(totalPage < 1){
        totalPage = 1;
    }else{
        totalPage = Math.ceil(totalPage);
    }
    var pageSizeOpt = [
        {'value': 10, 'text': '10条/页','selected':false},
        {'value': 15, 'text': '15条/页','selected':false},
        {'value': 20, 'text': '20条/页','selected':false},
        {'value': 30, 'text': '30条/页','selected':false},
        {'value': 50, 'text': '50条/页','selected':false}
    ];

    $(pageSizeOpt).each(function(){
        if(this.value == pagesize){
            this.selected = true;
        }else{
            this.selected = false;
        }
    });
    $("#pagination").whjPaging({
        css: 'css-2',
        pageSizeOpt:pageSizeOpt,
        totalPage: totalPage,
        totalNum: total,
        showPageNum: 5,
        firstPage: '首页',
        previousPage: '上一页',
        nextPage: '下一页',
        lastPage: '尾页',
        skip: '跳至',
        confirm: '确认',
        totalPageText: '共{}页',
        totalNumText: '共{}条记录',
        isShowFL: true,
        isShowPageSizeOpt: true,
        isShowSkip: true,
        isShowRefresh: false,
        isShowTotalPage: true,
        isResetPage: false,
        isShowTotalNum: true,
        callBack: function (currPage, pageSize) {
            $('#pageSize').val(pageSize);
            $('#pageNo').val(currPage);
            subsearch();
        }
    });
    $("#pagination").whjPaging("setPage",pageNo, totalPage);
}