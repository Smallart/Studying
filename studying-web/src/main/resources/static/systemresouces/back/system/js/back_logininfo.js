layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','layer','studying'],function(){
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        table = studying.table(),
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
            filter: 'loginInfoTable',
            options: {
                elem:'#loginInfoTable',
                height: 'full-240',
                url:'/system/loginInfo/find',
                cols:[[
                    {type:'checkbox',fixed:'left'},
                    {field:'infoId',title:'访问编号',align:'center'},
                    {field:'loginName',title:'登录名称',align:'center'},
                    {field:'ipAddr',title:'登录地址',align:'center'},
                    {field:'loginLocation',title:'登录地点',align:'center'},
                    {field:'browser',title:'浏览器',align:'center'},
                    {field:'os',title:'操作系统',align:'center'},
                    {title:'登录状态',align:'center',toolbar:'#status'},
                    {field:'msg',title:'操作信息',align:'center'},
                    {title:'登录时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.loginTime,'yyyy-MM-dd')}}</div>"},
                ]]
            },
            events:{
                checkbox:tableCheckBoxEvent
            }
        },
        globalEvents:{
            enable: true,
            batchDelete: delEvent,
            clearAll: clearAll,
            unlock: unlockEvent,
            export: exportEvent,
            reset: resetEvent
        }
    };


    studying.render(studyingConfig);

    function submitEvent(obj) {
        let data = obj.field;
        let params={
            ipAddr: data.ipAddr,
            loginName: data.loginName,
            status: data.status,
            createStartTime: data.createStartTime,
            createEndTime: data.createEndTime
        };
        loadTable(params);
        return false;
    }

    function loadTable(params) {
        table.reload('loginInfoTable',{
            url: '/system/loginInfo/find',
            where: params
        });
    }

    function tableCheckBoxEvent() {
        let checkStatus = table.checkStatus('loginInfoTable');
        let unlock =  $('#unlock');
        let del = $('#del');
        if (checkStatus.data.length>0){
            if (unlock.hasClass('layui-btn-disabled')){
                unlock.removeClass('layui-btn-disabled');
            }
            if (del.hasClass('layui-btn-disabled')){
                del.removeClass('layui-btn-disabled');
            }
        }else{
            if (!unlock.hasClass('layui-btn-disabled')){
                unlock.addClass('layui-btn-disabled');
            }
            if (!del.hasClass('layui-btn-disabled')){
                del.addClass('layui-btn-disabled');
            }
        }
    }

    function resetEvent() {
        loadTable(null);
    }
    
    function RestoreButtonStatus() {
        $('#unlock').addClass('layui-btn-disabled');
        $('#del').addClass('layui-btn-disabled');
    }

    /*********************************全局事件*****************************************/

    function delEvent() {
        layer.confirm('是否删除相关日志？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let ids = table.checkStatus('loginInfoTable').data.map(item=>item.infoId).join(",");
            $.ajax({
                url: '/system/loginInfo/del/'+ids,
                method: 'get',
                success: function (resp) {
                    layer.msg(resp.data);
                    if (resp.code==0){
                        closecurrent();
                    }
                }
            });
        });

    }

    function closecurrent() {
        setTimeout(function(){
            loadTable(null);
            RestoreButtonStatus();
        },1000);
    }

    function clearAll() {
        layer.confirm('是否清空所有日志信息？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.ajax({
                url:'/system/loginInfo/loginInfoService',
                method: 'get',
                success:function (resp) {
                    layer.msg(resp.msg);
                    if (resp.code==0){
                        closecurrent();
                    }
                }
            });
        });
    }

    function exportEvent() {

    }
    function unlockEvent() {

    }
});