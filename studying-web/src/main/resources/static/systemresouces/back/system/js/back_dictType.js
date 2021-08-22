layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','studying','layer'],function(){
    let $= layui.jquery,
        layer = layui.layer,
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
                filter: 'dictTypeTable',
                options: {
                    elem:'#dictTypeTable',
                    height: 'full-240',
                    url:'/system/dict/type/find',
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'dictId',title:'字典主键',align:'center',width:90},
                        {field:'dictName',title:'字典名称',align:'center',sort:true},
                        {title:'字典类型',align:'center',sort:true,width:150,templet:function (res) {
                                return `<a class="dictType" href="javaScript:void(0)" studying="detail">${res.dictType}</a>`;
                            }},
                        {title:'状态',align:'center',toolbar:'#dictStatus'},
                        {field:'remark',title:'备注',align:'center'},
                        {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>",width: 170},
                        {title:'操作',align:'center',toolbar:'#operatorButtons',width:200,fixed: 'right'},
                    ]],
                    done:function () {
                        $('.btn-box').on('mouseenter mouseleave',function (event) {
                            if (event.type=='mouseenter'){
                                $(this).find('.more-operate').show();
                            }else if (event.type == 'mouseleave'){
                                $(this).find('.more-operate').hide();
                            }
                        });
                        $("a[studying='detail']").on('click',function (event) {
                            top.layui.pageOp.openTabsPage('/system/dict/detail/'+this.textContent,'字典数据');
                        });
                    }
                },
                events:{
                    checkbox:tableCheckBoxEvent,
                    tool: tableToolEvent
                }
            },
            globalEvents:{
                enable: true,
                add: addEvent,
                edit: editEvent,
                del: delEvent,
                reset: exportData,
                clearCache: clearCache
            }
        },
        table = studying.table();

    studying.render(studyingConfig);

    function submitEvent(obj) {
        let data = obj.field;
        let params={
            dictTypeName:data.dictTypeName,
            dictTypecode:data.dictTypeCode,
            dictTypeStatus: data.dictTypeStatus,
            createStartTime: data.preTime,
            createEndTime: data.nextTime
        };
        reloadTable(params);
        return false;
    }

    /**
     * 重新加载
     * @param params
     */
    function reloadTable(params) {
        table.reload('dictTypeTable',{
            url: '/system/dict/type/find',
            where: params
        });
    }

    /**
     * 表格复选框连通修改，删除事件
     */
    function tableCheckBoxEvent() {
        let checkStatus = table.checkStatus('dictTypeTable');
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
     * 表格工具事件
     * @param obj
     */
    function tableToolEvent(obj) {
        switch (obj.event) {
            case 'edit':
                layer.open({
                    title: "更改类别",
                    type: 2,
                    content: '/system/dict/dictType/edit?dictId='+obj.data.dictId,
                    area:['600px','450px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {
                        layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                    }
                });
                break;
            case 'list':
                top.layui.pageOp.openTabsPage('/system/dict/detail/'+obj.data.dictType,'字典数据');
                break;
            case 'delete':
                layer.confirm('是否删除所选择字典类型？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
                    $.ajax({
                        url: '/system/dict/dictType/Delete/'+obj.data.dictId,
                        type: 'get',
                        headers:{
                            'Content-Type': 'application/json'
                        },
                        success:function (resp) {
                            layer.msg(resp.msg);
                            if(resp.code==0){
                                reloadTable(null);
                                tableCheckBoxEvent();
                            }
                        }
                    });
                });
                break;
        }
    }

    /*****************************************GlobalEvent********************************************************/
    /**
     * 全局添加事件
     */
    function addEvent() {
        layer.open({
            title: "添加类别",
            type: 2,
            content: '/system/dict/dictType/add',
            area:['600px','450px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            }
        });
    }

    /**
     * 全局编辑
     */
    function editEvent() {
        let data = table.checkStatus('dictTypeTable').data;
        if (data.length!=1){
            layer.msg("选择一个字典类型进行编辑");
            return;
        }
        layer.open({
            title: "更改类别",
            type: 2,
            content: '/system/dict/dictType/edit?dictId='+data[0].dictId,
            area:['600px','450px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            }
        });
    }

    /**
     * 全局删除
     */
    function delEvent() {
        layer.confirm('是否删除所选择字典类型？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let data = table.checkStatus('dictTypeTable').data;
            let dictIds = data.map(item=>item.dictId);
            $.ajax({
                url: '/system/dict/dictType/Delete/'+dictIds,
                type: 'get',
                headers:{
                    'Content-Type': 'application/json'
                },
                success:function (resp) {
                    layer.msg(resp.msg);
                    if(resp.code==0){
                        reloadTable(null);
                        tableCheckBoxEvent();
                    }
                }
            });
        });
    }

    /**
     * 清除缓存
     */
    function clearCache() {

    }

    /**
     * 导出
     */
    function exportData() {

    }
});