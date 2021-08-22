layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','studying'],function(){
    let $= layui.jquery,
        studying = layui.studying,
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: submitEvent
                    }
                },
                dateTime:{
                    enable: true
                }
            },
            table:{
                enable: true,
                filter: 'dictDetailTable',
                options: {
                    elem:'#dictDetailTable',
                    height: 'full-240',
                    url:'/system/dict/detail/find?dictType='+$('input[name="dictType"]').val(),
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'dictCode',title:'字典编码',align:'center'},
                        {field:'dictLabel',title:'字典标签',align: 'center',width: 110},
                        {field:'dictValue',title:'字典键值',align:'center'},
                        {field:'dictSort',title:'字典排序',align:'center'},
                        {title:'状态',align:'center',width:80,templet: '#status'},
                        {field:'remark',title:'备注',align:'center'},
                        {title:'创建时间',align:'center',sort:true,width:250,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>"},
                        {title:'操作',fixed:'right',align:'center',toolbar:'#operatorButtons',width:140}
                    ]]
                },
                events:{
                    checkbox:tableCheckBoxEvent,
                    tool: tableToolEvent
                }
            },
            globalEvents:{
                enable: true,
                add: addEvent,
                reset: resetEvent,
                del: delEvent,
                close: close,
                editEvent: editGlobalEvent
            }
        },
        table = studying.table(),
        form = studying.form();

    init();

    function init() {
        initSelect();
        studying.render(studyingConfig);
    }

    function initSelect(){
        $.get({
            url:'/system/dict/dictData/allDictTypeName',
            method: 'get',
            success: function (resp) {
                $('#selectDictType').html(resp.map(item=>`<option value="${item.dictType}">${item.dictName}</option>`).join(''));
                form.render('select');
                form.val('dictDetail',{
                    "dictType": $('input[name="dictType"]').val()
                });
            }
        });
    }

    // table Events
    function tableCheckBoxEvent(){
        let checkStatus = table.checkStatus('dictDetailTable');
        let edit =  $('#edit');
        let del = $('#del');
        if (checkStatus.data.length>0){
            if (edit.hasClass('layui-btn-disabled')){
                edit.removeClass('layui-btn-disabled');
            }
            if (del.hasClass('layui-btn-disabled')){
                del.removeClass('layui-btn-disabled');
            }
        }else{
            if (!edit.hasClass('layui-btn-disabled')){
                edit.addClass('layui-btn-disabled');
            }
            if (!del.hasClass('layui-btn-disabled')){
                del.addClass('layui-btn-disabled');
            }
        }
    }

    /**
     * tool Event
     */
    function tableToolEvent(obj) {
        switch (obj.event) {
            case "edit" :
                editEvent(obj.data);
                break;
            case "delete":
                deleteEvent(obj.data);
                break;
        }
    }

    function reloadTable(params){
        table.reload('dictDetailTable',{
            url:'/system/dict/detail/find',
            where: params
        });
    }

    function submitEvent(obj){
        let params = {
            dictDetailStatus: obj.field.dictDetailStatus,
            dictLabel: obj.field.dictLabel,
            dictType: obj.field.dictType
        };
        reloadTable(params);
        $('input[name="dictType"]').val(params.dictType);
        return false;
    }

    function addEvent() {
        addIndex($('input[name="dictType"]').val());
    }

    function addIndex(dictType) {
        layer.open({
            title: "添加类别",
            type: 2,
            content: '/system/dict/dictDetail/add?dictType='+dictType,
            area:['520px','500px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            }
        });
    }

    function resetEvent() {
        reloadTable({dictType:$('input[name="dictType"]').val()});
        form.val('dictDetail',{
            "dictDetailStatus":null,
            "dictLabel":"",
            "dictType": $('input[name="dictType"]').val()
        });
    }

    function delEvent() {
        layer.confirm('是否删除所选择字典数据？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let data = table.checkStatus('dictDetailTable').data;
            let dictIds = data.map(item=>item.dictCode);
            $.ajax({
                url: '/system/dict/dictDetail/batchDelete/'+dictIds,
                type: 'get',
                headers:{
                    'Content-Type': 'application/json'
                },
                success:function (resp) {
                    layer.msg(resp.msg);
                    if(resp.code==0){
                        reloadTable({dictType:$('input[name="dictType"]').val()});
                    }
                }
            });
        });
    }

    // 关闭当前页面
    function close() {
        top.layui.pageOp.closeThisTab();
    }

    function editGlobalEvent() {
        let data = table.checkStatus('dictDetailTable').data;
        if (data.length>1){
            layer.msg("修改个数不超过一个");
            return;
        }
        layer.open({
            title: "修改字典数据",
            type: 2,
            content: '/system/dict/dictDetail/editIndex/'+data[0].dictCode,
            area:['520px','500px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            }
        });
    }

    function deleteEvent(data) {
        layer.confirm('是否删除所选择字典数据？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.ajax({
                url: '/system/dict/dictDetail/batchDelete/'+data.dictCode,
                type: 'get',
                headers:{
                    'Content-Type': 'application/json'
                },
                success:function (resp) {
                    layer.msg(resp.msg);
                    if(resp.code==0){
                        reloadTable({dictType:$('input[name="dictType"]').val()});
                    }
                }
            });
        });
    }

    function editEvent(data) {
        layer.open({
            title: "修改字典数据",
            type: 2,
            content: '/system/dict/dictDetail/editIndex/'+data.dictCode,
            area:['520px','500px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            }
        });
    }
});