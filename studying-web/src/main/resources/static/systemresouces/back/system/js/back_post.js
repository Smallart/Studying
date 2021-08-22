layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','studying','layer'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        table = studying.table(),
        form = studying.form(),
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: submitEvent
                    }
                }
            },
            table:{
                enable: true,
                filter: 'postTable',
                options: {
                    elem:'#postTable',
                    height: 'full-200',
                    url:'/system/post/find',
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'postCode',title:'岗位编码',align:'center',sort:true},
                        {field:'postName',title:'岗位名称',sort:true,align: 'center',width: 110,sort:true},
                        {field:'postSort',title:'显示顺序',align:'center',sort:true},
                        {title:'状态',align:'center',width:120,templet: `<a class="layui-btn layui-btn-radius layui-btn-xs {{d.status=='0'?'':'layui-btn-warm'}}">{{d.status==0?'正常':'停用'}}</a>`},
                        {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>"},
                        {title:'操作',fixed:'right',align:'center',toolbar:'#barOperator',width:180},
                    ]]
                },
                events:{
                    tool: tableToolEvent,
                    checkbox:tableCheckBoxEvent,
                }
            },
            globalEvents:{
                enable: true,
                add: addEvent,
                edit: editEvent,
                del: delEvent,
                reset: resetEvent
            }
        };

    studying.render(studyingConfig);

    // 查询
    function submitEvent(obj) {
        let data = obj.field;
        let param = {
            postCode: data.postCode,
            postName: data.postName,
            postStatus: data.postStatus
        };
        reloadTable(param);
    }

    //全局 add事件
    function addEvent () {
        layer.open({
            title: "添加岗位",
            type: 2,
            content: '/system/post/add',
            area:['600px','520px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            btn2: function () {

            }
        });
    }
    //全局 edit事件
    function editEvent(){
        let data = table.checkStatus('postTable').data;
        if (data.length!=1){
            layer.msg('选择其中一个岗位进行修改');
            return;
        }
        layer.open({
            title: "修改岗位",
            type: 2,
            content: '/system/post/edit/'+data[0].postId,
            area:['600px','520px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            btn2: function () {

            }
        });
    }
    //全局 del事件
    function delEvent() {
        layer.confirm('是否删除所选择岗位？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let data = table.checkStatus('postTable').data;
            let postIds = data.map(item=>item.postId).join(',');
            $.get("/system/post/batchDelete?postIds="+postIds,function (data) {
                layer.msg(data.msg);
                reloadTable(null);
            });
        });

    }
    // 重置事件
    function resetEvent() {
        reloadTable(null);
    }

    // tableTool事件
    function tableToolEvent(obj) {
        switch (obj.event) {
            case 'edit':
                editTool(obj.data);
                break;
            case 'del':
                delTool(obj.data);
                break;
        }
    }

    function editTool(data) {
        layer.open({
            title: "修改岗位",
            type: 2,
            content: '/system/post/edit/'+data.postId,
            area:['600px','520px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            btn2: function () {

            }
        });
    }

    function delTool(data) {
        layer.confirm('是否删除所选择岗位？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.get("/system/post/batchDelete?postIds="+data.postId,function (data) {
                layer.msg(data.msg);
                reloadTable(null);
            });
        });
    }

    function tableCheckBoxEvent(){
        let checkStatus = table.checkStatus('postTable');
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

    function reloadTable(params) {
        table.reload('postTable',{
            url: '/system/post/find',
            where: params
        })
    }
});