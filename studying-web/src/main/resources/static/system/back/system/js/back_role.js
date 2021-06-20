layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','layer','studying'],function(){
    let $ = layui.jquery,
        studying = layui.studying,
        layer = layui.layer,
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
                    event: changeRoleStatus
                }
            },
            dateTime:{
                enable: true
            }
        },
            table:{
            enable: true,
            filter: 'roleTable',
            options: {
                elem:'#roleTable',
                height: 'full-240',
                url:'/system/role/find',
                cols:[[
                    {type:'checkbox',fixed:'left'},
                    {field:'roleId',title:'角色编号',align:'center'},
                    {field:'roleName',title:'角色名称',align:'center',sort:true},
                    {field:'roleKey',title:'权限字符',align:'center',sort:true},
                    {field:'roleSort',title:'显示顺序',align:'center',sort:true},
                    {title:'角色状态',align:'center',templet:function (res) {
                            return `<input type="checkbox" lay-skin="switch" lay-filter="status" lay-text="正常|停用" ${res.status==0?'checked':''} value="${res.roleId}">`;
                        }},
                    {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>"},
                    {title:'操作',align:'center',toolbar:'#barOperator',width:250},
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
                tool: tableToolEvent
            }
        },
            globalEvents:{
                enable: true,
                add: addEvent,
                edit: editEvent,
                del: delEvent,
                reset: resetEvent
            }
        },
        form = studying.form();

    studying.render(studyingConfig);
    /**
     * 提交事件
     */
    function submitEvent(obj) {
        let data = obj.field;
        let params={
            roleName: data.roleName,
            roleKey: data.roleKey,
            roleStatus: data.roleStatus,
            createStartTime: data.preTime,
            createEndTime: data.nextTime
        };
        reloadTable(params);
        return false;
    }
    
    function changeRoleStatus(obj) {
        let status = this.checked?'0':'1';
        $.get('/system/role/changeStatus/'+this.value+'?status='+status,function (data) {
            layer.msg(data.msg);
            reloadTable(getParams());
        });
    }

    /**
     * 表格复选框
     */
    function tableCheckBoxEvent() {
        let checkStatus = table.checkStatus('roleTable');
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
     * 表格工具栏事件
     */
    function tableToolEvent(obj) {
        switch (obj.event) {
            case "edit":
                editFunction(obj.data);
                break;
            case "delete":
                delFunction(obj.data);
                break;
            case "assignUsers":
                assignUsersEvent(obj.data);
                break;
            case "dataAuth":
                dataScopeEvent(obj.data);
                break;
        }
    }

    /**
     * 行编辑功能
     */
    function editFunction(data) {
        layer.open({
            title: "修改角色",
            type: 2,
            content: '/system/role/modify/'+data.roleId,
            area:['600px','600px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                //这个应该是直接获得了子窗口的东西
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            end:function () {
                reloadTable(getParams());
            }
        });
    }

    /**
     * 行删除功能
     */
    function delFunction(data) {
        layer.confirm('是否删除所选择角色？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.get('/system/role/delete/'+data.roleId,function (data) {
                layer.msg(data.msg);
                reloadTable(getParams());
            });
        });
    }

    /**
     * 分配用户功能
     * @param data
     */
    function assignUsersEvent(data) {
        top.layui.pageOp.openTabsPage('/system/role/assignUsers/'+data.roleId,'分配用户');
    }

    /**
     *  分配数据权限
     * @param data
     */
    function dataScopeEvent(data) {
        layer.open({
            title: '数据权限',
            type: 2,
            content: '/system/role/dataScope/'+data.roleId,
            area:['600px','500px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            }
        });
    }

    /**
     * globalEvents add
     */
    function addEvent() {
        layer.open({
            title: "添加角色",
            type: 2,
            content: '/system/role/add',
            area:['600px','600px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                //这个应该是直接获得了子窗口的东西
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            done:function () {
                reloadTable(getParams());
            }
        });
    }

    /**
     * globalEvents edit
     */
    function editEvent() {
        let data = table.checkStatus('roleTable').data;
        if (data.length!=1){
            layer.msg("选择一个角色进行编辑");
            return;
        }
        layer.open({
            title: "修改角色",
            type: 2,
            content: '/system/role/modify/'+data[0].roleId,
            area:['600px','600px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                //这个应该是直接获得了子窗口的东西
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                //关闭窗口
                layer.close(index);
            },
            btn2: function () {

            }
        });
    }

    /**
     * globalEvents del
     */
    function delEvent() {
        layer.confirm('是否删除所选择角色？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let checkedData = table.checkStatus('roleTable');
            let ids = checkedData.data.map(item=>item.roleId).join(',');
            $.get('/system/role/delete/'+ids,function (data) {
                layer.msg(data.msg);
                reloadTable(getParams());
            });
        });
    }

    /**
     * globalEvent reset
     */
    function resetEvent() {
        reloadTable(null);
    }

    /**
     * 重新加载
     */
    function reloadTable(params) {
        table.reload('roleTable',{
            url: '/system/role/find',
            where: params
        });
    }

    function getParams() {
        let data = form.val('roleForm');
        let params={
            roleName: data.roleName,
            roleKey: data.roleKey,
            roleStatus: data.roleStatus,
            createStartTime: data.preTime,
            createEndTime: data.nextTime
        };
        return params;
    }
});