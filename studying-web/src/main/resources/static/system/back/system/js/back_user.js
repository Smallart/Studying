layui.config({
    base: '/expandjs/'
}).extend({
    JqueryLayout: 'layout/jquery-layout',
    JqueryZtree: 'ztree/jquery-ztree'
}).use(['JqueryLayout','JqueryZtree','laydate','table','form'],function () {
    let $ = layui.JqueryZtree,
        laydate = layui.laydate,
        table = layui.table,
        form = layui.form,
        layout_config = {
            togglerTip_closed: "打开公司面板",
            togglerTip_open: "收起公司面板"
        },setting = {
            async:{
                enable:true,
                type: "get",
                url: "/system/dynamicGetDept",
                dataType: "json",
                autoParam: ["departmentId"]
            },
            callback:{
                onAsyncSuccess:function (event, treeId, treeNode) {
                    if (treeNode == undefined|null){
                        var ztree = $.fn.zTree.getZTreeObj('companyTree');
                        ztree.expandNode(ztree.getNodes()[0],true,false);
                    }
                }
            },
            data:{
                simpleData: {
                    enable: true,
                    idKey: "departmentId",
                    pIdKey: "parentId",
                    rootPId: 0
                },
                key:{
                    name:"departmentName"
                }
            }
        },zTreeObj,events={
            refresh:function () {
                zTreeObj.reAsyncChildNodes(null,'refresh');
            },
            toggle:function () {
                let i = $(this).find('i').first();
                i.toggleClass('layui-icon-down');
                i.toggleClass('layui-icon-up');
                if(i.hasClass('layui-icon-up')){
                    zTreeObj.expandAll(false);
                }else{
                    zTreeObj.expandAll(true);
                }
            },
            batchDelete:function () {
                let checkedData = table.checkStatus('userTable');
                let ids = checkedData.data.map(item=>item.userId).join(',');
                console.log(ids);
                fetch('')
            },
            add:function () {
                //todo 去了解下 top是个啥
                top.layui.pageOp.openTabsPage('/system/userAdd','添加用户');
            }
        };

    //初始化layout
    $(document).ready(function () {
        $('body').layout(layout_config);
    });
    //初始化
    init();

    function init(){
        initTree();
        initDate();
        initTable();
    }
    //初始化组织树
    function initTree(){
        $(document).ready(function () {
            zTreeObj = $.fn.zTree.init($('#companyTree'),setting);
        });
    }
    //初始化日期
    function initDate(){
       let startTime = laydate.render({
            elem: '#create-start-time',
            type: 'datetime',
            form: 'yyyy-MM-dd HH:mm:ss',
            done:function (value,dates,endDate) {
                endTime.config.min = {
                    year: dates.year,
                    month: dates.month - 1,
                    date: dates.date
                };
            }
        });
        let endTime = laydate.render({
            elem: '#create-end-time',
            type: 'datetime',
            form: 'yyyy-MM-dd HH:mm:ss',
            done:function (value,dates,endDate) {
                startTime.config.max = {
                    year: dates.year,
                    month: dates.month - 1,
                    date: dates.date
                };
            }
        });
    }
    //初始化table
    function initTable(){
        table.render({
            elem:'#userTable',
            height: 'full-260',
            url:'/system/user/find',
            request:{
                pageName: 'limit',
                limitName: 'offset'
            },
            page: true,
            skin: 'line',
            cols:[[
                {type:'checkbox',fixed:'left'},
                {field:'userId',title:'Id',align:'center'},
                {field:'loginName',title:'登录名称',sort:true,align: 'center',width: 100},
                {field:'userName',title:'用户名称',align:'center',width:100},
                {field:'deptName',title:'部门',align:'center',width:120},
                {field:'iphone',title:'手机',align:'center',width:130},
                {title:'用户状态',align:'center',width:100,templet:function (res) {
                    return `<input type="checkbox" lay-skin="switch" lay-text="正常|停用" ${res.status==0?'checked':''}>`;
                }},
                {title:'创建时间',align:'center',sort:true,width:120,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>"},
                {title:'操作',fixed:'right',align:'center',toolbar:'#barOperator',width:250},
            ]],
            parseData:function (res) {
                return{
                    "code":res.code,
                    "msg":res.msg,
                    "count":res.data.total,
                    "data": res.data.data
                }
            },
            done:function () {
                $('.btn-box').on('mouseenter mouseleave',function (event) {
                    if (event.type=='mouseenter'){
                        $(this).find('.more-operate').show();
                    }else if (event.type == 'mouseleave'){
                        $(this).find('.more-operate').hide();
                    }
                });
            }
        });
    }
    //重新加载Table
    function renderTable(param){
        table.reload('userTable',{
            url:'/system/user/find',
            where:param
        });
    }
    // 提交
    form.on('submit(*)',function (data) {
        let param = {
          'loginName': data.field.loginName,
           'createStartTime': data.field.createStartTime,
           'createEndTime' : data.field.createEndTime,
           'userStatus': data.field.userStatus
        };
        renderTable(param);
        return false;
    });

    table.on('checkbox(userTable)',function (obj) {
        let checkStatus = table.checkStatus('userTable');
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
    });

    $('body').on('click','*[studying-event]',function () {
       let eventName = $(this).attr('studying-event');
       eventName && events[eventName].call(this);
    });
});