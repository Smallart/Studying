layui.config({
    base: '/expandjs/'
}).extend({
    JqueryLayout: 'layout/jquery-layout',
    studying: 'studying/studying'
}).use(['JqueryLayout','studying','layer'],function () {
    let $ = layui.JqueryZtree,
        studying = layui.studying,
        layer = layui.layer,
        layout_config = {
            togglerTip_closed: "打开公司面板",
            togglerTip_open: "收起公司面板"
        },
        table = studying.table(),
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: submitEvent
                    },
                    switch:{
                        filter: 'status',
                        event: changeStatus
                    }
                },
                dateTime:{
                    enable: true
                }
            },
            table:{
                enable: true,
                filter: 'userTable',
                options: {
                    elem:'#userTable',
                    height: 'full-260',
                    url:'/system/user/find',
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'userId',title:'Id',align:'center'},
                        {field:'loginName',title:'登录名称',sort:true,align: 'center',width: 110},
                        {field:'userName',title:'用户名称',align:'center',width:100},
                        {field:'deptName',title:'部门',align:'center',width:120},
                        {field:'iphone',title:'手机',align:'center',width:130},
                        {title:'用户状态',align:'center',width:100,templet: '#userStatus'},
                        {title:'创建时间',align:'center',sort:true,width:120,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>",field:'createTime'},
                        {title:'操作',fixed:'right',align:'center',toolbar:'#barOperator',width:250},
                    ]],
                    done:function () {
                        $('.btn-box').on('mouseenter mouseleave',function (event) {
                            if (event.type=='mouseenter'){
                                $(this).find('.more-operate').show();
                            }else if (event.type == 'mouseleave'){
                                $(this).find('.more-operate').hide();
                            }
                        });
                    }
                },
                events:{
                    checkbox:tableCheckBoxEvent,
                    tool: tableToolEvent,
                    sort: sort
                }
            },
            globalEvents:{
                enable: true,
                add: add,
                edit: edit,
                batchDelete: batchDelete,
                reset: resetEvent,
                openDept: openDept,
                toggle:toggle,
                refresh:refresh
            },
            ztree:{
                enable:true,
                elem: '#companyTree',
                url: '/system/dynamicGetDept',
                setting: {
                    data:{
                        simpleData: {
                            enable: true,
                            idKey: "departmentId",
                            pIdKey: "parentId",
                            rootPId: 0
                        },
                        key:{
                            name:"departmentName",
                            url:""
                        }
                    },
                    callback:{
                        onClick:function (event, treeId, treeNode) {
                            let param = getParam();
                            param.deptId = treeNode.departmentId;
                            renderTable(param);
                        }
                    }
                }
            }
        },
        form = studying.form();
    //初始化layout
    $(document).ready(function () {
        $('body').layout(layout_config);
    });

    studying.render(studyingConfig);


    //获取form中的参数值
    function getParam(){
        let data = form.val('userForm');
        let param = {
            'loginName': data.loginName,
            'createStartTime': data.createStartTime,
            'createEndTime' : data.createEndTime,
            'userStatus': data.userStatus
        };
        return param;
    }

    // 提交
    function submitEvent(data){
        let param = {
            'loginName': data.field.loginName,
            'createStartTime': data.field.createStartTime,
            'createEndTime' : data.field.createEndTime,
            'userStatus': data.field.userStatus
        };
        renderTable(param);
        return false;
    }

    //重新加载Table
    function renderTable(param){
        table.reload('userTable',{
            url:'/system/user/find',
            where:param
        });
    }

    //监听switch事件
    function changeStatus(obj) {
        let params = {
            userId: parseInt(obj.elem.getAttribute("current-id")),
            status: this.checked? 0:1
        };
        $.ajax({
            url: '/system/user/edit',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
            }
        });
    }

    /**********************Table***************************/

    //table复选框事件
    function tableCheckBoxEvent() {
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
    }

    //table工具栏事件
    function tableToolEvent(obj) {
        switch (obj.event) {
            case "edit" :
                editEvent(obj.data);
                break;
            case "delete":
                deleteEvent(obj.data);
                break;
            case "resetPassword":
                resetPwd(obj.data);
                break;
            case "assignRole":
                assignRole(obj.data);
                break;
        }
    }

    /**
     * 编辑事件
     * @param data
     */
    function editEvent(data) {
        top.layui.pageOp.openTabsPage('/system/user/edit/'+data.userId,'修改用户信息');
    }
    /**
     * 删除
     */
    function deleteEvent(data) {
        layer.confirm('是否删除所选择用户？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.get('/system/user/delete/'+data.userId,function (data) {
                layer.msg(data.msg);
                renderTable(getParam());
            });
        });
    }
    /**
     * 重新设置密码
     * @param data
     */
    function resetPwd(data) {
        layer.open({
            title: '重置密码',
            type: 2,
            content: '/system/user/resetPwd/'+data.loginName+'/'+data.userId,
            area:['600px','300px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                layer.close(index);
            }
        });
    }
    /**
     * 重新分配角色
     */
    function assignRole(data) {
        top.layui.pageOp.openTabsPage('/system/user/assignRole/'+data.userId,'用户分配角色');
    }

    /**
     * 排序
     * @param obj
     */
    function sort(obj){
        let param={
            'order': obj.field,
            'orderStrategy': obj.type
        };
        let params = $.extend(true,param,getParam());
        var nodes = $.fn.zTree.getZTreeObj('companyTree').getSelectedNodes();
        if (nodes!=null|undefined&&nodes.length>0){
            params.deptId = nodes[0].departmentId;
        }
        renderTable(params);
    }

    /*************************GlobalEvents********************************/
    /**
     * 添加用户
     */
    function add() {
        //todo 去了解下 top是个啥
        top.layui.pageOp.openTabsPage('/system/user/add','添加用户');
    }

    /**
     * 编辑用户
     */
    function edit() {
        let checkStatus = table.checkStatus('userTable');
        if (checkStatus.data.length!=1){
            layer.msg('每次只能选中一项进行编辑');
            return;
        }
        top.layui.pageOp.openTabsPage('/system/user/edit/'+checkStatus.data[0].userId,'修改用户信息');
    }

    /**
     * 批量删除
     */
    function batchDelete() {
        layer.confirm('是否删除所选择用户？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let checkedData = table.checkStatus('userTable');
            let ids = checkedData.data.map(item=>item.userId).join(',');
            $.get('/system/user/delete/'+ids,function (data) {
                layer.msg(data.msg);
                renderTable(getParam());
            });
        });
    }

    /**
     * 部门
     */
    function openDept() {
        top.layui.pageOp.openTabsPage('/system/dept','部门管理');
    }

    function toggle() {
        let i = $('#toggle');
        i.toggleClass('layui-icon-down');
        i.toggleClass('layui-icon-up');
        var zTreeObj = $.fn.zTree.getZTreeObj('companyTree');
        if (!i.hasClass('layui-icon-up')) {
            zTreeObj.expandAll(true);
        } else {
            zTreeObj.expandAll(false);
        }
    }

    /**
     * 刷新deptZtree
     */
    function refresh() {
        $.get(studyingConfig.ztree.url,function (data) {
            $.fn.zTree.init($(studyingConfig.ztree.elem),studyingConfig.ztree.setting,data).expandAll(true);
        });
    }

    /**
     * reset
     */
    function resetEvent() {
        renderTable(null);
    }


});