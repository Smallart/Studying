layui.config({
    base: '/expandjs/'
}).extend({
    JqueryZtree: 'ztree/jquery-ztree'
}).use(['JqueryZtree','form'],function () {
    let $ = layui.JqueryZtree,
        form = layui.form,zTreeObj,setting={
        async:{
            enable: true,
            type: 'get',
            url:'/system/deptSelect'
        },
        data:{
            key:{
                name: 'departmentName'
            },
            simpleData:{
                enable: true,
                idKey: "departmentId",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        callback:{
            onAsyncSuccess:function () {
                zTreeObj.expandAll(true);
            }
        }
    },events={
            fold:function () {
                zTreeObj.expandAll(false);
            },
            unfold:function () {
                zTreeObj.expandAll(true);
            }
        },
    searchToggle = $('#searchToggle');
    $(document).ready(function () {
        zTreeObj = $.fn.zTree.init($('#companyTree'),setting);
    });
    searchToggle.on('click',function () {
        let form = $('form');
        searchToggle.toggleClass('layui-icon-up');
        searchToggle.toggleClass('layui-icon-down');
        if (form.is(":hidden")){
            form.show();
        }else {
            form.hide();
        }
    });

    form.on('submit(*)',function (data) {
       let nodes = zTreeObj.getNodesByParamFuzzy("departmentName",data.field.deptName,null);
       let allNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
       allNodes.forEach(item=>{
           item.highlight = false;
           zTreeObj.updateNode(item);
       });
       zTreeObj.expandAll(false);
       zTreeObj.expandNode(zTreeObj.getNodes()[0],true);
       nodes.forEach(item=>{
           item.highlight = true;
           zTreeObj.updateNode(item);
           zTreeObj.expandNode(item.getParentNode(),true);
       })
    });

    $('body').on('click','*[studying-event]',function () {
        let eventName = $(this).attr('studying-event');
        eventName && events[eventName].call(this);
    });
});